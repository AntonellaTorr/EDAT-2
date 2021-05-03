/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd;

import jerarquicas.ArbolGen;
import jerarquicas.NodoGen;
/**
 *
 * @author Anto
 */
public class edd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArbolGen a = new ArbolGen();
        a.insertar('A', null);
        a.insertar('B', 'A');
        a.insertar('F', 'A');
        a.insertar('E', 'A');
        a.insertar('C', 'B');
        a.insertar('D', 'B');
        a.insertar('G', 'F');
        System.out.println(a.listarInorden().toString()); 
        System.out.println(a.listarPosorden().toString()); 
        System.out.println(a.listarPorNiveles().toString()); 
        System.out.println(a.listarPreorden().toString()+"\n"); 
        
        System.out.println (a.toString()+"\n");
        ArbolGen b=a.clone();    
        System.out.println (b.toString());
        
    }
    
}
