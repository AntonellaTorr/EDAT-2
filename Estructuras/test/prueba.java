/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anto
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
	    public static void testLista() {
	        System.out.println("***************COMIENZO TEST LISTA***************");
	        Lista l1 = new Lista();
	        System.out.println("Muestra lista vacia: \t\t\t\t\t--> " + l1.toString());
	        System.out.println("Longitud de lista vacia:\t\t\t" + l1.longitud());
	        System.out.print("Inserta 5 pos 5 espera FALSE: \t\t\t" + ((l1.insertar(5, 5) == false) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Inserta 2 pos 1 espera TRUE: \t\t\t" + ((l1.insertar(2, 1) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Inserta 1 pos 1 espera TRUE: \t\t\t" + ((l1.insertar(1, 1) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Inserta 3 pos long+1 espera TRUE: \t\t" + ((l1.insertar(3, l1.longitud() + 1) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Inserta 5 pos 4 espera TRUE: \t\t\t" + ((l1.insertar(5, 4) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Inserta 4 pos 4 espera TRUE: \t\t\t" + ((l1.insertar(4, 4) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.println("Inserta 10 pos 0 espera FALSE: \t\t\t" + ((l1.insertar(10, -1) == false) ? sOk : sErr));
	        System.out.println("Inserta 10 pos -1 espera FALSE: \t\t" + ((l1.insertar(10, -1) == false) ? sOk : sErr));
	        System.out.print("Inserta 10 pos long+2 espera FALSE: \t\t" + ((l1.insertar(10, l1.longitud() + 2) == false) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());

	        int longi = l1.longitud();
	        for (int i = -1; i <= longi + 2; i++) {
	            if (i > 0 && i <= longi) {
	                System.out.println("Recupera " + i + " \tespera " + i + " retorna " + l1.recuperar(i) + ":\t\t" + (((int) l1.recuperar(i) == i) ? sOk : sErr));
	            } else {
	                System.out.println("Recupera " + i + " \tespera NULL retorna " + l1.recuperar(i) + ":\t" + ((l1.recuperar(i) == null) ? sOk : sErr));
	            }
	        }

	        longi = l1.longitud();
	        for (int i = -1; i <= longi + 2; i++) {
	            if (i > 0 && i <= longi) {
	                System.out.println("Localiza " + i + " \tespera " + i + " retorna " + l1.localizar(i) + ":\t\t" + ((l1.localizar(i) == i) ? sOk : sErr));
	            } else {
	                System.out.println("Localiza " + i + " \tespera -1 retorna " + l1.localizar(i) + ":\t\t" + ((l1.localizar(i) == -1) ? sOk : sErr));
	            }
	        }

	        System.out.print("Elimina pos -1 espera FALSE: \t\t\t" + ((l1.eliminar(-1) == false) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Elimina pos 0 espera FALSE: \t\t\t" + ((l1.eliminar(0) == false) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Elimina pos 10 espera FALSE: \t\t\t" + ((l1.eliminar(10) == false) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Elimina pos 1 espera TRUE y 2,3,4,5: \t\t" + ((l1.eliminar(1) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Elimina pos 3 espera TRUE y 2,3,5: \t\t" + ((l1.eliminar(3) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Elimina ultimo espera TRUE y 2,3: \t\t" + ((l1.eliminar(l1.longitud()) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Inserta 9 pos 2 espera TRUE y 2,9,3: \t\t" + ((l1.insertar(9, 2) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.println("Recupera pos 5 espera NULL: \t\t\t" + ((l1.recuperar(5) == null) ? sOk : sErr));

	        Lista l2 = l1.clone();
	        System.out.println("Copia espera [2,9,3]: \t\t\t\t\t--> " + l2.toString());

	        System.out.print("Inserta 10 pos 1 espera [10,2,9,3]: \t\t" + ((l1.insertar(10, 1) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());
	        System.out.print("Elimina pos 4 espera true y 10,2,9: \t\t" + ((l1.eliminar(4) == true) ? sOk : sErr));
	        System.out.println("\t--> " + l1.toString());

	        while (!l1.esVacia()) {
	            System.out.print("Elimina cabecera varias veces hasta vaciar: \t" + ((l1.eliminar(1) == true) ? sOk : sErr));
	            System.out.println("\t--> " + l1.toString());
	        }

	        System.out.print("Se vacio la lista l1, espera ver lista vacia");
	        System.out.println("\t\t--> " + l1.toString());
	        System.out.println("Eliminar pos 1 en lista vacia espera FALSE: \t" + ((l1.eliminar(1) == false) ? sOk : sErr));
	        System.out.println("Eliminar pos 5 en lista vacia espera FALSE: \t" + ((l1.eliminar(5) == false) ? sOk : sErr));
	        System.out.println("Sacar en lista vacia espera FALSE: \t\t" + ((l1.eliminar(1) == false) ? sOk : sErr));
	        System.out.println("Recupera pos 1 en lista vacia espera NULL: \t" + ((l1.recuperar(1) == null) ? sOk : sErr));
	        System.out.println("Recupera pos 5 en lista vacia espera NULL: \t" + ((l1.recuperar(5) == null) ? sOk : sErr));
	        System.out.println("Localiza 1 en vacia espera -1, retorna " + l1.localizar(1) + ": \t" + ((l1.localizar(1) == -1) ? sOk : sErr));

	        System.out.println("Verifica copia anterior espera [2,9,3]: \t--> " + l2.toString());
	        System.out.println("Inserta 7,1 espera true : \t\t\t" + ((l2.insertar(7, 1) == true) ? sOk : sErr));
	        System.out.println("Copia modificada espera [7,2,9,3]: \t\t--> " + l2.toString());
	        System.out.println("Inserta 6,3 espera true: \t\t\t" + ((l2.insertar(6, 3) == true) ? sOk : sErr));
	        System.out.println("Copia modificada espera [7,2,6,9,3]: \t\t--> " + l2.toString());
	        System.out.println("Inserta 90,ultimo espera true: \t\t\t" + ((l2.insertar(90, l2.longitud() + 1) == true) ? sOk : sErr));
	        System.out.println("Copia modificada espera [7,2,6,9,3,90]: \t--> " + l2.toString());

	        System.out.println("Elimina pos 3 espera true: \t\t\t" + ((l2.eliminar(3) == true) ? sOk : sErr));
	        System.out.println("Copia modificada espera [7,2,9,3,90]: \t\t--> " + l2.toString());
	        System.out.println("Elimina elemento 90 : \t\t\t\t" + ((l2.eliminar(l2.localizar(90)) == true) ? sOk : sErr));
	        System.out.println("Copia modificada espera [7,2,9,3]: \t\t--> " + l2.toString());
	        l2.vaciar();
	        System.out.println("Vacia copia: \t\t\t\t\t--> " + l2.toString());

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
    public static void testArbol2 (){
    ArbolBin arbol= new ArbolBin ();
        System.out.println (arbol.toString());
        System.out.println ("Inserta 1 como raiz del arbol, espera: true==>"+ arbol.insertar(1,null, 'I'));
        System.out.println ("Inserta 2 como hijo izquierdo de 1, espera: true==>"+ arbol.insertar(2,1, 'I'));
        System.out.println ("Inserta 3 como hijo derecho de 1, espera: true==>"+ arbol.insertar(3,1, 'D'));
        System.out.println ("Inserta 4 como hijo izquierdo de 2, espera: true==>"+arbol.insertar(4,2, 'I'));        
        System.out.println ("Inserta 5 como hijo derecho de 2, espera: true==>"+ arbol.insertar(5,2, 'D'));        
        System.out.println ("Inserta 6 como hijo izquierdo de 3, espera: true==>"+ arbol.insertar(6,3, 'I'));       
        System.out.println ("Inserta 7 como hijo izquierdo de 6, espera: true==>"+ arbol.insertar(7,6, 'I')+"\n");
        System.out.println ("Muestra el arbol:\n" +arbol.toString());
        System.out.println ("Altura: espera 2 => " +arbol.altura());
        System.out.println ("Nivel del elemento 1: espera 0 => "+arbol.nivel(1));
        System.out.println ("Nivel del elemento 2: espera 1 => "+arbol.nivel(2));
        System.out.println ("Nivel del elemento 3: espera 1 => "+arbol.nivel(3));
        System.out.println ("Nivel del elemento 6: espera 2 => "+arbol.nivel(6));
        System.out.println ("Nivel del elemento 7: espera 3 => "+arbol.nivel(7));
        System.out.println ("Padre del nodo 1: espera null =>" +arbol.padre(1));
        System.out.println ("Padre del nodo 2: espera 1=>" +arbol.padre(2));
        System.out.println ("Padre del nodo 6: espera 3=>" +arbol.padre(6));
        System.out.println ("Listar preorden: espera [1,2,4,5,3,6]=>" +arbol.listarPreorden().toString());
        System.out.println ("Listar posorden: espera [4,5,2,6,3,1]=>" +arbol.listarPosorden().toString());
        System.out.println ("Listar inorden: espera [4,2,5,1,6,3]=>" +arbol.listarInorden().toString());
        System.out.println ("Listar por niveles: espera [1,2,3,4,5,6]=>" +arbol.listarPorNiveles().toString());
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
