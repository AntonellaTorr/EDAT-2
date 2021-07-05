/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp;
import Estructuras.MapeoAMuchos;
import Estructuras.TDB;
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
        MapeoAMuchos n= new MapeoAMuchos ();
        n.insertar(1,"ENERO" );
        n.insertar(1,"LUNES" );
        n.insertar(2,"FEBRERO" );
        n.insertar(2,"MARTES" );
        n.insertar(3,"MARZO" );
        n.insertar(3,"MIERCOLES" );
        n.insertar(4,"ABRIL" );
        n.insertar(4,"JUEVES" );
        n.insertar(5,"MAYO" );
        n.insertar(5,"VIERNES" );
        n.insertar(6,"JUNIO" );
        n.insertar(6,"SABADO" );
        n.insertar(7,"JULIO" );
        n.insertar(7,"DOMINGO" );
        n.insertar(8,"AGOSTO" );
        n.insertar(9,"SEPTIMEBRE" );
        n.insertar(10,"OCTUBRE" );
        n.insertar(11,"NOVIEMBRE" );
        n.insertar(12,"DICIEMBRE" );
        TablaDeBusqueda a= new TablaDeBusqueda();
        a.insertar(4, "Antonella");
        a.insertar(7, "Bruno");
        a.insertar(9, "Luis");
        a.insertar(15, "Camila");
        a.insertar(3, "Lucia");
        a.insertar(2, "Claudia");
        a.insertar(1, "Gustavo");
        TDB p= new TDB();
        p.insertar("ABB", "brunoktr");
        p.insertar("ACC", "bantonella");
        p.insertar("CCA", "crozas");
        p.insertar("MMM", "karinarozas");
        p.insertar("NNJ", "takki");
        p.insertar("XXX", "jose123");
        
        System.out.println ("INICIO TESTING MAPEO");
        System.out.println (n.obtenerValores(1).toString());
        System.out.println ("Espera LUNES y Enero");
        System.out.println ("Quita MARTES para dom 2 febrero");
        n.desasociar(2, "MARTES");
        System.out.println(n.obtenerValores(2).toString());
        System.out.println ("Espera Febrero");
        n.insertar(14, "cumple anto");
        n.insertar(14, "examen calculo");
        System.out.println ("Asocia cumple anto y examen calculo al nro 14");
        System.out.println (n.obtenerValores(14).toString());
        n.desasociar(3, "MIERCOLES");
        n.desasociar(3, "MARZO");
        System.out.println ("Desasocia miercoles y marzo del nro 3");
        System.out.println (n.obtenerValores(3).toString());
       
        
        
        
        
    }
    
}
