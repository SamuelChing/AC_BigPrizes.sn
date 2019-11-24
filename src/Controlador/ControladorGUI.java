/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConexionDB;
import ac_proyecto.PlanPremios;
import ac_proyecto.Premio;
import ac_proyecto.Sorteo;
import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
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
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Método que maprea una fila referente a la consulta
     * @param Consulta
     * @return un array de string con los datos en orden como en la tabla
     */
    public String[] getRowToArray(String Consulta)
    {
        try{
            ResultSet TablaResultado = ConexionDB.getConexionDB().EjecutarConsulta(Consulta);            
            //  Se toma la fila del encabezado
            ResultSetMetaData FilaEncabezado = (ResultSetMetaData) TablaResultado.getMetaData();
            int ColumnasEncabezado = FilaEncabezado.getColumnCount();                        
            //  Se posiciona en la fila resultado de la consulta
            TablaResultado.next();
            
            String[] fila = new String[ColumnasEncabezado];//   Crea un arreglo para setear la fila
            for (int i = 1; i <= ColumnasEncabezado; i++)//  Setea columna por columna la fila actual
            {
              //    Toma el valor de la columna y lo inserta en el arreglo fila
                fila[i-1] = TablaResultado.getObject(i).toString();                    
            }
            return fila;
        }catch(Exception ex){                        
            return null;
        }          
    }
    
    /**
     * Método que llena un elemento JCombobox con los datos de una consula pero solo de una columna
     * @param Consulta
     * @param CB_Temporal 
     */
    public void LlenarCombobox(String Consulta, JComboBox CB_Temporal)
    {
        try{               
            //  Se ejecuta la consulta y se guarda la tabla resultado
            ResultSet TablaResultado = ConexionDB.getConexionDB().EjecutarConsulta(Consulta);            
            //  Se toma la fila del encabezado
            ResultSetMetaData FilaEncabezado = (ResultSetMetaData) TablaResultado.getMetaData();                                               
            //  Se limpia primero el Combobox
            CB_Temporal.removeAllItems();
            //  Se llena el combobox con los resultado de la consulta
            while (TablaResultado.next())// Recorre fila por fila la consulta
            {                                
                CB_Temporal.addItem(TablaResultado.getObject(1).toString());
            }
            //  Se cierra la consulta
            TablaResultado.close();                                    
        }catch(Exception ex){            
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Método que retorna un string con el resultado de una consulta de una columna
     * @param Consulta
     * @return String con la respuesta de la consulta
     */
    public String getDatoCosnulta(String Consulta)
    {
        String Resultado = ConexionDB.getConexionDB().ResultadoString(ConexionDB.getConexionDB().EjecutarConsulta(Consulta));
        return Resultado;
    }
    
    /**
     * Método que mapea un objeto sorteo de la base de datos
     * @param IdentificadorSorteo
     * @return Un objeto sorteo
     */
    public Sorteo MapeoSorte(String IdentificadorSorteo)
    {
        //  Lo primero es encontrar el identificador del plan de premios de ese sorteo
        String IdentificadorPlan = getDatoCosnulta("Select Identificador From PlanPremios Where Sorteo = "+IdentificadorSorteo+";");                
        //  Se mapean los premios asociado al plan de premios
        ArrayList<Premio> ListaPremios = MapeoPremio("Select * From Premio Where PlanPremios = "+IdentificadorPlan);
        //  Se genera el objeto plan
        PlanPremios Plan = new PlanPremios(ListaPremios);        
        //  Se mapea el objeto Sorteo
        String[] DataSorteo = getRowToArray("Select * From Sorteo Where Numero = "+IdentificadorSorteo);
        Sorteo NuevoSorteo = new Sorteo(Integer.parseInt(DataSorteo[0]), DataSorteo[1], DataSorteo[2], DataSorteo[3], Integer.parseInt(DataSorteo[4]), Integer.parseInt(DataSorteo[5]), DataSorteo[6], Plan);               
        //  Se retorna el objeto sorteo
        return NuevoSorteo;
    }
    
    /**
     * Método que mapea una tabla completa de premios
     * @param Consulta tabla asociada a un plan de premios
     * @return una lista de premios
     */
    public ArrayList<Premio> MapeoPremio(String Consulta)
    {
        try{
            ResultSet TablaResultado = ConexionDB.getConexionDB().EjecutarConsulta(Consulta);                                    
            ArrayList<Premio> Lista = new ArrayList<Premio>();
            //  Se posiciona en la fila resultado de la consulta
            while (TablaResultado.next())// Recorre fila por fila la consulta
            {         
                int Identificador = Integer.parseInt(TablaResultado.getObject(1).toString());
                int Monto = Integer.parseInt(TablaResultado.getObject(2).toString());
                int Cantidad = Integer.parseInt(TablaResultado.getObject(3).toString());
                Premio Nuevo = new Premio(Identificador,Monto,Cantidad);                
                //  Setea la fila en el modelo de la tabla de muestra                              
                Lista.add(Nuevo);
            }            
            return Lista;
        }catch(Exception ex){            
            JOptionPane.showMessageDialog(null, "Ocurrió un error al conectarse a la base de datos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            return null;
        }          
    }
}
