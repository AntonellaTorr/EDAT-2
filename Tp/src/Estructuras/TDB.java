/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Anto
 */
public class TDB {
    
    private static final int TAMANIO = 100;
    private NodoHashDic[] hash;
    private int cant;

    public TDB () {
        this.hash = new NodoHashDic[this.TAMANIO];
        cant = 0;
    }

    public boolean insertar(Object clave, Object dato) {
        int pos = Math.abs(clave.hashCode()%TAMANIO);
        NodoHashDic aux = this.hash[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        if (!encontrado) {
            this.hash[pos] = new NodoHashDic(clave, dato, this.hash[pos]);
            this.cant++;
        }
        return !encontrado;
    }

    public boolean eliminar(Comparable clave) {
        //busca la posicion del elemento 
        int pos = clave.hashCode()%TAMANIO;
        NodoHashDic aux = this.hash[pos];
        boolean encontrado = false;
        //si el elemento se encuentra en la primer posicion
        if (aux != null && aux.getClave().equals(clave)) {
            this.hash[pos] = this.hash[pos].getEnlace();
        } else {
            while (!encontrado && aux != null) {
                //verifica si el elemento siguiente tiene al elemento buscado
                encontrado = aux.getEnlace().getClave().equals(clave);
                if (encontrado) {
                    //si lo tenia borra dicho enlace 
                    aux.setEnlace(aux.getEnlace().getEnlace());
                    //disminuye la cantidad de elementos en la table
                    this.cant--;
                } else {
                    //sino no lo encontro sigue avanzando 
                    aux = aux.getEnlace();
                }
            }
        }
        return encontrado;

    }

    public boolean existeClave(Comparable clave) {
        //busca la posicion en la que se podria encontrar el elemento
        int pos = clave.hashCode()%TAMANIO;
        NodoHashDic aux = this.hash[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        //si lo encuentra va a retornar true y sino false
        return encontrado;
    }

    public Object obtenerDato(Comparable clave) {
        //busca la posicion en la que se podria encontrar el elemento
        int pos = clave.hashCode()%TAMANIO;
        NodoHashDic aux = this.hash[pos];
        Object dato = null;

        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            if (encontrado) {
                dato = aux.getDato();
            }
            aux = aux.getEnlace();
        }
        //si lo encuentra va a retornar true y sino false
        return dato;
    }

    public boolean esVacia() {
        return (cant == 0);
    }

    public Lista listarClaves() {
        int pos = 0, pos2 = 1;
        Lista lista = new Lista();
        //recorre la tabla hash
        while (pos < TAMANIO) {
            //recorre la lista de cada pos
            NodoHashDic aux = this.hash[pos];
            while (aux != null) {
                lista.insertar(aux.getClave(), pos2);
                pos2++;
                aux = aux.getEnlace();
            }
            pos++;
        }
        return lista;

    }

    public Lista listarDatos() {
        int pos = 0, pos2 = 1;
        Lista lista = new Lista();
        //recorre la tabla hash0
        while (pos < TAMANIO) {
            //recorre la lista de cada pos
            NodoHashDic aux = this.hash[pos];
            while (aux != null) {
                lista.insertar(aux.getDato(), pos2);
                pos2++;
                aux = aux.getEnlace();
            }
            pos++;
        }
        return lista;

    }

    public TDB clone() {
        TDB clone = new TDB();
        int pos = 0;
        while (pos < this.TAMANIO) {
            clone.hash[pos] = new NodoHashDic(this.hash[pos].getClave(), this.hash[pos].getDato(), null);
            NodoHashDic aux = this.hash[pos];
            while (aux != null) {
                clone.hash[pos].setEnlace(new NodoHashDic(this.hash[pos].getEnlace().getClave(), this.hash[pos].getEnlace().getDato(), clone.hash[pos]));
                aux = aux.getEnlace();
            }
            pos++;
        }
        return clone;

    }
}
