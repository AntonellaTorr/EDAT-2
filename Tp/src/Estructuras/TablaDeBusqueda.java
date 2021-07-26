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
        boolean exito = eliminarAux(this.raiz, clave, null);
        this.raiz.recalcularAltura();
        chequearBalanceo(this.raiz, null);
        this.raiz.recalcularAltura();
        return exito;

    }

    private boolean eliminarAux(NodoAVLDic nodo, Comparable clave, NodoAVLDic padre) {
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
        if (padre == null) {
            this.raiz = null;
        } else {
            if (padre.getIzquierdo() != null && padre.getIzquierdo().getClave().compareTo(hijo.getClave()) == 0) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }

        }
    }

    private void caso2(NodoAVLDic padre, NodoAVLDic hijo, NodoAVLDic nieto) {

        //nos fijamos si el elemento a eliminar es hijo izquierdo o derecho
        if (padre.getIzquierdo() != null && padre.getIzquierdo().getClave().equals(hijo.getClave())) {
            //procedemos a eliminar
            padre.setIzquierdo(nieto);
        } else {
            padre.setDerecho(nieto);
        }
    }

    private void caso3(NodoAVLDic hijo, NodoAVLDic padre) {
        //cambiar por un set clave
        NodoAVLDic nuevoNodo = encontrarCandidato(hijo.getIzquierdo(), hijo);
        hijo.setClave(nuevoNodo.getClave());
        hijo.setDato(nuevoNodo.getDato());

    }

    private NodoAVLDic encontrarCandidato(NodoAVLDic hijoIzquierdo, NodoAVLDic padre) {
        //actualizar las alturas a la vuelta
        //busco el mayor elemento del lado izquierdo der arbol
        NodoAVLDic candidato = null;
        if (hijoIzquierdo.getDerecho() == null) {
            candidato = hijoIzquierdo;
            if (candidato.getIzquierdo() == null) {
                caso1(padre, candidato);
            } else {
                caso2(padre, candidato, candidato.getIzquierdo());
            }
        } else {
            //si el hijo derecho no es null avanzo
            candidato = encontrarCandidato(hijoIzquierdo.getDerecho(), hijoIzquierdo);
        }
        return candidato;
    }

    private int chequearBalanceo(NodoAVLDic nodo, NodoAVLDic padre) {
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
        int balanceoHijo;
        NodoAVLDic n = null;
        if (balanceo == 2) {
            balanceoHijo = chequearBalanceo(nodo.getIzquierdo(), nodo);
            if (balanceoHijo == 0 || balanceoHijo == 1) {
                n = rotarDerecha(nodo); //OK
            } else {
                n = rotacionIzquierdaDerecha(nodo);
            }
        } else {
            balanceoHijo = chequearBalanceo(nodo.getDerecho(), nodo);
            if (balanceoHijo == 0 || balanceoHijo == -1) {
                n = rotarIzquierda(nodo); //OK
            } else {
                n = rotacionDerechaIzquierda(nodo); //OK
            }
        }

        if (padre == null) {
            this.raiz = n;
        } else {
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
        return buscarDato(this.raiz, clave);
    }

    private Object buscarDato(NodoAVLDic nodo, Comparable clave) {
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
        return buscarClave(this.raiz, clave);
    }

    private boolean buscarClave(NodoAVLDic nodo, Comparable clave) {
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
        Lista lista = new Lista();
        listarClavesAux(this.raiz, lista, 1);
        return lista;
    }

    public int listarClavesAux(NodoAVLDic nodo, Lista lista, int pos) {
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
        Lista lista = new Lista();
        listarDatosAux(this.raiz, lista, 1);
        return lista;
    }

    private int listarDatosAux(NodoAVLDic nodo, Lista lista, int pos) {
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
        return this.raiz == null;
    }

    public String toString() {
        return concatenar(this.raiz);
    }

    private String concatenar(NodoAVLDic n) {
        //este metodo privado y recursivo devuelve un String con el contenido del arbol/subArbol
        String cadenaAux = "", cadena = "arbol vacio";
        if (n != null) {
            cadena = "";
            cadena += "\nNodo padre Clave:" + n.getClave() + " Dato:" + n.getDato() + " Altura:" + n.getAltura() + "\n";
            if (n.getIzquierdo() != null) {
                cadena += "HI:" + " Clave:" + n.getIzquierdo().getClave() + " Dato:" + n.getIzquierdo().getDato() + " Altura:" + n.getIzquierdo().getAltura() + "\n";
            } else {
                cadena += "HI:- " + "\n";
            }

            if (n.getDerecho() != null) {
                cadena += "HD:" + " Clave:" + n.getDerecho().getClave() + " Dato:" + n.getDerecho().getDato() + " Altura:" + n.getDerecho().getAltura() + "\n";
            } else {
                cadena += "HD:-" + "\n";
            }
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

    public String buscarDesafiosTipo(String tipo, int puntMin, int puntMax) {
        return buscarDesafiosAux(this.raiz, tipo, puntMin, puntMax);
    }

    private String buscarDesafiosAux(NodoAVLDic nodo, String tipo, int puntMin, int puntMax) {
        String cad = "", cadAux;
        if (nodo != null) {
            Desafio d = (Desafio) nodo.getDato();
            if (d.getTipo().equalsIgnoreCase(tipo) && d.getPuntajeAOtorgar() >= puntMin && d.getPuntajeAOtorgar() <= puntMax) {
                cad = d.toString();
            }

            if (nodo.getIzquierdo() != null) {
                cadAux = buscarDesafiosAux(nodo.getIzquierdo(), tipo, puntMin, puntMax);
                cad += cadAux;
            }
            if (nodo.getDerecho() != null) {
                cadAux = buscarDesafiosAux(nodo.getDerecho(), tipo, puntMin, puntMax);
                cad += cadAux;
            }
        }
        return cad;
    }

}
