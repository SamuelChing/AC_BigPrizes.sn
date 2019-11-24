/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase para gestionar el juego de un sorteo
 * @author Nahum
 */
public class Sorteo {
    
    private int Numero;
    private String Leyenda;
    private String Fecha;
    private String Tipo;
    private int CantidadFracciones;
    private int PrecioBillete;
    private String Estado;
    private PlanPremios Plan;

    public Sorteo(int Numero, String Leyenda, String Fecha, String Tipo, int CantidadFracciones, int PrecioBillete, String Estado, PlanPremios Plan) {
        this.Numero = Numero;
        this.Leyenda = Leyenda;
        this.Fecha = Fecha;
        this.Tipo = Tipo;
        this.CantidadFracciones = CantidadFracciones;
        this.PrecioBillete = PrecioBillete;
        this.Estado = Estado;
        this.Plan = Plan;              
    }

    public String[] GirarTombola()
    {
        ArrayList<Premio> Lista = this.Plan.getListaPremios();
        if(Lista.size()>0){
            Random random = new Random();
            int Numero = random.nextInt(99)+1;
            int Serie = random.nextInt(999)+1;
            int NumeroPremio = random.nextInt(Lista.size());
            int Monto = Lista.remove(NumeroPremio).getMonto();
            String[] Array = new String[]{""+Numero,""+Serie,""+Monto};
            return Array;
        }
        return null;                                
    }

    public int getNumero() {
        return Numero;
    }

    public String getTipo() {
        return Tipo;
    }
    
    
}
