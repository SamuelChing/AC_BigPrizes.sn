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
import javax.swing.JOptionPane;

/**
 * @author Nahum Blanco Rojas
 * Clase que tiene el fin de gesionar la conexion con la base de datos
 */
public class ConexionDB 
{
    //  Datos del modelo de almacenamiento 
    private static ConexionDB Conexion = null;
    private String DRIVER = "org.gjt.mm.mysql.Driver";
    private String URL = "jdbc:mysql://localhost:3306/LotteryDB?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private String Usuario = "root";
    private String Contrasena = "Nahum.Nisshi33"; 
    private Connection DataBase;                     
    
    /// Constructor de la clase
    public ConexionDB() throws ClassNotFoundException, SQLException 
    {                        
        Class.forName(DRIVER);
        this.DataBase = DriverManager.getConnection(URL, Usuario,Contrasena);        
    }
    
    //  Método que crea el objeto singleton
    public static void CrearConexionDB() throws ClassNotFoundException, SQLException
    {
        if(Conexion == null){
            Conexion = new ConexionDB();
        }
    }
    
    //  Método que retorna el objeto singletón
    public static ConexionDB getConexionDB(){
        return Conexion;
    }
    /**
     * Método que procesa una respuesta de consulta en un string
     * @param Resultado Es un objeto que tiene el resultado de una consulta a procedimiento
     * @return  Un entero resultado de la consulta
     * @throws SQLException 
     */
    public String ResultadoString(ResultSet Resultado) 
    {
        try{
            ResultSetMetaData rsMd = Resultado.getMetaData();
            Resultado.next();                                
            String ResultadoFin = Resultado.getObject(1).toString();
            Resultado.close();         
            return ResultadoFin;
        }catch(Exception ex){                      
            return null;
        }
    }
    /**
     * 
     * @param Consulta Un string con la consulta
     * @return  El resultado de la consulta en ResultSet formato que necesita ser procesado
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public ResultSet EjecutarConsulta(String Consulta) 
    {              
        try{
            Statement Statement = DataBase.createStatement();
            ResultSet Resultado = Statement.executeQuery(Consulta);            
            return Resultado;           
        }catch(Exception ex){                       
            return null;
        }
    }    
}
