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
        System.out.println (arbol.toString());
        System.out.println ("Inserta 1 como raiz del arbol, espera: true==>"+ arbol.insertar(1,null, 'I'));
        arbol.insertar(1,null, 'I');
        System.out.println ("Inserta 2 como hijo izquierdo de 1, espera: true==>"+ arbol.insertar(2,1, 'I'));
        System.out.println ("Inserta 3 como hijo derecho de 1, espera: true==>"+ arbol.insertar(3,1, 'D'));
        System.out.println ("Inserta 4 como hijo izquierdo de 2, espera: true==>"+arbol.insertar(4,2, 'I'));        
        System.out.println ("Inserta 5 como hijo derecho de 2, espera: true==>"+ arbol.insertar(5,2, 'D'));        
        System.out.println ("Inserta 6 como hijo izquierdo de 3, espera: true==>"+ arbol.insertar(6,3, 'I'));       
        System.out.println ("Inserta 6 como hijo izquierdo de 3, espera: false==>"+ arbol.insertar(6,3, 'I'));
        System.out.println ("Muestra el arbol:" +arbol.toString());
        System.out.println ("Altura: espera 2 => " +arbol.altura());
        System.out.println ("Nivel del elemento 1: espera 0 => "+arbol.nivel(1));
        System.out.println ("Nivel del elemento 2: espera 1 => "+arbol.nivel(2));
        System.out.println ("Nivel del elemento 3: espera 1 => "+arbol.nivel(3));
        System.out.println ("Nivel del elemento 6: espera 2 => "+arbol.nivel(6));
        System.out.println ("Padre del nodo 1: espera null =>" +arbol.padre(1));
        System.out.println ("Padre del nodo 2: espera 1=>" +arbol.padre(2));
        System.out.println ("Padre del nodo 6: espera 3=>" +arbol.padre(3));
        System.out.println ("Listar preorden: espera [1,2,4,5,3,6]=>" +arbol.listarPreorden().toString());
        System.out.println ("Listar posorden: espera [4,5,2,6,3,1]=>" +arbol.listarPosorden().toString());
        System.out.println ("Listar inorden: espera [4,2,5,1,6,3]=>" +arbol.listarInorden().toString());
        System.out.println ("Listar por niveles: espera [1,2,3,4,5,6]=>" +arbol.listarNiveles().toString());
        System.out.println ("Verifica si el arbol esta vacio: espera false"+arbol.esVacio());
        
        
        
        

        /*  1
          2   3
        4  5 6
        */
        System.out.println (arbol.listarNiveles().toString());
    }


}



