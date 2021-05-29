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
        b.insertar(10);
        b.insertar(9);
        b.insertar(15);
        b.insertar(12);
        b.insertar(17);
        System.out.println (b.toString());
        b.eliminar(15);
        
        
        System.out.println (b.toString());
      
      
    }

}