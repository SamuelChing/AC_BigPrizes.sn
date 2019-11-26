/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Frontend.Login;
import Frontend.Ventana;
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
 * @author SAMUEL
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
        String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta("select Login('"+Nombre+"','"+Contrasena+"');"));
        switch(Resultado)
        {
            case "0":// Usuario Administrador
                Ventana VentanaA = new Ventana();
                VentanaA.setVisible(true);
                break;
            case "1":// Usuario Cliente
                Ventana VentanaB = new Ventana();
                VentanaB.BloqueraCliente();
                VentanaB.setVisible(true);                
                break;
            default://  No existe el usuario
                JOptionPane.showMessageDialog(null, "Usuario no existe", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                return;
        }                                        
        Objeto.dispose();// Cierra la ventana login        
    }            
    /**
     * Método que maneja las consultas a sorteos
     * @param Cmd
     * @param Leyenda
     * @param Fecha
     * @param Tipo
     * @param CantFraciones
     * @param PrecioBillete
     * @param Identificador 
     */
    public void ManejoSorteo(int Cmd, String Leyenda, String Fecha, String Tipo, int CantFraciones, int PrecioBillete, int Identificador)
    {        
        String Consulta = "SELECT ManejoSorteo("+Cmd+",'"+Leyenda+"','"+Fecha+"','"+Tipo+"',"+CantFraciones+","+PrecioBillete+","+Identificador+");";        
        String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta(Consulta));
        switch(Resultado)
        {
            case "1":
                JOptionPane.showMessageDialog(null, "Sorteo agregado con éxito", "Mensaje de éxito", JOptionPane.INFORMATION_MESSAGE);                    
                break;
            case "2":
                JOptionPane.showMessageDialog(null, "Sorteo eliminado con éxito", "Mensaje de éxito", JOptionPane.INFORMATION_MESSAGE);                    
                break;
            case "3":
                JOptionPane.showMessageDialog(null, "Sorteo finalizado", "Mensaje de éxito", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "4":
                JOptionPane.showMessageDialog(null, "Sorteo actualizado con éxito", "Mensaje de éxito", JOptionPane.INFORMATION_MESSAGE);                    
                break;
        }        
    }  
    
    /**
     * Método que maneja las consultas a planes de premio
     * @param Cmd
     * @param Monto
     * @param Cantidad
     * @param Identificador
     * @return true o false
     */
    public boolean ManejoPlanPremios(int Cmd, int Monto,int Cantidad, int Identificador)
    {
        String Consulta = "Select ManejoPlanDePremio("+Cmd+","+Monto+","+Cantidad+","+Identificador+");";
        String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta(Consulta));
        switch(Resultado)
        {
            case "1":
                return true;                
            case "2":
                return true;
            case "3":
                return true;
            case "4":
                return true;
        }
        return false;
    }
    
    /**
     * Método que maneja las consultas de ganadores
     * @param Cmd
     * @param Sorteo
     * @param Tipo
     * @param Numero
     * @param Serie
     * @param Monto
     * @return true o false
     */
    public boolean ManejoGanadores(int Cmd, int Sorteo, String Tipo, int Numero, int Serie, int Monto)
    {
        String Consulta = "Select ManejoGanadores("+Cmd+","+Sorteo+",'"+Tipo+"',"+Numero+","+Serie+","+Monto+");";
        String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta(Consulta));
        switch(Resultado)
        {
            case "1":
                return true;                
            case "2":
                return true;            
        }
        return false;
    }        
}
