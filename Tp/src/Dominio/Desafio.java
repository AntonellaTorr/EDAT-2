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
public class Desafio {

    private int puntajeAOtorgar;
    private String nombre;
    private String  tipo;

    public Desafio(int puntajeAOtorgar, String nombre, String tipo) {
        this.puntajeAOtorgar = puntajeAOtorgar;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    public int getPuntajeAOtorgar() {
        return puntajeAOtorgar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
     @Override
    public String toString() {
        return "Desafio->" + " puntajeAOtorgar=" + puntajeAOtorgar + ", nombre=" + nombre + ", tipo=" + tipo ;
    }
        @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.puntajeAOtorgar;
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
        final Desafio other = (Desafio) obj;
        if (this.puntajeAOtorgar != other.puntajeAOtorgar) {
            return false;
        }
        return true;
    }
    
   
}
