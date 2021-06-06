/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

import funciones.Funciones;

/**
 *
 * @author Anto
 */
public class TablaHash {

    private static final int TAMANIO = 20;
    private CeldaHash[] hash;
    private int cant;
    private static final int VACIO = 0;
    private static final int OCUPADO = 1;
    private static final int BORRADO = -1;

    public TablaHash() {
        this.hash = new CeldaHash[this.TAMANIO];
        cant = 0;
        for(int i=0;i<this.TAMANIO;i++){
            this.hash[i]= new CeldaHash(null, VACIO);
        }
    }

    public boolean pertenece(Object elem) {
        int incremento = elem.hashCode();
        int pos = Funciones.rehash(elem)%this.TAMANIO;
        boolean encontrado = false;
        int intento = 1;

        if (this.hash[pos].getEstado() != VACIO && this.hash[pos].getElem().equals(elem)) {
            encontrado = true;
        } else {
            //mientras que no encontremos el elemento, la celda en pos no esta vacia y no hicimos mas intentos 
            //que el tam de la tabla 
            while (!encontrado && this.hash[pos].getEstado() != VACIO && intento < TAMANIO) {
                pos = (pos + incremento * intento) % this.TAMANIO;
                encontrado = this.hash[pos].getElem().equals(elem);
                intento++;
            }
        }
        return encontrado;
    }

    public boolean insertar(Object elem) {
        int pos = elem.hashCode();
        int incremento = Funciones.rehash(elem)% this.TAMANIO;
        boolean exito = false, salir = false;
        int intento = 1;
        while (!exito && !salir && intento < TAMANIO) {//añadir  this.hash[pos].getEstado()==BORRADO
            //si la celda no esta ocupada creamos el elemento
               if (this.hash[pos].getEstado() != OCUPADO) {
                   this.hash[pos] = new CeldaHash(elem, OCUPADO);
                   this.cant++;
                   exito = true;
               } else {
                   //si el elemento ya se encuentra en la tabla salimos
                   if (this.hash[pos].equals(elem)) {
                       exito = false;
                       salir = true;
                   }
               }
               pos = (pos + incremento * intento) % this.TAMANIO;
               intento++;
           }

        

        return exito;
    }

    public boolean eliminar(Object elem) {
        int pos = elem.hashCode();
        int incremento = Funciones.rehash(elem)% this.TAMANIO;
        boolean encontrado = false;
        int intento = 1;
        while (!encontrado && intento < this.TAMANIO && this.hash[pos].getEstado() != VACIO) {
            if (this.hash[pos].getEstado() == OCUPADO) {
                encontrado = this.hash[pos].getElem().equals(elem);
                //si encontro el elemento lo borra
                if (encontrado) {
                    //no hace falta hacer set null?
                    this.hash[pos].setEstado(BORRADO);
                    this.hash[pos].setElem(null);
                    this.cant--;
                }
            }
            pos = (pos + intento * incremento) & this.TAMANIO;
            intento++;
        }
        return encontrado;
    }
    @Override
    public String toString (){
        String cadena="[";
        for(int i=0;i<TAMANIO;i++){
            if (this.hash[i]!=null){
                cadena+= ""+ this.hash[i].getElem();
                if (i!=cant-1){
                    cadena+=",";
                }
            }
        }
        cadena+="]";
        return cadena;
    }

}
