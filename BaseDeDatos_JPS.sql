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

#Insert por default de la aplicación
INSERT INTO Usuario (nombre,contrasena,tipo) VALUES('Nahum',MD5('Nahum123'),'Administrador');
Insert Into Usuario (nombre,contrasena,tipo) values('Samuel',MD5('Samuel123'),'Cliente');

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
#Tabla para el manejo de planes de premios
Create Table PlanPremios
(
	Identificador int Primary Key AUTO_INCREMENT,
    Sorteo int not null,
    
    FOREIGN KEY (Sorteo) REFERENCES Sorteo(Numero)
);
#Tabla para el manejo de premios
Create Table Premio
(
	Identificador int Primary Key AUTO_INCREMENT,
    Monto int not null,
    Cantidad int not null,    
    PlanPremios int not null,
    
    FOREIGN KEY (PlanPremios) REFERENCES PlanPremios(Identificador)    
);
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
Select Numero as 'Número', Leyenda, Fecha, Tipo, CantidadFracciones as 'Fracciones',PrecioBillete as 'Precio', Estado
From Sorteo;

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

Select * From Planes 
#**********************************************************************************************************************
#Vista para sorteos que no tengan plan de premios
Create View SorteosSinPlan
As
Select Numero
From Sorteo as S
Where Numero NOT IN(
SELECT Sorteo  FROM PlanPremios);

#**********************************************************************************************************************
#Vista para el reporte de un sorteo
Create View Reporte
As
Select 


