/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import Controlador.ControladorDB;
import Controlador.ControladorGUI;
import Frontend.Login;
import Frontend.Ventana;
import java.sql.SQLException;

/**
 *
 * @author Samuel
 */
public class AC_Proyecto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //  Se crea los objetos Singleton
        ControladorDB.CrearControlador();
        ControladorGUI.CrearControlador();
        
        //  Se crea la ventana inicial
        Login login = new Login();
        login.setVisible(true); 
        //Ventana V1 = new Ventana();
        //V1.setVisible(true);
    }
    
}
