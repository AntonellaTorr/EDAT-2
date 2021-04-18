/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import jerarquicas.ArbolBin;

/**
 *
 * @author Anto
 */
public class Estructuras {

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
       System.out.println (arbol.clone().toString());
    }
    
}
