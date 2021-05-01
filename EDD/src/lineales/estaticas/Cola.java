/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author Quiroga Nicolas, Torres Bianca Antonella
 */
public class Cola {

    //atributos de la clase
    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;

    public Cola() {
        //constructor
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object elem) {
        boolean exito = false;
        //si la cola no esta llena insertamos otro elemento en ella
        if (((this.fin + 1) % this.TAMANIO) != this.frente) {
            this.arreglo[this.fin] = elem;
            this.fin = (this.fin + 1) % this.TAMANIO;
            exito = true;
        }
        return exito;
    }

    public boolean sacar() {
        //este metodo quita el frente de la cola
        boolean exito = false;
        //si la cola no esta vacia quitamos su frente
        if (this.fin != this.frente) {
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % this.TAMANIO;
            exito = true;
        }
        return exito;
    }

    public boolean esVacia() {
        //este metodo verifica si la lista esta vacia
        return (this.frente == this.fin);
    }

    public Object obtenerFrente() {
        //este metodo retorna el frente de la cola
        Object frente = null;
        //si la cola no esta vacia obtenemos su frente
        if (this.frente != this.fin) {
            frente = this.arreglo[this.frente];
        }
        return frente;
    }

    public void vaciar() {
        //este metodo vacia la cola
        for (int i = this.frente; i < this.fin + 1; i++) {
            //vaciamos cada celda de la cola
            this.arreglo[i] = null;
        }
        this.fin = 0;
        this.frente = 0;
    }

    public Cola clone() {
        //este metodo devuelve un clon de la cola original
        //creo la cola vacia
        Cola colaClon = new Cola();
        //seteamos los valores de frente y fin con los mismos de la cola original
        colaClon.frente = this.frente;
        colaClon.fin = this.fin;
        //clonamos la cola
        colaClon.arreglo = this.arreglo.clone();
        return colaClon;
    }

    public String toString() {
        //este metodo, el cual es a utilizado a fines de debugging, retorna todos los elementos de la cola en una cadena
        String cadena = "La cola vacia";
        int i = this.frente;
        //si la cola no esta vacia
        if (this.frente != this.fin) {
            cadena = "[";
            while (i != this.fin) {
                //recorremos toda la cola obteniendo los elementos, concatenandolos a la cadena
                cadena += this.arreglo[i].toString();
                i = (i + 1) % this.TAMANIO;
                if (i != this.fin) {
                    cadena += " ";
                }
            }
            cadena += "]";
        }
        return cadena;
    }

}
