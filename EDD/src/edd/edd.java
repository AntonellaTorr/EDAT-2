package edd;

import Grafos.Grafo;

public class edd {

    public static void main(String args[]) {
        Grafo g= new Grafo ();
        g.insertarVertice('A');
        g.insertarVertice('C');
        g.insertarVertice('D');
        g.insertarVertice('E');
        g.insertarVertice('F');
        g.insertarVertice('G');
        g.insertarVertice('H');
        g.insertarVertice('B');
        
        g.insertarArco( 'A', 'B', 0);
        g.insertarArco( 'B', 'D', 0);
        g.insertarArco( 'B', 'F', 0);
        g.insertarArco( 'D', 'G', 0);
        g.insertarArco( 'D', 'E', 0);
        g.insertarArco( 'G', 'E', 0);
        g.insertarArco( 'C', 'A', 0);
        g.insertarArco( 'C', 'F', 0);
        g.insertarArco( 'C', 'H', 0);
        
        System.out.println (g.listarEnAnchura().toString());
        
        
        
          
          
      
    }
}
