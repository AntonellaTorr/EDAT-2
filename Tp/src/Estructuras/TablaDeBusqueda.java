/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import Dominio.Desafio;

/**
 *
 * @author Anto
 */
public class TablaDeBusqueda {

    private NodoAVLDic raiz;

    public TablaDeBusqueda() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        /*este metodo inserta la clave con su dato en la tabla
        devuelve false en el caso en que la clave ya este en la tabla
        true en el caso contrario
        */
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoAVLDic(clave, dato, null, null, 0);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, clave, dato);
            this.raiz.recalcularAltura();
            chequearBalanceo(this.raiz, null);
            this.raiz.recalcularAltura();
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDic nodo, Comparable clave, Object dato) {
        /*metodo privado recursivo auxiliar que inserta la clave con su dato en la tabla*/
        boolean exito = false;
        int resultado = clave.compareTo(nodo.getClave());
        //si el elemento a insertar es menor al elemento que se encuentra en el nodo bajamos a la izquierda
        if (resultado < 0) {
            //si no tiene hijo izquierda inserta el elemento nuevo
            if (nodo.getIzquierdo() == null) {
                nodo.setIzquierdo(new NodoAVLDic(clave, dato, null, null, 0));
                exito = true;
                nodo.recalcularAltura();
            } else {
                //si tiene llama recursivamente con el 
                exito = insertarAux(nodo.getIzquierdo(), clave, dato);
                nodo.recalcularAltura();
                chequearBalanceo(nodo.getIzquierdo(), nodo);
                nodo.recalcularAltura();

            }
        } else {
            if (resultado > 0) {
                //si no tiene hijo derecho inserta el elemento nuevo
                if (nodo.getDerecho() == null) {
                    nodo.setDerecho(new NodoAVLDic(clave, dato, null, null, 0));
                    exito = true;
                    nodo.recalcularAltura();

                } else {
                    exito = insertarAux(nodo.getDerecho(), clave, dato);
                    nodo.recalcularAltura();
                    chequearBalanceo(nodo.getDerecho(), nodo);
                    nodo.recalcularAltura();
                }
            }

        }
        return exito;
    }

    public boolean eliminar(Comparable clave) {
        /*este metodo elimina la clave con su dato de la tabla
        devuelve false en el caso en que la clave no este en la tabla
        true si la encuentra
        */
        boolean exito = eliminarAux(this.raiz, clave, null);
        this.raiz.recalcularAltura();
        chequearBalanceo(this.raiz, null);
        this.raiz.recalcularAltura();
        return exito;

    }

    private boolean eliminarAux(NodoAVLDic nodo, Comparable clave, NodoAVLDic padre) {
        /*metodo privado recursivo que busca la clave en la tabla y la elimina junto con su dato*/
        NodoAVLDic hijo = null;
        boolean encontrado = false;
        if (nodo != null) {
            int res = clave.compareTo(nodo.getClave());
            //si se encontro el elemento que se quiere eliminar verifica que caso le aplica
            if (res == 0) {
                hijo = nodo;
                encontrado = true;
                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                    //es hoja
                    caso1(padre, hijo);

                } else {
                    if (hijo.getIzquierdo() != null && hijo.getDerecho() != null) {
                        caso3(hijo, padre);

                    } else {
                        if (hijo.getIzquierdo() != null) {
                            caso2(padre, hijo, hijo.getIzquierdo());
                        } else {
                            caso2(padre, hijo, hijo.getDerecho());
                        }
                    }
                }
            } else {
                //sino llama al lado que corresponde
                if (res < 0) {
                    encontrado = eliminarAux(nodo.getIzquierdo(), clave, nodo);
                    nodo.recalcularAltura();
                    chequearBalanceo(nodo, padre);
                    nodo.recalcularAltura();
                } else {
                    encontrado = eliminarAux(nodo.getDerecho(), clave, nodo);
                    nodo.recalcularAltura();
                    chequearBalanceo(nodo, padre);
                    nodo.recalcularAltura();

                }

            }

        }
        return encontrado;

    }

    private void caso1(NodoAVLDic padre, NodoAVLDic hijo) {
        /*este metodo elimina el nodo que es hoja*/
        //si el padre es null significa que queremos eliminar la raiz 
        if (padre == null) {
            this.raiz = null;
        } else {
            //sino buscamos de que lado del padre estaba el hijo
            if (padre.getIzquierdo() != null && padre.getIzquierdo().getClave().compareTo(hijo.getClave()) == 0) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }

        }
    }

    private void caso2(NodoAVLDic padre, NodoAVLDic hijo, NodoAVLDic nieto) {
        /*este metodo elimina al nodo que tiene a uno de sus hijos*/
        //nos fijamos si el elemento a eliminar es hijo izquierdo o derecho
        if (padre.getIzquierdo() != null && padre.getIzquierdo().getClave().equals(hijo.getClave())) {
            //procedemos a eliminar
            padre.setIzquierdo(nieto);
        } else {
            padre.setDerecho(nieto);
        }
    }

    private void caso3(NodoAVLDic hijo, NodoAVLDic padre) {
        /*este metodo elimina al nodo que tiene a sus dos hijos*/
        //busca el candidato a reemplazar al nodo a eliminar
        NodoAVLDic nuevoNodo = encontrarCandidato(hijo.getIzquierdo(), hijo);
        //lllamos al chequear balanceo por si se produjo un desbalanceo al eliminar al candidato
        chequearBalanceo(hijo,padre);
        hijo.setClave(nuevoNodo.getClave());
        hijo.setDato(nuevoNodo.getDato());

    }

    private NodoAVLDic encontrarCandidato(NodoAVLDic hijoIzquierdo, NodoAVLDic padre) {
        /*este metodo busca un candidato para reemplazar al nodo que sera eliminado
        */
        //busco el mayor elemento del lado izquierdo der arbol
        NodoAVLDic candidato = null;
        if (hijoIzquierdo.getDerecho() == null) {
            candidato = hijoIzquierdo;
            //llama a eliminar al candidato segun el caso que corresponda
            if (candidato.getIzquierdo() == null) {
                caso1(padre, candidato);
            } else {
                caso2(padre, candidato, candidato.getIzquierdo());
            }
            //luego de eliminar reestablacemos la altura del padre
            padre.recalcularAltura();
        } else {
            //si el hijo derecho no es null avanzo
            candidato = encontrarCandidato(hijoIzquierdo.getDerecho(), hijoIzquierdo);
        }
        return candidato;
    }

    private int chequearBalanceo(NodoAVLDic nodo, NodoAVLDic padre) {
        /*este metodo chequea el balanceo de un nodo y llama a rebalancear cuando sea necesario*/
        int balanceo = -1;
        int altIzq = -1, altDer = -1;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                altIzq = nodo.getIzquierdo().getAltura();
            }
            if (nodo.getDerecho() != null) {
                altDer = nodo.getDerecho().getAltura();
            }
            balanceo = altIzq - altDer;
            //si esta desbalanceado ya sea a la izquierda o a la derecha llama a rebalancear
            if (balanceo == 2 || balanceo == -2) {
                rebalancear(balanceo, nodo, padre);
            }
        }
        return balanceo;
    }

    private void rebalancear(int balanceo, NodoAVLDic nodo, NodoAVLDic padre) {
        /*este metodo rebalancea haciendo las rotaciones necesarios*/
        int balanceoHijo;
        NodoAVLDic n = null;
        //si esta balaceado a la izquierda
        if (balanceo == 2) {
            balanceoHijo = chequearBalanceo(nodo.getIzquierdo(), nodo);
            if (balanceoHijo == 0 || balanceoHijo == 1) {
                n = rotarDerecha(nodo); 
            } else {
                n = rotacionIzquierdaDerecha(nodo);
            }
        } else {
            //si esta balanceado a la derecha
            balanceoHijo = chequearBalanceo(nodo.getDerecho(), nodo);
            if (balanceoHijo == 0 || balanceoHijo == -1) {
                n = rotarIzquierda(nodo); 
            } else {
                n = rotacionDerechaIzquierda(nodo); 
            }
        }
        //si el padre es null a nueva raiz sera n
        if (padre == null) {
            this.raiz = n;
        } else {
            //sino buscamos el lado al que hay que setearle al padre
            if (padre.getIzquierdo().equals(nodo)) {
                padre.setIzquierdo(n);
            } else {
                padre.setDerecho(n);
            }
        }
    }

    private NodoAVLDic rotarIzquierda(NodoAVLDic r) {
        NodoAVLDic h = r.getDerecho();
        NodoAVLDic temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);

        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDic rotarDerecha(NodoAVLDic r) {
        NodoAVLDic h = r.getIzquierdo();
        NodoAVLDic temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    private NodoAVLDic rotacionIzquierdaDerecha(NodoAVLDic r) {
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo()));
        return rotarDerecha(r);
    }

    private NodoAVLDic rotacionDerechaIzquierda(NodoAVLDic r) {
        r.setDerecho(rotarDerecha(r.getDerecho()));
        return rotarIzquierda(r);
    }

    public Object obtenerDato(Comparable clave) {
        /*este metodo devuelve el objeto asociada a la clave ingresada por parametro
        devuelve null en el caso en que la clave no exista 
        */
        return buscarDato(this.raiz, clave);
    }

    private Object buscarDato(NodoAVLDic nodo, Comparable clave) {
        /*este metodo privado recursivo busca el dato con la clave recibida por parametro
        devuelve null si la clave no se encuentra en la tabla*/
        Object datoBuscado = null;
        if (nodo != null) {
            int res = clave.compareTo(nodo.getClave());
            //si se encontro el nodo que contiene al dato buscado lo obtenemos
            if (res == 0) {
                datoBuscado = nodo.getDato();
            } else {
                //sino llama al lado que corresponde
                if (res < 0) {
                    datoBuscado = buscarDato(nodo.getIzquierdo(), clave);

                } else {
                    datoBuscado = buscarDato(nodo.getDerecho(), clave);
                }
            }
        }
        return datoBuscado;

    }

    public boolean existeClave(Comparable clave) {
        /*este metodo retorna true si la clave esta en la tabla, false en el caso contrario*/
        return buscarClave(this.raiz, clave);
    }

    private boolean buscarClave(NodoAVLDic nodo, Comparable clave) {
        /*este metodo busca la clave en la tabla si la encuentra devuelve true false en el caso contrario*/
        boolean encontrado = false;
        if (nodo != null) {
            int res = clave.compareTo(nodo.getClave());
            //se encuentra la clave
            if (res == 0) {
                encontrado = true;
            } else {
                //sino llama al lado que corresponde
                if (res < 0) {
                    encontrado = buscarClave(nodo.getIzquierdo(), clave);

                } else {
                    encontrado = buscarClave(nodo.getDerecho(), clave);
                }
            }
        }
        return encontrado;
    }

    public Lista listarClaves() {
        /*este metodo devuelve una lista con todas las claves de la tabla
        devuelve una lista vacia si la tabla esta vacia*/
        Lista lista = new Lista();
        listarClavesAux(this.raiz, lista, 1);
        return lista;
    }

    private int listarClavesAux(NodoAVLDic nodo, Lista lista, int pos) {
        /*este metodo lista las claves de la tabla*/
        if (nodo != null) {
            //inserta la clave en la lista
            lista.insertar(nodo.getClave(), pos);
            //llama recursivamente con el hijo izquierdo
            if (nodo.getIzquierdo() != null) {
                pos = listarClavesAux(nodo.getIzquierdo(), lista, pos + 1);
            }
            //llama recursivamente con el hijo derecho 
            if (nodo.getDerecho() != null) {
                pos = listarClavesAux(nodo.getDerecho(), lista, pos + 1);
            }
        }
        return pos;
    }

    public Lista listarDatos() {
        /* este metodo devuelve una lista con todos los datos de la tabla
        devuelve una lista vacia en el caso en que la tabla este vacia*/
        Lista lista = new Lista();
        listarDatosAux(this.raiz, lista, 1);
        return lista;
    }

    private int listarDatosAux(NodoAVLDic nodo, Lista lista, int pos) {
        /*este metodo lista todos los datos de la tabla*/
        if (nodo != null) {
            //inserta el dato en la lista
            lista.insertar(nodo.getDato(), pos);
            //llama recursivamente con el hijo izquierdo
            if (nodo.getIzquierdo() != null) {
                pos = listarDatosAux(nodo.getIzquierdo(), lista, pos + 1);
            }
            //llama recursivamente con el hijo derecho
            if (nodo.getDerecho() != null) {
                pos = listarDatosAux(nodo.getDerecho(), lista, pos + 1);
            }
        }
        return pos;
    }

    public boolean esVacio() {
        /*devuelve true si esta vacia, false en el caso contrario*/
        return this.raiz == null;
    }

    public String toString() {
        /*devuelve una lista con las claves y los datos de la tabla*/
        return concatenar(this.raiz);
    }

    private String concatenar(NodoAVLDic n) {
        //este metodo privado y recursivo va concantenando cadenas con la informacion de las claves y los datos de la tabla
        String cadenaAux = "", cadena = "Tabla Vacia";
        if (n != null) {
            cadena = "";
            //añade la info del nodo actual
            cadena += "\nNodo padre Clave:" + n.getClave() + " Dato:" + n.getDato() + " Altura:" + n.getAltura() + "\n";
            //si tiene hijo izquierdo añada la informacion del nodo
            if (n.getIzquierdo() != null) {
                cadena += "HI:" + " Clave:" + n.getIzquierdo().getClave() + " Dato:" + n.getIzquierdo().getDato() + " Altura:" + n.getIzquierdo().getAltura() + "\n";
            } else {
                cadena += "HI:- " + "\n";
            }
            //si tiene hijo derecho añade la informacion del nodo
            if (n.getDerecho() != null) {
                cadena += "HD:" + " Clave:" + n.getDerecho().getClave() + " Dato:" + n.getDerecho().getDato() + " Altura:" + n.getDerecho().getAltura() + "\n";
            } else {
                cadena += "HD:-" + "\n";
            }
            //llama con el hijo izquierdo o derecho si no son nulos
            if (n.getIzquierdo() != null) {
                cadenaAux = concatenar(n.getIzquierdo());
                cadena += cadenaAux;
            }
            if (n.getDerecho() != null) {
                cadenaAux = concatenar(n.getDerecho());
                cadena += cadenaAux;
            }
        }
        return cadena;
    }
     public TablaDeBusqueda clone() {
        /*este metodo devuelve una tabla, copia de la original*/
        TablaDeBusqueda clone = new TablaDeBusqueda();
        if (this.raiz != null) {
            clone.raiz = new NodoAVLDic(this.raiz.getClave(),this.raiz.getDato(), null, null,this.raiz.getAltura());
            //invocamos al metodo auxClon
            auxClon(this.raiz, clone.raiz);

        }
        return clone;

    }

    private void auxClon(NodoAVLDic nodo, NodoAVLDic aux2) {
        //este metodo privado y recursivo crea el clone de la tabla original
        //precondicion nodo distinto de null
        if (nodo.getIzquierdo() != null) {
            //si existe un nodo izquierdo
            //creamos un nodo nuevo a la izquierda del nodo actual del clon
            aux2.setIzquierdo(new NodoAVLDic(nodo.getIzquierdo().getClave(),nodo.getIzquierdo().getDato(), null, null,nodo.getIzquierdo().getAltura()));
            //invocamos recursivamente al metodo
            auxClon(nodo.getIzquierdo(), aux2.getIzquierdo());

        }
        if (nodo.getDerecho() != null) {
            //si existe un nodo derecho
            //creamos un nodo nuevo a la derecha del nodo actual del clon
            aux2.setDerecho(new NodoAVLDic(nodo.getDerecho().getClave(),nodo.getDerecho().getDato(), null, null,nodo.getDerecho().getAltura()));
            //invocamos recursivamente al metodo
            auxClon(nodo.getDerecho(), aux2.getDerecho());
        }

    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        /*este metodo devuelve una lista con las claves que esten dentro del rango [elemMinimo, elemMaximo]*/
        Lista listaRango = new Lista();
        listarRangoAux(this.raiz, listaRango, elemMinimo, elemMaximo,1);
        return listaRango;
    }

     private int listarRangoAux(NodoAVLDic n, Lista lista, Comparable elemMin, Comparable elemMaximo,int pos) {
        /*este metodo devuelve lista las claves que esten dentro del rango [elemMinimo, elemMaximo]*/
        if (n != null) {
            //si esta en el rango lo inserta
            if ((elemMin.compareTo(n.getClave()) <= 0) && (elemMaximo.compareTo(n.getClave()) >= 0)) {
                lista.insertar(n.getClave(),pos);
                pos++;
            }
            //si es menor llama con la parte izquierda
            if (elemMin.compareTo(n.getClave()) < 0) {
                pos=listarRangoAux(n.getIzquierdo(), lista, elemMin, elemMaximo,pos);
            }
            //si es mayor llama con la parte derecha
            if (elemMaximo.compareTo(n.getClave()) > 0) {
               pos=listarRangoAux(n.getDerecho(), lista, elemMin, elemMaximo,pos);
            }
        }
        return pos;
    }

}
