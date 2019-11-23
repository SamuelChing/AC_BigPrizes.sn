/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConexionDB;
import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nahum
 */
public class ControladorGUI {
    
    private static ControladorGUI Controlador = null;    
    
    public ControladorGUI() throws ClassNotFoundException, SQLException {
        ConexionDB.CrearConexionDB();
    }
    
    //  Método que crea el objeto singleton
    public static void CrearControlador() throws ClassNotFoundException, SQLException 
    {
        if(Controlador == null){
            Controlador = new ControladorGUI();
        }
    }
    
    //  Método que retorna el objeto singletón
    public static ControladorGUI getControlador(){
        return Controlador;
    }
    
    /**
     * Método que realiza una consulta y la despliega en una tabla
     * @param Consulta String de consulta
     * @param Tabla Objeto Jtable
     */
    public void LlenarTablaConsulta(String Consulta, JTable Tabla)
    {
        try{
            //  Se setea el modelo de la tabla
            DefaultTableModel modeloTabla = new DefaultTableModel();
            Tabla.setModel(modeloTabla);             
            //  Se ejecuta la consulta y se guarda la tabla resultado
            ResultSet TablaResultado = ConexionDB.getConexionDB().EjecutarConsulta(Consulta);            
            //  Se toma la fila del encabezado
            ResultSetMetaData FilaEncabezado = (ResultSetMetaData) TablaResultado.getMetaData();
            int ColumnasEncabezado = FilaEncabezado.getColumnCount();            
            //  Se definen las columnas del encabezado para mostrar la información
            for (int i = 1; i <= ColumnasEncabezado; i++) 
            {
                //  Obtiene el valor de la etiqueta del encabezado y la setea a la tabla
                modeloTabla.addColumn(FilaEncabezado.getColumnLabel(i));
            }            
            //  Se lena la tabla con la información de la tabla resultado de la consulta
            while (TablaResultado.next())// Recorre fila por fila la consulta
            {                
                Object[] fila = new Object[ColumnasEncabezado];//   Crea un arreglo para setear la fila
                for (int i = 1; i <= ColumnasEncabezado; i++)//  Setea columna por columna la fila actual
                {
                  //    Toma el valor de la columna y lo inserta en el arreglo fila
                    fila[i-1] = TablaResultado.getObject(i).toString();                    
                }
                //  Setea la fila en el modelo de la tabla de muestra
                modeloTabla.addRow(fila);                
            }
            //  Se cierra la consulta
            TablaResultado.close();                                    
        }catch(Exception ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String[] getRowToArray(String Consulta)
    {
        try
        {
            ResultSet TablaResultado = ConexionDB.getConexionDB().EjecutarConsulta(Consulta);            
            //  Se toma la fila del encabezado
            ResultSetMetaData FilaEncabezado = (ResultSetMetaData) TablaResultado.getMetaData();
            int ColumnasEncabezado = FilaEncabezado.getColumnCount();            
            //  Se define el temaño del array de retorno
            String[] Resultado = new String[ColumnasEncabezado];
            //  Se posiciona en la fila resultado de la consulta
            TablaResultado.next();
            
            String[] fila = new String[ColumnasEncabezado];//   Crea un arreglo para setear la fila
            for (int i = 1; i <= ColumnasEncabezado; i++)//  Setea columna por columna la fila actual
            {
              //    Toma el valor de la columna y lo inserta en el arreglo fila
                fila[i-1] = TablaResultado.getObject(i).toString();                    
            }
            return fila;
        }   
        catch(Exception ex)
        {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }  
        return null;
    }
}
