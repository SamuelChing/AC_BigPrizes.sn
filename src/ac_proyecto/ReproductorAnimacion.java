/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import Controlador.ControladorDB;
import Controlador.ControladorGUI;
import Frontend.Ventana;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Nahum
 */
public class ReproductorAnimacion extends Thread{

    private String Sonido;
    private JLabel Imagen;
    private JLabel Numero;
    private JLabel Serie;
    private JLabel Premio;
    private Sorteo Sorteo;
    private Ventana ventana;    
    private JTable TablaUpdate;

    public ReproductorAnimacion(String Sonido, JLabel Imagen, JLabel Numero, JLabel Serie, JLabel Premio, Sorteo Sorteo, Ventana ventana, JTable TablaUpdate) {
        this.Sonido = Sonido;
        this.Imagen = Imagen;
        this.Numero = Numero;
        this.Serie = Serie;
        this.Premio = Premio;
        this.Sorteo = Sorteo;
        this.ventana = ventana;
        this.TablaUpdate = TablaUpdate;        
    }
    
    @Override
    public void run()
    {        
        //  Se muestra la tombola
        Imagen.setVisible(true);
        Imagen.update(Imagen.getGraphics());
        String[] TempArray = Sorteo.GirarTombola();
        while(TempArray != null)
        {               
            //  Se da play al efecto
            Sonido("Loteria.mp3");
            //  Se muestran los números            
            Numero.setText("Número: "+TempArray[0]);
            Serie.setText("Serie: "+TempArray[1]);
            Premio.setText("Premio: "+TempArray[2]);
            //  Se despliegan en pantalla
            Numero.update(Numero.getGraphics());
            Serie.update(Serie.getGraphics());
            Premio.update(Premio.getGraphics());
            //  Play efecto premio
            Sonido("Premio.mp3");
            //  Se guardan los datos del ganador            
            ControladorDB.getControlador().ManejoGanadores(1,Sorteo.getNumero(),Sorteo.getTipo(),Integer.parseInt(TempArray[0]),Integer.parseInt(TempArray[1]),Integer.parseInt(TempArray[2]));
            ControladorGUI.getControlador().LlenarTablaConsulta("Select * From Ganador Where NumeroSorteo = "+Sorteo.getNumero(), TablaUpdate);
            //  Se continúa con el siguiente premio
            TempArray = Sorteo.GirarTombola();
        }
        Imagen.setVisible(false);
        Imagen.update(Imagen.getGraphics());  
        ControladorDB.getControlador().ManejoSorteo(3, "", "12/10/15", "", 0, 0, Sorteo.getNumero());            
        ventana.Recargar();
    }    
    public void Sonido(String Sonido)
    {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(Sonido));
            Player player = new Player(buffer);
            player.play();
        } catch(Exception ex){            
            this.interrupt();
        }
    }
}
