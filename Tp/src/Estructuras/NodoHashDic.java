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
class NodoHashDic {
    private Object clave;
    private Object dato;
    private NodoHashDic enlace;

    public NodoHashDic(Object clave, Object dato, NodoHashDic enlace) {
        this.clave = clave;
        this.dato = dato;
        this.enlace=enlace;
    }

    public Object getClave() {
        return clave;
    }


    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public NodoHashDic getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoHashDic enlace) {
        this.enlace = enlace;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.clave);
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
        final NodoHashDic other = (NodoHashDic) obj;
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        return true;
    }
    
    
    
}
