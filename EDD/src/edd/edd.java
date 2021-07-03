package edd;

import Grafos.Grafo;

public class edd {

    public static void main(String args[]) {
        Grafo g= new Grafo ();
        g.insertarVertice('A');
        g.insertarVertice('B');
        g.insertarVertice('C');
        g.insertarVertice('D');
        g.insertarVertice('E');
        g.insertarArco( 'A', 'B', 1);
        g.insertarArco( 'B', 'E', 1);
        g.insertarArco( 'C', 'D', 1);
        System.out.println (g.existeCamino('E', 'C'));
        System.out.println (g.listarEnProfundidad().toString());
        
          
          
      
    }
}
