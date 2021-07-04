/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Anto
 */
public class NodoAVLDic {

    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDic izquierdo;
    private NodoAVLDic derecho;

    public NodoAVLDic(Comparable clave, Object dato, NodoAVLDic izquierdo, NodoAVLDic derecho, int altura) {
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = altura;

    }
    public void setDato(Object dato) {
        this.dato = dato;
    }
    public void setIzquierdo(NodoAVLDic izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoAVLDic derecho) {
        this.derecho = derecho;
    }
    public void recalcularAltura() {
        //alturaIz y der por defecto, sino buscandola en el nodo
        int alturaIzq = -1, alturaDer = -1;
        if (this.izquierdo != null) {
            alturaIzq = this.izquierdo.altura;
        }
        if (this.derecho != null) {
            alturaDer = this.derecho.altura;
        }
        this.altura = (Math.max(alturaIzq, alturaDer)) + 1;

    }

    public Comparable getClave() {
        return clave;
    }

    public int getAltura() {
        return altura;
    }
    public Object getDato() {
        return dato;
    }

    public NodoAVLDic getIzquierdo() {
        return izquierdo;
    }

    public NodoAVLDic getDerecho() {
        return derecho;
    }
}
