/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac_proyecto;

import java.util.ArrayList;
import javax.swing.JTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nahal
 */
public class UtilitariosTest {
    
    public UtilitariosTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of ValidarFecha method, of class Utilitarios.
     */
    @Test
    public void testValidarFecha() {
        System.out.println("ValidarFecha");
        String Sorteo = "";
        String año = "";
        String mes = "";
        String dia = "";
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.ValidarFecha(Sorteo, año, mes, dia);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FechaChance method, of class Utilitarios.
     */
    @Test
    public void testFechaChance() {
        System.out.println("FechaChance");
        String año = "";
        String mes = "";
        String dia = "";
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.FechaChance(año, mes, dia);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FechaLoteria method, of class Utilitarios.
     */
    @Test
    public void testFechaLoteria() {
        System.out.println("FechaLoteria");
        String año = "";
        String mes = "";
        String dia = "";
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.FechaLoteria(año, mes, dia);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PremiosMontoCantidad method, of class Utilitarios.
     */
    @Test
    public void testPremiosMontoCantidad() {
        System.out.println("PremiosMontoCantidad");
        JTable Tabla = null;
        String Tipo = "";
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.PremiosMontoCantidad(Tabla, Tipo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of TresNumeros method, of class Utilitarios.
     */
    @Test
    public void testTresNumeros() {
        System.out.println("TresNumeros");
        JTable Tabla = null;
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.TresNumeros(Tabla);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of AlgunoIgual method, of class Utilitarios.
     */
    @Test
    public void testAlgunoIgual() {
        System.out.println("AlgunoIgual");
        JTable Tabla = null;
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.AlgunoIgual(Tabla);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MayorQuePremiosMayores method, of class Utilitarios.
     */
    @Test
    public void testMayorQuePremiosMayores() {
        System.out.println("MayorQuePremiosMayores");
        ArrayList<Integer> Lista = null;
        int Valor = 0;
        Utilitarios instance = new Utilitarios();
        boolean expResult = false;
        boolean result = instance.MayorQuePremiosMayores(Lista, Valor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
