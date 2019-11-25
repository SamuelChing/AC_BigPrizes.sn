#**********************************************************************************************************************
CREATE database LotteryDB;
use LotteryDB;
SET GLOBAL log_bin_trust_function_creators = 1;
#**********************************************************************************************************************
#Tabla para control de usuarios
create table Usuario
(
	id int AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(256),
    contrasena varchar(256) not null,
    tipo varchar(256) not null
);

#**********************************************************************************************************************
#Insert por default de la aplicación
INSERT INTO Usuario (nombre,contrasena,tipo) VALUES('Nahum',MD5('Nahum123'),'Administrador');
Insert Into Usuario (nombre,contrasena,tipo) values('Samuel',MD5('Samuel123'),'Cliente');
#**********************************************************************************************************************
#Funcion para el login de usuarios
DELIMITER //
CREATE Function Login
(
	PNombre varchar(256),
    PContrasenna varchar(256)
)
Returns int
BEGIN
	Declare Resultado Varchar(256);
    Select tipo Into Resultado From Usuario as U Where U.nombre = PNombre and U.contrasena = MD5(PContrasenna);    
    if Resultado is null then
		return -1;#Usuario no existe         
    else if Resultado = 'Administrador' then
			return 0;#Usuario Administrador            
        else 
			return 1;#Usuario cliente             
		end if;
    end if;	    
END //
DELIMITER ;

#**********************************************************************************************************************
#Tabla para el manejo de Sorteos
Create Table Sorteo
(
	Numero int Primary Key AUTO_INCREMENT,
    Leyenda varchar(256) not null,
    Fecha date not null,
    Tipo varchar(256) not null,
    CantidadFracciones int not null,
    PrecioBillete int not null,
    Estado varchar(256) default 'Sin jugar'
);

#**********************************************************************************************************************
#Tabla para el manejo de planes de premios
Create Table PlanPremios
(
	Identificador int Primary Key AUTO_INCREMENT,
    Sorteo int not null,
    
    FOREIGN KEY (Sorteo) REFERENCES Sorteo(Numero)
);

#**********************************************************************************************************************
#Tabla para el manejo de premios
Create Table Premio
(
	Identificador int Primary Key AUTO_INCREMENT,
    Monto int not null,
    Cantidad int not null,    
    PlanPremios int not null,
    
    FOREIGN KEY (PlanPremios) REFERENCES PlanPremios(Identificador)    
);

#**********************************************************************************************************************
#Tabla para el manejo de ganadores
Create Table Ganador
(
	Identificador int Primary Key AUTO_INCREMENT,
    NumeroSorteo int not null,
    TipoSorteo varchar(256) not null,
    NumeroGanador int not null,
    SerieGanadora int not null,
    MontoGanado int not null,
    
    FOREIGN KEY (NumeroSorteo) REFERENCES Sorteo(Numero)
);
#**********************************************************************************************************************
DELIMITER //
CREATE Function ManejoGanadores
(
	Cmd int,    
    PIdentificador int,
    PTipo varchar(256),
    PNumero int,
    PSerie int,
    PMonto int   
)
Returns int
BEGIN
	Declare Resultado int;        
	if Cmd = 1 then # Insertar un plan de premios
		INSERT INTO Ganador(NumeroSorteo,TipoSorteo,NumeroGanador,SerieGanadora,MontoGanado)
        VALUES(PIdentificador,PTipo,PNumero,PSerie,PMonto);
        return 1;
    End if;    
END ;
//
DELIMITER ;

#**********************************************************************************************************************
#Funcion para el manejo de Sorteos
#Para agregar un sorteo Cmd = 1, enviamos los datos en orden y Pidentificador no importa
#Para eliminar un sorteo Cmd = 2, Pidentificador = Sorteo que queremos eliminar
#Para cambiar estado de jugado a un sorteo Cmd = 3, Pidentificador = Sorteo que queremos jugar
DELIMITER //
CREATE Function ManejoSorteo
(
	Cmd int, 
    PLeyenda varchar(256),
    PFecha date,
    PTipo varchar(256),
    PCantidad_fracciones int,
    PPrecio_billete int,    
    Pidentificador int
)
Returns int
BEGIN
	Declare Resultado int;        
	if Cmd = 1 then # Insertar un sorteo
		INSERT INTO Sorteo(Leyenda,Fecha,Tipo,CantidadFracciones,PrecioBillete)
        VALUES(PLeyenda,PFecha,PTipo,PCantidad_fracciones,PPrecio_billete);
        return 1;
    end if;		
    if cmd = 2 then # Eliminar un sorteo
		Select Identificador Into Resultado From PlanPremios as PP where (PP.Sorteo = Pidentificador);
        if Resultado is null then
			delete from Sorteo where (Sorteo.Numero = Pidentificador);
			return 2;
        else
			delete from Premio where (Premio.PlanPremios = Resultado);
			delete from PlanPremios where (PlanPremios.Identificador = Resultado);
			delete from Sorteo where (Sorteo.Numero = Pidentificador);
            return 2;
        end if;		        
    end if;        
    if cmd = 3 then # CAMBIAR ESTADO A SORTEO JUGADO		
		update Sorteo set Sorteo.Estado = 'Jugado' where (Sorteo.Numero = Pidentificador);
        return 3;        
    End if;
    if cmd = 4 then
		update Sorteo set Leyenda=PLeyenda,Fecha=PFecha,Tipo=PTipo,CantidadFracciones=PCantidad_fracciones,PrecioBillete=PPrecio_billete
        where (Sorteo.Numero = Pidentificador);
        return 4;
    End if;
