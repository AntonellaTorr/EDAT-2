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

   

    private int codigo;
    private int puntajeAOtorgar;
    private String nombre;
    private String  tipo;

    public Desafio(int codigo, int puntajeAOtorgar, String nombre, String tipo) {
        this.codigo = codigo;
        this.puntajeAOtorgar = puntajeAOtorgar;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public int getCodigo() {
        return codigo;
    }

    

    public int getPuntajeAOtorgar() {
        return puntajeAOtorgar;
    }

    public void setPuntajeAOtorgar(int puntajeAOtorgar) {
        this.puntajeAOtorgar = puntajeAOtorgar;
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
        return "Desafio->" + " codigo=" + codigo + ", puntajeAOtorgar=" + puntajeAOtorgar + ", nombre=" + nombre + ", tipo=" + tipo ;
    }
    
   
}
