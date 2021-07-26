package edd;

import conjuntistas.dinamicas.ArbolAVL;

public class edd {

    public static void main(String args[]) {
        ArbolAVL a= new ArbolAVL ();
        a.insertar(14);
        a.insertar(7);
        a.insertar(6);  
        a.insertar(10); 
        a.insertar(12); 
        a.insertar(2);  
        a.insertar(4);  
        a.insertar(1);  
        a.insertar(3);  
        a.insertar(5);  
        a.insertar(9); 
        a.insertar(8); 
        a.insertar(17); 
        a.insertar(19);
       
        a.insertar(11);
        a.insertar(13);
        a.insertar(18);
        a.insertar(20);
        a.insertar(15);
        a.insertar(16);
       
        System.out.println (a.toString());
       
        
        
          
          
      
    }
}
