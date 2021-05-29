package edd;

import conjuntistas.dinamicas.ArbolBB;
import conjuntistas.dinamicas.ArbolAVL;


public class edd {

    public static void main(String args[]) {
//        ArbolBB a= new ArbolBB ();
//        a.insertar(30);
//        a.insertar(20);
//        a.insertar(40);
//        a.insertar(22);
//        a.insertar(23);
        ArbolAVL b= new ArbolAVL ();
        b.insertar(12);
        b.insertar(5);
        b.insertar(23);
        b.insertar(3);
        b.insertar(8);
        System.out.println (b.toString());
        b.insertar(10);
        
        
        System.out.println (b.toString());
      
      
    }

}