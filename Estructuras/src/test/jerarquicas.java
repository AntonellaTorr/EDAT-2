/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import jerarquicas.ArbolBin;

/**
 *
 * @author Anto
 */
public class jerarquicas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArbolBin arbol= new ArbolBin ();
        arbol.insertar('A', null, 'I');
        arbol.insertar('B', 'A', 'I');
        arbol.insertar('C', 'A', 'D');
        arbol.insertar('D', 'B', 'I');
        arbol.insertar('E', 'C', 'I');
        arbol.insertar('F', 'C', 'D');
        arbol.insertar('G', 'E', 'I');
        arbol.insertar('H', 'E', 'D');
        
       System.out.println (arbol.toString());
        
        
        System.out.println ("Espera: [A,B,D,C,E,G,H,F]");
        System.out.println (arbol.listarPreorden().toString());
        System.out.println ("Espera: [D,B,A,G,E,H,C,F]");
        System.out.println (arbol.listarInorden().toString());
        System.out.println ("Espera: [D,B,G,H,E,F,C,A]");
        System.out.println (arbol.listarPosorden().toString());
        
    }
    
}

