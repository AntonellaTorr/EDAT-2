package edd;

import conjuntistas.dinamicas.ArbolBB;
import conjuntistas.dinamicas.ArbolAVL;
import jerarquicas.ArbolGen;
import conjuntistas.TablaHash;
import lineales.dinamicas.Lista;

public class edd {

    public static void main(String args[]) {
          
        ArbolBB a= new ArbolBB ();
        a.insertar(8);
        a.insertar(3);
        a.insertar(1);
        a.insertar(6);
        a.insertar(4);
        a.insertar(7);
        a.insertar(10);
        a.insertar(14);
       /*
        ArbolAVL b= new ArbolAVL ();
        b.insertar(10);
        b.insertar(9);
        b.insertar(15);
        b.insertar(12);
        b.insertar(17);
        System.out.println (b.toString());
        b.eliminar(15);

      ArbolGen a= new ArbolGen();
      a.insertar('A', null);
      a.insertar('C', 'A');
      a.insertar('F', 'A');
      a.insertar('M', 'A');
      a.insertar('N', 'C');
      a.insertar('L', 'F');
      a.insertar('O', 'L');
      Lista lis= new Lista();
      lis.insertar('A', 1);
      lis.insertar('F', 2);
      lis.insertar('L',3);
      System.out.println (a.listarHastaNivel(2).toString());
      */
       
        System.out.println (a.toString());
        System.out.println(a.clonarIn(3));
        //System.out.println (a.toString());
        
                
        

      
      
       
      
      
    }

}