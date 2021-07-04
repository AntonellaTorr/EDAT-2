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
public class Habitacion {
    private int codigo;
    private String nombre;
    private int planta;
    private double mCuadrados;
    private boolean tieneSalida;
    
    public Habitacion(int codigo, String nombre, int planta, double mCuadrados, boolean tieneSalida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.mCuadrados = mCuadrados;
        this.tieneSalida = tieneSalida;
    }
    public int getCodigo(){
        return codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public double getmCuadrados() {
        return mCuadrados;
    }

    public void setmCuadrados(double mCuadrados) {
        this.mCuadrados = mCuadrados;
    }

    public boolean tieneSalida() {
        return tieneSalida;
    }

    public void setTieneSalida(boolean tieneSalida) {
        this.tieneSalida = tieneSalida;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.codigo;
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
        final Habitacion other = (Habitacion) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }
}
