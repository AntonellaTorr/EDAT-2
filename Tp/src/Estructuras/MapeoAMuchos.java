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
public class MapeoAMuchos {

    private static final int TAMANIO = 10;
    private NodoHashMapeo[] hash;
    private int cant;

    public MapeoAMuchos() {
        this.hash = new NodoHashMapeo[this.TAMANIO];
        cant = 0;
    }

    public boolean asociar(Object dominio, Object dato) {
        int pos = Math.abs(dominio.hashCode() % TAMANIO), res;
        NodoHashMapeo aux = this.hash[pos];
        boolean exito = false;
        while (!exito && aux != null) {
            exito = aux.getDominio().equals(dominio);
            if (!exito) {
                aux = aux.getEnlace();
            }
        }
        //si ya se encontraba el dominio
        if (exito) {
            res = aux.getRango().localizar(dato);
            //si no estaba el dato en la lista de rango
            if (res < 0) {
                aux.añadirElementoRango(dato);
                exito = true;
                this.cant++;
            } else {
                exito = false;
            }
        } //si no se encontraba el dominio entonces inserta un nuevo nodo
        else {
            this.hash[pos] = new NodoHashMapeo(dominio, this.hash[pos]);
            this.hash[pos].añadirElementoRango(dato);
            this.cant++;
            exito = true;
        }
        return exito;
    }

    public boolean desasociar(Object dominio, Object rango) {
        int pos = Math.abs(dominio.hashCode() % TAMANIO);
        //almaceno el enlace anterior a aux, asi si tengo que eliminar el nodo puedo hacerlo facilmente
        NodoHashMapeo aux = this.hash[pos], enlaceAnt = null;
        boolean exito = false, encontrado = false;
        //busca el nodo que contiene al dominio
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(dominio);
            if (!encontrado) {
                enlaceAnt = aux;
                aux = aux.getEnlace();
            }
        }
        //si se encuentra busca el rango en la lista
        if (encontrado) {
            int res = aux.getRango().localizar(rango);
            //si se encuentra el rango en la lista se elimina
            if (res > 0) {
                aux.getRango().eliminar(res);
                //si luego de eliminar el rango la lista queda vacia tmbn se elimina el dominio
                if (aux.getRango().esVacia()) {
                    eliminarNodo(aux, enlaceAnt);
                }
                exito = true;
            }

        }
        return exito;

    }

    private void eliminarNodo(NodoHashMapeo nodoActual, NodoHashMapeo nodoAnterior) {
        //si el elemento se encuentra en la primer posicion
        if (nodoAnterior == null) {
            nodoActual = nodoActual.getEnlace();
        } else {
            nodoAnterior.setEnlace(nodoActual.getEnlace());
        }
        this.cant--;
    }

    public Lista obtenerValores(Object dominio) {
        //busca la posicion de el elemento en la tabla 
        int pos = Math.abs(dominio.hashCode() % TAMANIO);
        NodoHashMapeo aux = this.hash[pos];
        boolean encontrado = false;
        Lista valores = new Lista();
        //busca el nodo que contiene al dominio
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(dominio);
            if (!encontrado) {
                aux = aux.getEnlace();
            }
        }
        //si se encuentra almacena la lista que contiene a los rangos para luego retonarla 
        if (encontrado) {
            valores = aux.getRango();
        }
        return valores;
    }

    @Override
    public String toString() {
        int pos = 0;
        String cadena = "";
        NodoHashMapeo aux;
        while (pos < TAMANIO) {
            aux = this.hash[pos];
            if (aux != null) {
                cadena += "Dominio " + this.hash[pos].getDominio() + " Rango " + this.hash[pos].getRango() + "\n";
            }
            pos++;

        }
        return cadena;

    }

    @Override
    public MapeoAMuchos clone() {
        int pos = 0;
        MapeoAMuchos mapeoClone = new MapeoAMuchos();
        NodoHashMapeo aux, auxClone;
        while (pos < TAMANIO) {
            aux = this.hash[pos];
            if (aux != null) {
                //copia de todos los nodos en la pos
                mapeoClone.hash[pos] = new NodoHashMapeo(aux.getDominio(), aux.getRango().clone(), null);
                aux = aux.getEnlace();
                auxClone=mapeoClone.hash[pos];
                while (aux != null) {
                    auxClone.setEnlace(new NodoHashMapeo(aux.getDominio(), aux.getRango().clone(), null));
                    auxClone = auxClone.getEnlace();
                    aux = aux.getEnlace();
                }
                
            }
            pos++;
        }
        mapeoClone.cant = this.cant;
        return mapeoClone;
    }
}
