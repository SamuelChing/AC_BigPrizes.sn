/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import java.util.Calendar;

/**
 *
 * @author Nahum
 */
public class Utilitarios {

    public Utilitarios() {
    }
    
    public boolean ValidarFecha(String Sorteo, String fecha)
    {
        String[] Date = fecha.split("/");        
        if(Sorteo.equals("Lotería")){                        
            return FechaLoteria(Date[2],Date[1],Date[0]);
        }else{            
            return FechaChance(Date[2],Date[1],Date[0]);
        }
    }
    
    public boolean FechaChance(String año, String mes, String dia)
    {
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(año),Integer.parseInt(mes), Integer.parseInt(dia));
        if(date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
            return true;
        else
            return false;
    }
    
    public boolean FechaLoteria(String año, String mes, String dia)
    {
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(año),Integer.parseInt(mes), Integer.parseInt(dia));
        if(date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        else
            return false;
    }
}
