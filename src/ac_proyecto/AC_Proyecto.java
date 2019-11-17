/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import Controlador.ControladorDataBase;
import Frontend.Ventana_Login;
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
        ControladorDataBase.CrearControlador();
        
        //  Se crea la ventana inicial
        //Ventana_Login login = new Ventana_Login();
        //login.setVisible(true); 
        ControladorDataBase.getControlador().LlenarTablaConsulta("Select * From Usuario", null);
    }
    
}
