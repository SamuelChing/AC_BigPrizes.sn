/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Nahum Blanco Rojas
 * Clase que tiene el fin de gesionar la conexion con la base de datos
 */
public class ConexionDB 
{
    //  Datos del modelo de almacenamiento
    private String DRIVER = "org.gjt.mm.mysql.Driver";
    private String URL;
    private String Usuario;
    private String Contrasena; 
    private Connection DataBase;                     
    
    /**
     * Constructor de la base
     * @param NombreBase Nombre de la base de datos a conectar
     * @param Usuario Nombre del usuario con permisos de conexión
     * @param Contrasena Contraseña del usuario
     */
    public ConexionDB(String NombreBase, String Usuario, String Contrasena) throws ClassNotFoundException, SQLException 
    {
        this.URL = "jdbc:mysql://localhost:3306/"+NombreBase+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        this.Usuario = Usuario;
        this.Contrasena = Contrasena;
        Class.forName(DRIVER);
        this.DataBase = DriverManager.getConnection(URL, Usuario,Contrasena);
    }
    /**
     * Método que convierte una respuesta de consulta en un entero
     * @param Resultado Es un objeto que tiene el resultado de una consulta
     * @return  Un entero resultado de la consulta
     * @throws SQLException 
     */
    public int ResultadoNumero(ResultSet Resultado) throws SQLException
    {
        ResultSetMetaData rsMd = Resultado.getMetaData();        
        int cantidadColumnas = rsMd.getColumnCount();
        Resultado.next();                                
        String ResultadoFin = Resultado.getObject(1).toString();
        Resultado.close(); 
        System.out.println(ResultadoFin);
        return Integer.parseInt(ResultadoFin);
    }
    /**
     * 
     * @param Consulta Un string con la consulta
     * @return  El resultado de la consulta en ResultSet formato
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public ResultSet EjecutarConsulta(String Consulta) throws ClassNotFoundException, SQLException
    {                
        Statement Statement = DataBase.createStatement();
        ResultSet Resultado = Statement.executeQuery(Consulta);
        
        return Resultado;
    }
}
