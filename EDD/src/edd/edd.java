package edd;

import conjuntistas.dinamicas.ArbolBB;
import conjuntistas.dinamicas.ArbolAVL;
import jerarquicas.ArbolGen;
import conjuntistas.TablaHash;
import lineales.dinamicas.Lista;

public class edd {

    public static void main(String args[]) {
          
        ArbolBB a= new ArbolBB ();
        a.insertar('R');
        a.insertar('M');
        a.insertar('H');
        a.insertar('L');
        a.insertar('V');
        a.insertar('T');
        a.insertar('S');
        a.insertar('U');
        a.insertar('Y');
        /*
          8 
         3   10
        1 6    14
         4 7
        */
       /*
        ArbolAVL b= new ArbolAVL ();
        b.insertar(10);
        b.insertar(9);
        b.insertar(15);
        b.insertar(12);
        b.insertar(17);
        System.out.println (b.toString());
        b.eliminar(15);
*/
       /*
      ArbolGen a= new ArbolGen();
      a.insertar('A', null);
      a.insertar('M', 'A');
      a.insertar('N', 'M');
      a.insertar('C', 'A');
      a.insertar('M', 'A');
      a.insertar('P', 'M');
      
      a.insertar('N', 'C');
      a.insertar('L', 'F');
      a.insertar('O', 'L');
      */
      
      Lista lis= new Lista();
      lis.insertar('A', 1);
      lis.insertar('M', 2);
      lis.insertar('N',3);
      


    System.out.println (a.concatenarInordenDesde('W', 3));
        
                
        

      
      
       
      
      
    }
}
