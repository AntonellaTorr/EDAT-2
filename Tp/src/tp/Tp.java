/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp;
import Estructuras.TablaDeBusqueda;

/**
 *
 * @author Anto
 */
public class Tp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TablaDeBusqueda n= new TablaDeBusqueda ();
        n.insertar(10,"Antonella" );
        n.insertar(9,"Luis" );
        n.insertar(8,"Clau" );
        n.insertar(7,"Bruno" );
        n.insertar(6,"MI" );
        System.out.println (n.toString());
        n.eliminar(10);
        System.out.println ("-------------------------------------------------------------------");
        System.out.println (n.toString());
        n.eliminar(9);
        System.out.println ("-------------------------------------------------------------------");
        System.out.println (n.toString());
        
        
        
    }
    
}
