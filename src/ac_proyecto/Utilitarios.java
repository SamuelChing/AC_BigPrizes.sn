/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import Controlador.ControladorGUI;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Creado con el fin de validar las cosas importantes de sorteos y planes de premio
 * @author SAMUEL
 */
public class Utilitarios {

    public Utilitarios() {
    }
    /**
     * Método que valida las fechas de un sorteo
     * @param Sorteo
     * @param fecha
     * @return true o false
     */
    public boolean ValidarFecha(String Sorteo, String año, String mes, String dia)
    {        
        if(Sorteo.equals("Lotería")){                        
            return FechaLoteria(año,mes,dia);
        }else{            
            return FechaChance(año,mes,dia);
        }
    }
    
    /**
     * Método que valida la fecha de un sorteo chance
     * @param año
     * @param mes
     * @param dia
     * @return true o false
     */
    public boolean FechaChance(String año, String mes, String dia)
    {
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(año)+2000,Integer.parseInt(mes)-1, Integer.parseInt(dia));
        if(date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
            return true;
        else
            return false;
    }
    
    /**
     * Método que valida la fecha de un sorteo lotería
     * @param año
     * @param mes
     * @param dia
     * @return true o false
     */
    public boolean FechaLoteria(String año, String mes, String dia)
    {
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(año)+2000,Integer.parseInt(mes)-1, Integer.parseInt(dia));
        if(date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        else
            return false;
    }
    
    /**
     * Método que valida los montos y premios de un plan
     * @param Tabla
     * @param Tipo
     * @return true o false
     */
    public boolean PremiosMontoCantidad(JTable Tabla, String Tipo)
    {        
        if(Tipo.equals("Lotería")){
            if(TresNumeros(Tabla) && AlgunoIgual(Tabla)){
                return true;
            }else{                
                return false;
            }
        }else{
            if(TresNumeros(Tabla)){
                return true;
            }
            else{                
                return false;
            }
        }        
    }
    /**
     * Método que valida los tres premios principales
     * @param Tabla
     * @return true o false
     */
    public boolean TresNumeros(JTable Tabla)
    {         
        ArrayList<String> PremiosGordos = new ArrayList<String>();
        for(int i = 0; i<Tabla.getRowCount();i++)
        {
            if(Tabla.getValueAt(i, 1).toString().equals("1"))
            {                
                PremiosGordos.add(Tabla.getValueAt(i, 0).toString());                
            }
        }
        if(PremiosGordos.size()==3)
        {            
            if(PremiosGordos.get(0).equals(PremiosGordos.get(1)) || PremiosGordos.get(1).equals(PremiosGordos.get(2)))
            { 
                JOptionPane.showMessageDialog(null, "Error, error primeros tres premios no definidos correctamente", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else
            {                
                return true;
            }            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error, debe agregar 3 premios principales", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    /**
     * Método que valida si hay premios repetidos
     * @param Tabla
     * @return true o false
     */
    public boolean AlgunoIgual(JTable Tabla)
    {        
        ArrayList<Integer> PremiosGordos = new ArrayList<Integer>();
        for(int i = 0; i<Tabla.getRowCount();i++)
        {
            if(Tabla.getValueAt(i, 1).toString().equals("1"))
            {                
                PremiosGordos.add(Integer.parseInt(Tabla.getValueAt(i, 0).toString()));
            }
        }
        
        ArrayList<String> Lista = new ArrayList<String>();
        for(int i = 0; i<Tabla.getRowCount();i++)
        {
            if(!Tabla.getValueAt(i, 1).toString().equals("1"))
            {
                if(Lista.contains(Tabla.getValueAt(i, 0).toString()))
                {           
                    JOptionPane.showMessageDialog(null, "Error, error montos de premios repetidos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(MayorQuePremiosMayores(PremiosGordos, Integer.parseInt(Tabla.getValueAt(i, 0).toString())))
                {
                    JOptionPane.showMessageDialog(null, "Error, error no puede haber montos mayor o iguales que los premios principales", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                Lista.add(Tabla.getValueAt(i, 0).toString());
            }               
        }
        return true;
    }
    
    /**
     * Método que valida si hay algún premio mayor que los principales 3
     * @param Lista
     * @param Valor
     * @return true o false
     */
    public boolean MayorQuePremiosMayores(ArrayList<Integer> Lista, int Valor)
    {
        for (Integer integerTemp : Lista) {            
            if(integerTemp <= Valor)
            {
                return true;
            }
        }
        return false;
    }
}
