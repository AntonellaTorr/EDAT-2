package edd;

import jerarquicas.dinamicas.ArbolGen;
import conjuntistas.ArbolHeap;
import conjuntistas.dinamicas.ArbolBB;
public class edd{

 

    public static void main(String args[]) {
        ArbolBB h= new ArbolBB ();
        System.out.println (h.esVacio());
        h.insertar(7);
        h.insertar(10);
        h.insertar(5);
        h.insertar(4);
       /* 7
         5  10
        4
        */
       System.out.println (h.esVacio());
       System.out.println ("Debe devolver: [4,5,7,10]==> "+h.listar().toString());
       System.out.println (h.minElem());
       System.out.println (h.maxElem());
       System.out.println (h.pertenece(4));
       System.out.println (h.pertenece(0));
       ArbolBB a=h.clone();
       System.out.println (h.toString());
       System.out.println (a.toString());
    }

}