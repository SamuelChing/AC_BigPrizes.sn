/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import java.util.ArrayList;

/**
 * Clase para gestionar un plan de premios
 * @author Nahum
 */
public class PlanPremios {
    
    private ArrayList<Premio> ListaPremios;

    public PlanPremios(ArrayList<Premio> ListaPremios) {
        this.ListaPremios = ListaPremios;
    }

    public ArrayList<Premio> getListaPremios() {
        return ListaPremios;
    }
    
}
