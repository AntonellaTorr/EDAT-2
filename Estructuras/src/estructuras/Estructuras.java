/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import jerarquicas.ArbolBin;
import lineales.estaticas.Cola;
import lineales.dinamicas.Lista;

public class Estructuras {
    static String sOk = "OK!", sErr = "ERROR";
    public static void main(String[] arg) {
        testCola();
    }
    public static void testCola() {
		System.out.println("COMIENZO TEST COLA");
		Cola q1 = new Cola();
		System.out.println("Cola vac�a: \t\t\t\t\t\t\t--> " + q1.toString());
		boolean exito = true;

		System.out.println("Si es cola estatica tama�o <= 10 se debe llenar");
		int num = 1;
		while (num < 12) {
			if (num < 10) {
				System.out.print("Pone " + num + " espera true: \t\t\t\t\t" + ((q1.poner(num) == true) ? sOk : sErr));
			} else {
				System.out.print("Pone " + num + " espera false en estatica y true en dinamica : \t" + q1.poner(num));
			}
			num++;
			System.out.println("\t--> " + q1.toString());
		}
		System.out
				.println("Recupera frente espera 1 recupera: \t\t\t" + (((int) q1.obtenerFrente() == 1) ? sOk : sErr));

		System.out.print("Saca espera true : \t\t\t\t\t" + ((q1.sacar() == true) ? sOk : sErr));
		System.out.println("\t--> " + q1.toString());
		System.out.println("Recupera frente espera 2 recupera \t\t\t" + (((int) q1.obtenerFrente() == 2) ? sOk : sErr));
		System.out.print("Saca espera true: \t\t\t\t\t" + ((q1.sacar() == true) ? sOk : sErr));
		System.out.println("\t--> " + q1.toString());
		System.out
				.println("Recupera frente espera 3 recupera: \t\t\t" + (((int) q1.obtenerFrente() == 3) ? sOk : sErr));
		System.out.print("Pone 23 espera true: \t\t\t\t\t" + ((q1.poner(23) == true) ? sOk : sErr));
		System.out.println("\t--> " + q1.toString());
		System.out.print("Pone 24 espera true: \t\t\t\t\t" + ((q1.poner(24) == true) ? sOk : sErr));
		System.out.println("\t--> " + q1.toString());
		System.out
				.println("Recupera frente espera 3 recupera: \t\t\t" + (((int) q1.obtenerFrente() == 3) ? sOk : sErr));

		Cola q2 = q1.clone();
		System.out.println("Copia espera [3 4 5 6 7 8 9 <10 11> 23 24]: \t\t\t--> " + q2.toString());

		System.out.println("Pone 7 espera false en estatica, true en dinamica: \t" + q1.poner(7));
		System.out.print("Pone 8 espera false en estatica, true en dinamica: \t" + q1.poner(8));
		System.out.println("\t--> " + q1.toString());

		while (!q1.esVacia()) {
			System.out.print("Saca " + q1.obtenerFrente() + " de cola espera true: \t\t\t\t"
					+ ((q1.sacar() == true) ? sOk : sErr));
			System.out.println("\t--> " + q1.toString());
		}
		System.out.print("Se vacio la cola q1");
		System.out.println("\t\t\t\t\t\t--> " + q1.toString());
		System.out.println("Sacar en cola vacia espera false: \t\t\t" + ((q1.sacar() == false) ? sOk : sErr));
		System.out.println(
				"Recupera frente en cola vacia espera null: \t\t" + ((q1.obtenerFrente() == null) ? sOk : sErr));

		System.out.println("Verifica copia guardada espera [3 4 5 6 7 8 9 <10 11> 23 24]: \t--> " + q2.toString());
		System.out.println(
				"Pone 27 espera true en dinamica y false en estatica: \t" + ((q2.poner(27) == true) ? sOk : sErr));
		System.out.println("Verifica copia espera [3 4 5 6 7 8 9 <10 11> 23 24 <27>]: \t--> " + q2.toString());
		System.out.println(
				"Saca " + q2.obtenerFrente() + " de cola espera true: \t\t\t\t" + ((q2.sacar() == true) ? sOk : sErr));
		System.out.println("Verifica copia espera [4 5 6 7 8 9 <10 11> 23 24 <27>]: \t--> " + q2.toString());
		System.out.println(
				"Saca " + q2.obtenerFrente() + " de cola espera true: \t\t\t\t" + ((q2.sacar() == true) ? sOk : sErr));
		System.out.println("Verifica copia espera [5 6 7 8 9 <10 11> 23 24 <27>]: \t\t--> " + q2.toString());
		System.out.println(
				"Saca " + q2.obtenerFrente() + " de cola espera true:\t\t\t\t" + ((q2.sacar() == true) ? sOk : sErr));
		System.out.println("Verifica copia espera [6 7 8 9 <10 11> 23 24 <27>]: \t\t--> " + q2.toString());
		System.out.println("Pone 28: \t\t\t\t\t\t" + ((q2.poner(28) == true) ? sOk : sErr));
		System.out.println("Pone 29: \t\t\t\t\t\t" + ((q2.poner(29) == true) ? sOk : sErr));
		System.out.println("Verifica copia espera [6 7 8 9 <10 11> 23 24 <27> 28 29]: \t--> " + q2.toString());
		q2.vaciar();
		System.out.println("Vacia copia espera []: \t\t\t\t\t\t--> " + q2.toString());

	}
    public void testArbol (){
    ArbolBin arbol= new ArbolBin ();
        System.out.println (arbol.toString());
        System.out.println ("Inserta 1 como raiz del arbol, espera: true==>"+ arbol.insertar(1,null, 'I'));
        System.out.println ("Inserta 2 como hijo izquierdo de 1, espera: true==>"+ arbol.insertar(2,1, 'I'));
        System.out.println ("Inserta 3 como hijo derecho de 1, espera: true==>"+ arbol.insertar(3,1, 'D'));
        System.out.println ("Inserta 4 como hijo izquierdo de 2, espera: true==>"+arbol.insertar(4,2, 'I'));        
        System.out.println ("Inserta 5 como hijo derecho de 2, espera: true==>"+ arbol.insertar(5,2, 'D'));        
        System.out.println ("Inserta 6 como hijo izquierdo de 3, espera: true==>"+ arbol.insertar(6,3, 'I'));       
        System.out.println ("Inserta 6 como hijo izquierdo de 3, espera: false==>"+ arbol.insertar(6,3, 'I')+"\n");
        System.out.println ("Muestra el arbol:\n" +arbol.toString());
        System.out.println ("Altura: espera 2 => " +arbol.altura());
        System.out.println ("Nivel del elemento 1: espera 0 => "+arbol.nivel(1));
        System.out.println ("Nivel del elemento 2: espera 1 => "+arbol.nivel(2));
        System.out.println ("Nivel del elemento 3: espera 1 => "+arbol.nivel(3));
        System.out.println ("Nivel del elemento 6: espera 2 => "+arbol.nivel(6));
        System.out.println ("Padre del nodo 1: espera null =>" +arbol.padre(1));
        System.out.println ("Padre del nodo 2: espera 1=>" +arbol.padre(2));
        System.out.println ("Padre del nodo 6: espera 3=>" +arbol.padre(6));
        System.out.println ("Listar preorden: espera [1,2,4,5,3,6]=>" +arbol.listarPreorden().toString());
        System.out.println ("Listar posorden: espera [4,5,2,6,3,1]=>" +arbol.listarPosorden().toString());
        System.out.println ("Listar inorden: espera [4,2,5,1,6,3]=>" +arbol.listarInorden().toString());
        System.out.println ("Listar por niveles: espera [1,2,3,4,5,6]=>" +arbol.listarNiveles().toString());
        System.out.println ("Verifica si el arbol esta vacio: espera false"+arbol.esVacio());
        System.out.println ("Vacia el arbol"); arbol.vaciar();
        System.out.println ("Verifica si el arbol esta vacio: espera true"+arbol.esVacio());
        System.out.println ("Inserta A como raiz del arbol, espera: true==>"+ arbol.insertar('A',null, 'I'));
        System.out.println ("Inserta B como hijo izquierdo de A, espera: true==>"+ arbol.insertar('B','A', 'I'));
        System.out.println ("Inserta C como hijo derecho de A, espera: true==>"+ arbol.insertar('C','A', 'D'));
        System.out.println ("Inserta D como hijo izquierdo de B, espera: true==>"+arbol.insertar('D','B', 'I'));        
        System.out.println ("Inserta E como hijo derecho de B, espera: true==>"+ arbol.insertar('E','B', 'D')); 
        System.out.println ("Inserta F como hijo derecho de B, espera: false ==>"+ arbol.insertar('F','B', 'D') +"\n"); 
        System.out.println ("Muestra el arbol: \n" +arbol.toString());
        System.out.println ("Clona el arbol \n"+arbol.clone().toString());

    }


}



