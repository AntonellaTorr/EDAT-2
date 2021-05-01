/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;

/**
 *
 * @author Anto
 */
public class NodoArbol {
    private Object elem;
    private NodoArbol izquierdo;
    private NodoArbol derecho;
    
    public NodoArbol (Object elem, NodoArbol izquierdo, NodoArbol derecho){
        this.elem=elem;
        this.izquierdo=izquierdo;
        this.derecho=derecho;
    }
    public Object getElem (){
        return this.elem;
    }
    public NodoArbol getIzquierdo (){
        return this.izquierdo;
    }
    public NodoArbol getDerecho (){
        return this.derecho;
    }
    public void setElem (Object elem){
        this.elem=elem;
    }
    public void setIzquierdo (NodoArbol izquierdo){
        this.izquierdo=izquierdo;
    }
    public void setDerecho (NodoArbol derecho){
        this.derecho=derecho;
    }
}