END ;
//
DELIMITER ;

#**********************************************************************************************************************
DELIMITER //
CREATE Function ManejoPlanDePremio
(
	Cmd int,    
    PMonto int,
    PCantidad int,
    PIdentificador int
)
Returns int
BEGIN
	Declare Resultado int;        
	if Cmd = 1 then # Insertar un plan de premios
		INSERT INTO PlanPremios(Sorteo)
        VALUES(PIdentificador);
        return 1;
    End if;
    if(Cmd = 2)	then # Insertar un premio de un plan de premios		
        Insert Into Premio(Monto,Cantidad,PlanPremios)
        Values(PMonto,PCantidad,PIdentificador);
        return 2;
	End if;
	if(Cmd = 3) then # Eliminar un plan de premios completo		
        delete from Premio where (Premio.PlanPremios = PIdentificador);
		delete from PlanPremios where (PlanPremios.Identificador = PIdentificador);
		return 3;
    End if;
    if(Cmd = 4) then # Eliminar los premios de un plan		
        delete from Premio where (Premio.PlanPremios = PIdentificador);
        return 4;
    End if;
END ;
//
DELIMITER ;

#**********************************************************************************************************************
#Vista para Sorteos
Create View Sorteos 
As
Select Numero, Leyenda, Fecha, Tipo, CantidadFracciones as 'Fracciones',PrecioBillete as 'Precio', Estado
From Sorteo
Where Estado = 'Sin jugar';

#**********************************************************************************************************************
#Vista para los planes de premios
Create View Planes
As
SELECT Identificador,S.Leyenda as 'Sorteo', 
(Select Count(Identificador) as Cantidad FROM Premio where PlanPremios = PP.Identificador) as 'Cantidad de premios',
(Select SUM(Monto*Cantidad) FROM Premio where PlanPremios = PP.Identificador) as 'Monto Total'
FROM PlanPremios as PP
Inner Join Sorteo as S
On S.Numero = PP.Sorteo;

#**********************************************************************************************************************
#Vista para sorteos que no tengan plan de premios
Create View SorteosSinPlan
As
Select Numero
From Sorteo as S
Where Numero NOT IN(
SELECT Sorteo  FROM PlanPremios);

#**********************************************************************************************************************
#Vista para Sorteos con plan pero sin jugar
Create View SorteosParaJugar
As
Select Numero as 'Número', Leyenda, Fecha, Tipo, CantidadFracciones as 'Fracciones',PrecioBillete as 'Precio', Estado
From Sorteo as S
Where Numero IN(
SELECT Sorteo  FROM PlanPremios) and Estado = 'Sin jugar';

#**********************************************************************************************************************
#Vista para comparar ganador
Create View Ganadores
As
Select NumeroSorteo, TipoSorteo, NumeroGanador, SerieGanadora, MontoGanado, S.CantidadFracciones
From Ganador
Inner Join Sorteo as S
On S.Numero = NumeroSorteo;

#**********************************************************************************************************************
#Vista Top 10 Para NUMEROS más jugados para lotería
Create View Top10MasJugadosLoteria
As
SELECT NumeroGanador, Count(NumeroGanador) as Cantidad
FROM Ganador as G
Where TipoSorteo = 'Lotería'
Group by G.NumeroGanador
ORDER BY Cantidad DESC
LIMIT 10;

#**********************************************************************************************************************
#Vista Top 10 Para NUMEROS más jugados para chances
Create View Top10MasJugadosChances
As
SELECT NumeroGanador , Count(NumeroGanador) as Cantidad
FROM Ganador as G
Where TipoSorteo = 'Chances'
Group by G.NumeroGanador
ORDER BY Cantidad DESC
LIMIT 10;

#**********************************************************************************************************************
#Vista Top 10 Para NUMEROS más jugados para todos
Create View Top10MasJugadosTodos
As
SELECT NumeroGanador , Count(NumeroGanador) as Cantidad
FROM Ganador as G
Group by G.NumeroGanador
ORDER BY Cantidad DESC
LIMIT 10;

#**********************************************************************************************************************
#Vista Top 5 Para NUMEROS más premiados para lotería
Create View Top5MasPremiadosLoteria
As
SELECT NumeroGanador , Sum(MontoGanado) as MontoTotal
FROM Ganador as G
Where TipoSorteo = 'Lotería'
Group by G.NumeroGanador
ORDER BY MontoTotal DESC
LIMIT 5;

