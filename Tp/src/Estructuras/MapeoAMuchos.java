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

    private static final int TAMANIO = 50;
    private NodoHashMapeo[] hash;
    private int cant;

    public MapeoAMuchos() {
        this.hash = new NodoHashMapeo[this.TAMANIO];
        cant = 0;
    }

    public boolean asociar(Object dominio, Object dato) {
        /*este metodo asocia los elementos recibidos por parametro
        devuelve true en el caso en que dichos elementos no esten asociados
        false en el caso contrario
        */
        int pos = Math.abs(dominio.hashCode() % TAMANIO), res;
        NodoHashMapeo aux = this.hash[pos];
        boolean exito = false;
        //busca el dominio
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
        /*este metodo desasocia los elementos recibidos por parametro
        devuelve true en el caso en que dichos elementos hayan estado asociados previamente
        false en el caso en que no lo estuvieran
        */
        int pos = Math.abs(dominio.hashCode() % TAMANIO);
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
        /*este metodo privado elimina el nodo actual recibido por parametro*/
        //si el elemento se encuentra en la primer posicion
        if (nodoAnterior == null) {
            nodoActual = nodoActual.getEnlace();
        } else {
            nodoAnterior.setEnlace(nodoActual.getEnlace());
        }
        this.cant--;
    }

    public Lista obtenerValores(Object dominio) {
        /*este metodo devuelve una lista con todos los valores asociados al dominio
        devuelve una lista vacia en el caso en que no exista el dominio en la tabla
        */
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
        /*este metodo devuelve una cadena con la informacion de todos los dominios y los rangos
        devuelve una cadena vacia en el caso en que no hayan elementos asociados al mapeo*/
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
        /*este metodo devuelve un mapeo, copia del mapeo original*/
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
