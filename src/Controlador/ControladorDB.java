/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Frontend.Login;
import Frontend.VAdministrador;
import Frontend.VClientes;
import Modelo.ConexionDB;
import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Clase Contralador que permite hacer una solicitud a a base de datos
 * @author Nahum
 */
public class ControladorDB 
{    
    private static ControladorDB Controlador = null;    

    public ControladorDB() throws ClassNotFoundException, SQLException {
        ConexionDB.CrearConexionDB();
    }
    
    //  Método que crea el objeto singleton
    public static void CrearControlador() throws ClassNotFoundException, SQLException 
    {
        if(Controlador == null){
            Controlador = new ControladorDB();
        }
    }
    
    //  Método que retorna el objeto singletón
    public static ControladorDB getControlador(){
        return Controlador;
    }
    
    /**
     * Método que valida el acceso de un usuario a la aplicación
     * @param Nombre String Nombre del usuario
     * @param Contrasena String Contrseña del usuario
     * @param Objeto Instancia de la ventana Login
     */
    public void InicioSesion(String Nombre, String Contrasena, Login Objeto)
    {     
        try{
            String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta("select Login('"+Nombre+"','"+Contrasena+"');"));
            switch(Resultado)
            {
                case "0":// Usuario Administrador
                    VAdministrador VentanaA = new VAdministrador();
                    VentanaA.setVisible(true);
                    break;
                case "1":// Usuario Cliente
                    VClientes VentanaC = new VClientes();
                    VentanaC.setVisible(true);
                    break;
                default://  No existe el usuario
                    JOptionPane.showMessageDialog(null, "Usuario no existe", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                    return;
            }                                        
            Objeto.dispose();// Cierra la ventana login
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }            
    
    public void ManejoSorteo(int Cmd, String Leyenda, String Fecha, String Tipo, int CantFraciones, int PrecioBillete, int Identificador)
    {        
        String Consulta = "SELECT ManejoSorteo("+Cmd+",'"+Leyenda+"','"+Fecha+"','"+Tipo+"',"+CantFraciones+","+PrecioBillete+","+Identificador+");";
        try{
            String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta(Consulta));
            switch(Resultado)
            {
                case "1":
                    JOptionPane.showMessageDialog(null, "Sorteo agregado con éxito", "Mensaje de éxito", JOptionPane.INFORMATION_MESSAGE);                    
                    break;
                case "2":
                    break;
                case "3":
                    break;
                
            }
        }catch(Exception ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }            
}
