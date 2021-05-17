package edd;

import jerarquicas.ArbolGen;
import conjuntistas.ArbolHeap;

public class edd{

 

    public static void main(String args[]) {
        ArbolHeap h= new ArbolHeap ();
        h.insertar(3);
        h.insertar(1);
        h.insertar(5);
       
        System.out.println (h.recuperarCima());
        System.out.println (h.toString());
    }

}