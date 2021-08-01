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
    
    private static final int TAMANIO = 50;
    private NodoHashDic[] hash;
    private int cant;

    public TDB () {
        this.hash = new NodoHashDic[this.TAMANIO];
        cant = 0;
    }

    public boolean insertar(Object clave, Object dato) {
        /*este metodo inserta el elemento con la clave y los datos recibidos
        devuelve true en el caso en el que la clave no estuviera previamente en la table
        false en el caso contrario
        */
        int pos = Math.abs(clave.hashCode()%TAMANIO);
        NodoHashDic aux = this.hash[pos];
        boolean encontrado = false;
        //busqueda de la clave en la tabla
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        //si no se encontraba el elemento lo inserta en la tabla
        if (!encontrado) {
            this.hash[pos] = new NodoHashDic(clave, dato, this.hash[pos]);
            this.cant++;
        }
        return !encontrado;
    }

    public boolean eliminar(Object clave) {
        /*este metodo elimina el elemento con la clave y los datos recibidos
        devuelve true en el caso en el que la clave exista
        false en el caso contrario
        */
        //busca la posicion del elemento 
        int pos = Math.abs(clave.hashCode()%TAMANIO);
        NodoHashDic aux = this.hash[pos];
        boolean encontrado = false;
        //si el elemento se encuentra en la primer posicion
        if (aux != null && aux.getClave().equals(clave)) {
            this.hash[pos] = this.hash[pos].getEnlace();
            this.cant--;
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

    public boolean existeClave(Object clave) {
        /*este metodo devuelve true en el caso de que la clave ingresada por parametro exista
        false en el caso contrario
        */
        //busca la posicion en la que se podria encontrar el elemento
        int pos = Math.abs(clave.hashCode()%TAMANIO);
        NodoHashDic aux = this.hash[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        //si lo encuentra va a retornar true y sino false
        return encontrado;
    }

    public Object obtenerDato(Object clave) {
        /*este metodo devuelve el objeto asociada a la clave ingresada por parametro
        devuelve null en el caso en que la clave no exista 
        */
        //busca la posicion en la que se podria encontrar el elemento
        int pos = Math.abs(clave.hashCode()%TAMANIO);
        NodoHashDic aux = this.hash[pos];
        Object dato = null;
        //busca la clave
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            if (encontrado) {
                dato = aux.getDato();
            }
            aux = aux.getEnlace();
        }
        return dato;
    }

    public boolean esVacia() {
        return (cant == 0);
    }

    public Lista listarClaves() {
        /* este metodo devuelve una lista con todas las claves de la tabla
        devuelve una lista vacia en el caso en que la tabla este vacia*/
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
         /* este metodo devuelve una lista con todos los datos de la tabla
        devuelve una lista vacia en el caso en que la tabla este vacia*/
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
    public String toString (){
         /* este metodo devuelve una cadena con las claves y datos de tabla*/
        int pos = 0;
        String cadena="";
        //recorre la tabla hash0
        while (pos < TAMANIO) {
            //recorre la lista de cada pos
            NodoHashDic aux = this.hash[pos];
            while (aux != null) {
                cadena+="Clave "+aux.getClave()+" Dato "+aux.getDato()+"\n";
                aux = aux.getEnlace();
            }
            pos++;
        }
        return cadena;
    }

    public TDB clone() {
        /*este metodo devuelve una tabla, copia de la original*/
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
