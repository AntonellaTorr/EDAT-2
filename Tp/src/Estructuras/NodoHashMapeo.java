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
public class NodoHashMapeo {
    private Object dominio;
    private Lista rango;
    private NodoHashMapeo enlace;

    public NodoHashMapeo(Object dominio,NodoHashMapeo enlace) {
        this.dominio = dominio;
        this.rango = new Lista ();
        this.enlace = enlace;
    }
    public void añadirElementoRango(Object elemRango){
        this.rango.insertar(elemRango, 1);
    }
    public Object getDominio() {
        return dominio;
    }

    public Lista getRango() {
        return rango;
    }

    public void setRango(Lista rango) {
        this.rango = rango;
    }

    public NodoHashMapeo getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoHashMapeo enlace) {
        this.enlace = enlace;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.dominio);
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
        final NodoHashMapeo other = (NodoHashMapeo) obj;
        if (!Objects.equals(this.dominio, other.dominio)) {
            return false;
        }
        return true;
    }
    
    
}
