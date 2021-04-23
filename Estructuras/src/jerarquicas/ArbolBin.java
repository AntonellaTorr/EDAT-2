/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Nodo;

/**
 *
 * @author Anto
 */
public class ArbolBin {

    NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        NodoArbol resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                resultado = n;
            } else {
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean insertar(Object nuevoElem, Object elemPadre, char lugar) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoArbol(nuevoElem, null, null);
        } else {
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                if (lugar == 'I' && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new NodoArbol(nuevoElem, null, null));
                } else {
                    if (lugar == 'D' && nodoPadre.getDerecho() == null) {
                        nodoPadre.setDerecho(new NodoArbol(nuevoElem, null, null));
                    } else {
                        exito = false;
                    }
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    public boolean esVacio() {
        return (this.raiz == null);
    }

    public void vaciar() {
        this.raiz = null;
    }

    public int nivel(Object elem) {
        //este metodo retorn el nivel donde se encuentra un determinado elemento en el arbol
        int nivel = -1;
        //si el arbol no esta vacio 
        nivel = encontrarNivel(this.raiz, elem, -1);

        return nivel;
    }

    private int encontrarNivel(NodoArbol nodo, Object elem, int nivel) {
        int encontrado = -1;
        //si el nodo es una hoja y el elemento esta en dicho nivel retornamos 0
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                encontrado = nivel + 1;
            } else {
                encontrado = encontrarNivel(nodo.getIzquierdo(), elem, nivel + 1);
                if (encontrado == -1) {
                    encontrado = encontrarNivel(nodo.getDerecho(), elem, nivel + 1);
                }
            }
        }

        return encontrado;
    }

    public int altura() {
        return medirAltura(this.raiz);
    }

    private int medirAltura(NodoArbol n) {
        int longitud = -1, longitud2 = -1;
        if (n != null) {
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                longitud = 0;
            } else {
                longitud = medirAltura(n.getIzquierdo()) + 1;
                longitud2 = medirAltura(n.getDerecho()) + 1;

            }
        }
        return Math.max(longitud, longitud2);
    }

    public Object padre(Object elem) {
        return obtenerElemPadre(this.raiz, elem);
    }
    private Object obtenerElemPadre(NodoArbol n, Object x) {
        Object elemento = null;
        if (n != null && !this.raiz.getElem().equals(x)){
            if ((n.getIzquierdo() != null && n.getIzquierdo().getElem().equals(x)) ||( n.getDerecho() != null && n.getDerecho().getElem().equals(x))) {
                elemento = n.getElem();
            } else {
                elemento = obtenerElemPadre(n.getIzquierdo(), x);

                if (elemento == null) {
               
                    elemento = obtenerElemPadre(n.getDerecho(), x);
                }
            }
        }
        return elemento;
    }
   

    public Lista listarPreorden() {
        Lista lista = new Lista();
        recursivoPreorden(this.raiz, lista, 1);
        return lista;
    }

    public int recursivoPreorden(NodoArbol nodo, Lista lista, int pos) {
        if (nodo != null) {
            lista.insertar(nodo.getElem(), pos);
            if (nodo.getIzquierdo() != null) {
                pos = recursivoPreorden(nodo.getIzquierdo(), lista, pos + 1);
            }
            if (nodo.getDerecho() != null) {
                pos = recursivoPreorden(nodo.getDerecho(), lista, pos + 1);
            }
        }
        return pos;
    }

    public Lista listarInorden() {
        Lista lista = new Lista();
        recursivoInorden(this.raiz, lista, 1);

        return lista;
    }

    public int recursivoInorden(NodoArbol nodo, Lista lista, int pos) {
        if (nodo != null) {
            //si llego a la hoja del lado izquierdo imprimo y aumento pos
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                lista.insertar(nodo.getElem(), pos);
                pos++;
            } else {
                pos = recursivoInorden(nodo.getIzquierdo(), lista, pos);
                lista.insertar(nodo.getElem(), pos);
                pos++;
                pos = recursivoInorden(nodo.getDerecho(), lista, pos);
            }

        }
        return pos;
    }

    public Lista listarPosorden() {
        Lista lista = new Lista();
        recursivoPosorden(this.raiz, lista, 1);
        return lista;
    }

    public int recursivoPosorden(NodoArbol nodo, Lista lista, int pos) {
        if (nodo != null) {
            //si llego a la hoja del lado izquierdo imprimo y aumento pos
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                lista.insertar(nodo.getElem(), pos);
                pos++;
            } else {
                pos = recursivoPosorden(nodo.getIzquierdo(), lista, pos);
                pos = recursivoPosorden(nodo.getDerecho(), lista, pos);
                lista.insertar(nodo.getElem(), pos);
                pos++;
            }

        }
        return pos;
    }

    public Lista listarPorNiveles() {
        //este metodo devuelve una lista con los elementos del arbol almacenados por niveles
        Lista lista = new Lista();
        if (this.raiz != null) {
            int i = 1;
            Cola Q = new Cola();
            Q.poner(this.raiz);
            NodoArbol nodoActual;
            while (!Q.esVacia()) {
                //obtenemos el nodo de la cola
                nodoActual = (NodoArbol) Q.obtenerFrente();
                //lo sacamos
                Q.sacar();
                //lo insertamos en la lista
                lista.insertar(nodoActual.getElem(), i);
                //si tiene hijo izquierdo lo pongo en la cola
                if (nodoActual.getIzquierdo() != null) {
                    Q.poner(nodoActual.getIzquierdo());
                }
                //si tiene hijo derecho lo pongo en la cola 
                if (nodoActual.getDerecho() != null) {
                    Q.poner(nodoActual.getDerecho());
                }
                i++;

            }

        }
        return lista;
    }

    public String toString() {
        return concatenar(this.raiz);
    }

    private String concatenar(NodoArbol n) {
        //creamos aca las cadenas porque nos indicaron que no era necesario pasarla por parametro
        String cadenaAux = "", cadena = "arbol vacio";
        if (n != null) {
            //si el nodo ingresado no es null
            cadena = "";
            //pondremos el padre primero 
            cadena += "\nNodo padre: " + n.getElem() + " ";
            //luego pondremos si tiene o no tiene hijos izquierdo o derecho
            if (n.getIzquierdo() != null) {
                cadena += "HI:" + n.getIzquierdo().getElem() + " ";
            } else {
                cadena += "HI:- ";
            }
            if (n.getDerecho() != null) {
                cadena += "HD: " + n.getDerecho().getElem() + "\n";
            } else {
                cadena += "HD:-\n";
            }
            //si tenia hijo izquierdo iremos repitiendo hasta que ya no hayan mas elementos del lado derecho del arbol 
            if (n.getIzquierdo() != null) {
                cadenaAux = concatenar(n.getIzquierdo());
                cadena += cadenaAux;
            }
            //repetimos el mismo procedimiento para el lado derecho
            if (n.getDerecho() != null) {
                cadenaAux = concatenar(n.getDerecho());
                cadena += cadenaAux;
            }
        }
        return cadena;
    }

    public ArbolBin clone() {
        ArbolBin clone = new ArbolBin();
        if (this.raiz != null) {
            
            clone.raiz =  new NodoArbol(this.raiz.getElem(), null, null);;
            auxClon(this.raiz, clone.raiz);

        }
        return clone;

    }

    private void auxClon(NodoArbol nodo, NodoArbol aux2) {
        //precondicion nodo distinto de null
        //si eixste un nodo izquierdo creo un nodo nuevo a la izquierda de mi nodo raiz
        if (nodo.getIzquierdo() != null) {
            aux2.setIzquierdo(new NodoArbol(nodo.getIzquierdo().getElem(), null, null));
            auxClon(nodo.getIzquierdo(), aux2.getIzquierdo());

        }
        //si eixste un nodo derecho creo un nodo nuevo a la derecho de mi nodo raiz
        if (nodo.getDerecho() != null) {
            aux2.setDerecho(new NodoArbol(nodo.getDerecho().getElem(), null, null));
            auxClon(nodo.getDerecho(), aux2.getDerecho());
        }

    }

   
    public Lista frontera() {
        //podriamos pasarle 1 nada mas para no crear la variable posicion
        Lista listado = new Lista();
        listarFrontera(this.raiz, listado, 1);
        return listado;
    }

    public int listarFrontera(NodoArbol n, Lista listado, int pos) {

        if (n != null) {
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                listado.insertar(n.getElem(), pos);
                pos++;
            } else {
                if (n.getIzquierdo() != null) {
                    pos = listarFrontera(n.getIzquierdo(), listado, pos);
                }
                if (n.getDerecho() != null) {
                    pos = listarFrontera(n.getDerecho(), listado, pos);
                }
            }
        }
        return pos;
    }

    public Lista frontera2() {
        Lista lista = new Lista();
        fronteraAux(this.raiz, lista, 1);
        return lista;
    }

    private int fronteraAux(NodoArbol nodo, Lista lista, int pos) {
        //pongo en la lista el elemento que sea hoja
        if (nodo != null) {
            if (nodo.getDerecho() == null && nodo.getIzquierdo() == null) {
                lista.insertar(nodo.getElem(), pos);
                pos++;
            } else {
                //llamo recursivamente con cada lado
                //puede que tenga hijo de un solo lado asi que verifico para no llamar con un elemento nulo
                if (nodo.getIzquierdo() != null) {
                    fronteraAux(nodo.getIzquierdo(), lista, pos);
                }
                if (nodo.getDerecho() != null) {
                    fronteraAux(nodo.getDerecho(), lista, pos);
                }
            }
        }
        return pos;
    }
}
