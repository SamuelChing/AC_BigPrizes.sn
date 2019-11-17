/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Frontend.Ventana_Administrador;
import Frontend.Ventana_Login;
import Modelo.ConexionDB;
import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase Contralador que permite hacer una solicitud a a base de datos
 * @author Nahum
 */
public class ControladorDataBase 
{    
    private static ControladorDataBase Controlador = null;    

    public ControladorDataBase() throws ClassNotFoundException, SQLException {
        ConexionDB.CrearConexionDB();
    }
    
    //  Método que crea el objeto singleton
    public static void CrearControlador() throws ClassNotFoundException, SQLException 
    {
        if(Controlador == null){
            Controlador = new ControladorDataBase();
        }
    }
    
    //  Método que retorna el objeto singletón
    public static ControladorDataBase getControlador(){
        return Controlador;
    }
    
    
    public void InicioSesion(String Nombre, String Contrasena, Ventana_Login Objeto)
    {     
        try{
            String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta("select Login('"+Nombre+"','"+Contrasena+"');"));
            switch(Resultado)
            {
                case "0":// Usuario Administrador
                    Ventana_Administrador Ventana = new Ventana_Administrador();
                    Ventana.setVisible(true);
                    break;
                case "1":// Usuario Cliente
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
    
    public void LlenarTablaConsulta(String Consulta, JTable Tabla)
    {
        try{
            //  Se setea el modelo de la tabla
            //DefaultTableModel modeloTabla = new DefaultTableModel();
            //Tabla.setModel(modeloTabla);             
            //  Se ejecuta la consulta y se guarda la tabla resultado
            ResultSet TablaResultado = ConexionDB.getConexionDB().EjecutarConsulta(Consulta);            
            //  Se toma la fila del encabezado
            ResultSetMetaData FilaEncabezado = (ResultSetMetaData) TablaResultado.getMetaData();
            int ColumnasEncabezado = FilaEncabezado.getColumnCount();            
            //  Se definen las columnas del encabezado para mostrar la información
            for (int i = 1; i <= ColumnasEncabezado; i++) 
            {
                //  Obtiene el valor de la etiqueta del encabezado y la setea a la tabla
                //modeloTabla.addColumn(FilaEncabezado.getColumnLabel(i));
            }            
            //  Se lena la tabla con la información de la tabla resultado de la consulta
            while (TablaResultado.next())// Recorre fila por fila la consulta
            {
                String Filita = "";
                Object[] fila = new Object[ColumnasEncabezado];//   Crea un arreglo para setear la fila
                for (int i = 1; i < ColumnasEncabezado; i++)//  Setea columna por columna la fila actual
                {
                  //    Toma el valor de la columna y lo inserta en el arreglo fila
                    fila[i] = TablaResultado.getObject(i).toString();
                    Filita += TablaResultado.getObject(i).toString()+"  ";
                }
                //  Setea la fila en el modelo de la tabla de muestra
                //modeloTabla.addRow(fila);               
                System.out.println(Filita);
            }
            //  Se cierra la consulta
            TablaResultado.close();                                    
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
