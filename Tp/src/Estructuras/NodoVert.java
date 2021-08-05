/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import java.util.Objects;

/**
 *
 * @author Anto
 */
public class NodoVert {
   //atributos
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    //constructor
    public NodoVert(Object elem, NodoVert sigVertice){
        this.elem=elem;
        this.sigVertice=sigVertice;
        this.primerAdy=null;
    }
    //setter y getters
    public Object getElem(){
        return elem;
    }
    public void setElem(Object elem){
        this.elem=elem;
    }
    public NodoVert getSigVertice(){
        return sigVertice;
    }
    public void setSigVertice(NodoVert sigVertice){
        this.sigVertice=sigVertice;
    }
    
    public NodoAdy getPrimerAdy(){
        return primerAdy;
    }
    public void setPrimerAdy(NodoAdy primerAdy){
        this.primerAdy=primerAdy;
    }
     @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.elem);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodoVert other = (NodoVert) obj;
        if (!Objects.equals(this.elem, other.elem)) {
            return false;
        }
        return true;
    }
    
}
