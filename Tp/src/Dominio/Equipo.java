/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.util.Objects;

/**
 *
 * @author Anto
 */
public class Equipo {


    private String nombre;
    private int puntajeExigido;
    private int puntajeTotal;
    private int habitacionActual;
    private int puntajeActual;
    //hay que agregar los desafios que hicieron?
    //
    public Equipo(String nombre, int puntajeExigido, int habitacionActual) {
        this.nombre = nombre;
        this.puntajeExigido = puntajeExigido;
        this.habitacionActual = habitacionActual;
        this.puntajeActual=0;
        this.puntajeTotal=0;
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getPuntajeExigido() {
        return puntajeExigido;
    }

    public void setPuntajeExigido(int puntajeExigido) {
        this.puntajeExigido = puntajeExigido;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public int getHabitacionActual() {
        return habitacionActual;
    }

    public void setHabitacionActual(int habitacionActual) {
        this.habitacionActual = habitacionActual;
    }
    
    

    public int getPuntajeActual() {
        return puntajeActual;
    }

    public void setPuntajeActual(int puntajeActual) {
        this.puntajeActual = puntajeActual;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.nombre);
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
        final Equipo other = (Equipo) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Equipo" + "nombre=" + nombre + ", puntajeExigido=" + puntajeExigido + ", puntajeTotal=" + puntajeTotal + ", habitacionActual=" + habitacionActual + ", puntajeActual=" + puntajeActual + '}';
    }
    
 
}
