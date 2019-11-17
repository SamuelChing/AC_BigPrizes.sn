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


#Funcion para el manejo de Sorteos
DELIMITER //
CREATE Function ManejoSorteo
(
	Cmd int, 
    PLeyenda varchar(256),
    PFecha date,
    PTipo varchar(256),
    PCantidad_fracciones int,
    PPrecio_billete int,
    PEstado int,
    Pidentificador int
)
Returns int
BEGIN
	Declare Resultado int;        
	if Cmd = 1 then # Insertar un sorteo
		INSERT INTO Sorteo(leyenda,fecha,tipo,cantidad_fracciones,precio_billete,estado)
        VALUES(PLeyenda,PFecha,PTipo,PCantidad_fracciones,PPrecio_billete,PEstado);
        return 1;
    end if;		
    if cmd = 2 then # Eliminar un sorteo
		Select plan Into Resultado From PlanPremios as PP where (PP.id_sorteo = Pidentificador);
        if Resultado is null then
			delete from Sorteo where (Sorteo.id = Pidentificador);
			return 2;
        else
			delete from Premios where (Premios.idPlan = Resultado);
			delete from PlanPremios where (PlanPremios.id_sorteo = Pidentificador);
			delete from Sorteo where (Sorteo.id = Pidentificador);
            return 2;
        end if;		        
    end if;        
    if cmd = 3 then # CAMBIAR ESTADO A SORTEO JUGADO		
		update Sorteo set Sorteo.estado = PEstado where (Sorteo.id = Pidentificador);
        return 4;
    End if;
END ;
//
DELIMITER ;

#SELECT ManejoSorteo(1,'Leyenda','2019-02-12','Loteria',20,2000,1,2);
#Select * From Sorteo;
#**********************************************************************************************************************


/*Premios disponibles de un plan*/

#create table ganadores(
#	id int AUTO_INCREMENT PRIMARY KEY,
#    serie varchar(255) not null,/*Serie del sorteo*/
#    cantidad int not null, /*Cantidad de fracciones*/
#    numero varchar(255) not null, /*Numero del sorteo*/
#    monto int not null,
#    idSorteo int,
#    foreign key (idSorteo) references sorteo(id)
#);*/