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
public class NodoGen {
    private Object elem;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;
    
    public NodoGen(Object elem, NodoGen hijoIzquierdo, NodoGen hermanoDerecho){
        this.elem= elem;
        this.hijoIzquierdo=hijoIzquierdo;
        this.hermanoDerecho=hermanoDerecho;
    }
    public Object getElem (){
        return this.elem;
    }
    public NodoGen getHijoIzquierdo(){
        return this.hijoIzquierdo;
    }
    public NodoGen getHermanoDerecho (){
        return this.hermanoDerecho;
    }
    public void setElem (Object elem){
        this.elem=elem;
    }
    public void setHijoIzquierdo(NodoGen hijoIzquierdo){
        this.hijoIzquierdo=hijoIzquierdo;
    }
    public void setHermanoDerecho(NodoGen hermanoDerecho){
        this.hermanoDerecho=hermanoDerecho;
    }
}
