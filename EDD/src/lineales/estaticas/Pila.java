/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author Antonella
 */
public class Pila {

    //atributos de la clase
    private static final int TAMANIO = 10;
    private Object[] arreglo;
    private int tope;

    //Constructor
    public Pila() {
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }

    public boolean apilar(Object nuevoElem) {
        boolean exito;
        if (this.tope + 1 >= this.TAMANIO) {
            //Pila llena
            exito = false;
        } else {
            //Se le pueden seguir agregando mas elementos a la pila
            this.tope++;
            this.arreglo[tope] = nuevoElem;
            exito = true;
        }
        return exito;
    }

    public boolean desapilar() {
        boolean exito;
        if (this.esVacia()) {
            //Pila vacia: no se pueden desapilar mas elementos
            exito = false;
        } else {
            //Se puede desapilar
            this.arreglo[tope] = null;
            this.tope--;
            exito = true;
        }
        return exito;
    }

    public Object obtenerTope() {
        //Devuelve el elemento en el tope de la pila pero antes verifica que la pila no este vacia
        if (!this.esVacia()) {
            return this.arreglo[this.tope];
        } else {
            return null;
        }
    }

    public boolean esVacia() {
        //Este metodo verifica que si la pila esta vacia o no
        return (tope == -1);
    }

    public void vaciar() {
        //Si la pila no esta vacia va a ser posible vaciarla
        int i = 0;
        while (!this.esVacia()) {
            this.arreglo[i] = null;
            i++;
            this.tope--;
        }
    }

    @Override
    public Pila clone() {
        //Creo la pila vacia
        Pila pilaClon = new Pila();
        int i = 0;
        //clonamos cada elemento de la pila
        while (i < this.tope + 1) {
            pilaClon.arreglo[i] = this.arreglo[i];
            i++;
        }
        pilaClon.tope = this.tope;
        return pilaClon;
    }

    @Override
    public String toString() {
        //el metodo convierte los elementos de pila a un String
        String cadena = "";
        int i = 0;
        //Recorre la pila hasta el ultimo elemento, en cada iteracion vuelve el objeto almacenado en cada posicion a un String 
        while (i < this.tope + 1) {
            cadena += this.arreglo[i].toString();
            i++;
            if (i < this.tope + 1) {
                cadena += ",";
            }
        }
        return cadena;
    }

}
