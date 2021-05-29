/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.util.Objects;

/**
 *
 * @author Anto
 */
public class persona {
    private int nroDoc;
    private String tipoDoc;
    private String nombre;
    private String apellido;
    
    public int getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(int nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.nroDoc;
        hash = 73 * hash + Objects.hashCode(this.tipoDoc);
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
        final persona other = (persona) obj;
        if (this.nroDoc != other.nroDoc) {
            return false;
        }
        if (!Objects.equals(this.tipoDoc, other.tipoDoc)) {
            return false;
        }
        return true;
    }
   
}
