/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

/**
 * Clase para gestionar un premio
 * @author Nahum
 */
public class Premio {
    
    private int Identificador;
    private int Monto;
    private int Cantidad;

    public Premio(int Identificador, int Monto, int Cantidad) {
        this.Identificador = Identificador;
        this.Monto = Monto;
        this.Cantidad = Cantidad;        
    }

    

    public int getMonto() {
        return Monto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad() {
        this.Cantidad = Cantidad-1;
    }

    
}
