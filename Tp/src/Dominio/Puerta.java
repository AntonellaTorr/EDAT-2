/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

/**
 *
 * @author Anto
 */
public class Puerta {
    private int codHabDestino;
    private int codHabOrigen;

    public Puerta(int codHabDestino, int codHabOrigen) {
        this.codHabDestino = codHabDestino;
        this.codHabOrigen = codHabOrigen;
    }

    public int getCodHabDestino() {
        return codHabDestino;
    }

    public void setCodHabDestino(int codHabDestino) {
        this.codHabDestino = codHabDestino;
    }

    public int getCodHabOrigen() {
        return codHabOrigen;
    }

    public void setCodHabOrigen(int codHabOrigen) {
        this.codHabOrigen = codHabOrigen;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.codHabDestino;
        hash = 79 * hash + this.codHabOrigen;
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
        final Puerta other = (Puerta) obj;
        if (this.codHabDestino != other.codHabDestino) {
            return false;
        }
        if (this.codHabOrigen != other.codHabOrigen) {
            return false;
        }
        return true;
    }
    
}
