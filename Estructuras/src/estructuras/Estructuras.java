/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import jerarquicas.ArbolBin;

public class Estructuras {
    public static void main(String[] arg) {
        ArbolBin arbol= new ArbolBin ();
        arbol.insertar(1,null, 'I');
        arbol.insertar(2,1, 'I');
        arbol.insertar(3,1, 'D');
        arbol.insertar(4,2, 'I');
        arbol.insertar(5,2, 'D');
        arbol.insertar(6,3, 'I');
        /*  1
          2   3
        4  5 6
        */
        System.out.println (arbol.listarNiveles().toString());
    }


}