#**********************************************************************************************************************
#Vista Top 5 Para NUMEROS más premiados para chances
Create View Top5MasPremiadosChances
As
SELECT NumeroGanador , Sum(MontoGanado) as MontoTotal
FROM Ganador as G
Where TipoSorteo = 'Chances'
Group by G.NumeroGanador
ORDER BY MontoTotal DESC
LIMIT 5;

#**********************************************************************************************************************
#Vista Top 5 Para NUMEROS más premiados para todos
Create View Top5MasPremiadosTodos
As
SELECT NumeroGanador , Sum(MontoGanado) as MontoTotal
FROM Ganador as G
Group by G.NumeroGanador
ORDER BY MontoTotal DESC
LIMIT 5;

#**********************************************************************************************************************
#Vista que muestra el primer premio por sorteo asoiciado a su plan
Create View PrimerPremioPorSorteo
As
Select Sorteo, Max(Monto) as PrimerPremio
From PlanPremios as PP
Inner Join Premio as P
ON P.PlanPremios = PP.Identificador
Group by Sorteo;

#**********************************************************************************************************************
#Vista para Top 5 más ganadores lotería
Create View Top5MasGanadoresLoteria
As
SELECT NumeroGanador, Count(NumeroGanador) as Cantidad
FROM Ganador as G
Inner Join PrimerPremioPorSorteo AS PPS
On PPS.Sorteo = G.NumeroSorteo and PPS.PrimerPremio = G.MontoGanado
Where G.TipoSorteo = 'Lotería'
Group by G.NumeroGanador
ORDER BY Cantidad DESC
LIMIT 5;

#**********************************************************************************************************************
#Vista para Top 5 más ganadores Chances
Create View Top5MasGanadoresChances
As
SELECT NumeroGanador, Count(NumeroGanador) as Cantidad
FROM Ganador as G
Inner Join PrimerPremioPorSorteo AS PPS
On PPS.Sorteo = G.NumeroSorteo and PPS.PrimerPremio = G.MontoGanado
Where G.TipoSorteo = 'Chances'
Group by G.NumeroGanador
ORDER BY Cantidad DESC
LIMIT 5;

#**********************************************************************************************************************
#Vista para Top 5 más ganadores todos
Create View Top5MasGanadoresTodos
As
SELECT NumeroGanador, Count(NumeroGanador) as Cantidad
FROM Ganador as G
Inner Join PrimerPremioPorSorteo AS PPS
On PPS.Sorteo = G.NumeroSorteo and PPS.PrimerPremio = G.MontoGanado
Group by G.NumeroGanador
ORDER BY Cantidad DESC
LIMIT 5;

#**********************************************************************************************************************
#Vista para ver la cantidad de Ganadores totales de lotería
Create View TotalGanadoresLoteria
as
Select Count(Identificador) as Cantidad
From Ganador as G
Where G.TipoSorteo = 'Lotería';

#**********************************************************************************************************************
#Vista para ver la cantidad de Ganadores totales de lotería
Create View TotalGanadoresChances
as
Select Count(Identificador) as Cantidad
From Ganador as G
Where G.TipoSorteo = 'Chances';

#**********************************************************************************************************************
#Vista para ver la cantidad de Ganadores totales
Create View TotalGanadoresTodos
as
Select Count(Identificador) as Cantidad
From Ganador as G;

#**********************************************************************************************************************
Create View VecesSalidoLoteria
As
SELECT NumeroGanador , Count(NumeroGanador) as Cantidad
FROM Ganador as G
Where G.TipoSorteo = 'Lotería'
Group by G.NumeroGanador;

#**********************************************************************************************************************
Create View VecesSalidoChances
As
SELECT NumeroGanador , Count(NumeroGanador) as Cantidad
FROM Ganador as G
Where G.TipoSorteo = 'Chances'
Group by G.NumeroGanador;

#**********************************************************************************************************************
Create View VecesSalidoTodos
As
SELECT NumeroGanador , Count(NumeroGanador) as Cantidad
FROM Ganador as G
Group by G.NumeroGanador;

#**********************************************************************************************************************
#Vista para probabilidad de un numero en lotería
Create View ProbabilidadLoteria
As 
Select NumeroGanador, (VL.Cantidad/TG.Cantidad) as Probabilidad
From VecesSalidoLoteria as VL
Inner Join TotalGanadoresLoteria as TG;

#**********************************************************************************************************************
#Vista para probabilidad de un numero en chances
Create View ProbabilidadChances
As 
Select NumeroGanador, (VL.Cantidad/TG.Cantidad) as Probabilidad
From VecesSalidoChances as VL
Inner Join TotalGanadoresChances as TG;

#**********************************************************************************************************************
#Vista para probabilidad de un numero en todos los sorteos
Create View ProbabilidadTodos
As 
Select NumeroGanador, (VL.Cantidad/TG.Cantidad) as Probabilidad
From VecesSalidoTodos as VL
Inner Join TotalGanadoresTodos as TG;



