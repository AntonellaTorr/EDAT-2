package edd;

import jerarquicas.dinamicas.ArbolGen;
import conjuntistas.ArbolHeap;

public class edd{

 

    public static void main(String args[]) {
        ArbolHeap h= new ArbolHeap ();
        h.insertar(7);
        h.insertar(6);
        h.insertar(5);
        h.insertar(4);
       
        System.out.println (h.recuperarCima());
        System.out.println(h.toString());
    }

}