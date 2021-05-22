package edd;

import conjuntistas.dinamicas.ArbolBB;

public class edd {

    public static void main(String args[]) {
        ArbolBB a= new ArbolBB ();
        a.insertar(10);
        a.insertar(7);
        a.insertar(4);
        a.insertar(6);
        a.insertar(15);
        a.insertar(12);
        a.insertar(17);
        System.out.println (a.toString());
        a.eliminar(10);
        System.out.println (a.toString());
    }

}