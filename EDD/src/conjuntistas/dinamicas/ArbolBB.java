/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;
import lineales.dinamicas.Lista;
import conjuntistas.dinamicas.NodoArbol;

/**
 *
 * @author Anto
 */
public class ArbolBB {

    private NodoArbol raiz;

    public ArbolBB() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoArbol(elem, null, null);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean insertarAux(NodoArbol nodo, Comparable elem) {
        boolean exito = false;
        int resultado = elem.compareTo(nodo.getElem());
        //si son iguales da error ya que no pueden existir elementos repetidos
        if (resultado == 0) {
            exito = false;
        } else {
            //si el elemento a insertar es menor al elemento que se encuentra en el nodo bajamos a la izquierda
            if (resultado < 0) {
                //si no tiene hijo izquierda inserta el elemento nuevo
                if (nodo.getIzquierdo() == null) {
                    nodo.setIzquierdo(new NodoArbol(elem, null, null));
                    exito = true;
                } else {
                    //si tiene llama recursivamente con el 
                    exito = insertarAux(nodo.getIzquierdo(), elem);
                }
            } //si el elemento a insertar es mayor que el elemento que se encuentra en el nodo bajamos a la derecha
            else {
                //si no tiene hijo derecho inserta el elemento nuevo
                if (nodo.getDerecho() == null) {
                    nodo.setDerecho(new NodoArbol(elem, null, null));
                    exito = true;
                } else {
                    exito = insertarAux(nodo.getDerecho(), elem);
                }

            }

        }

        return exito;
    }

    public boolean pertenece(Comparable elem) {
        boolean pertenece = false;
        if (this.raiz != null) {
            pertenece = perteneceAux(this.raiz, elem);
        }
        return pertenece;
    }

    private boolean perteneceAux(NodoArbol nodo, Comparable elem) {
        boolean exito = false;
        int resultado = elem.compareTo(nodo.getElem());
        //si son iguales encontramos el elemento
        if (resultado == 0) {
            exito = true;
        } else {
            //si el elemento a buscado es menor al elemento que se encuentra en el nodo bajamos a la izquierda
            if (resultado < 0) {
                //si llegamos a la hoja y no lo encontro no pertenece al arbol
                if (nodo.getIzquierdo() == null) {
                    exito = false;
                } else {
                    //si tiene hijo izq llama recursivamente con el 
                    exito = perteneceAux(nodo.getIzquierdo(), elem);
                }
            } //si el elemento buscado es mayor que el elemento que se encuentra en el nodo bajamos a la derecha
            else {
                //si llegamos a la hoja y no lo encontro no pertenece al arbol
                if (nodo.getDerecho() == null) {
                    exito = false;
                } else {
                    exito = perteneceAux(nodo.getDerecho(), elem);
                }

            }

        }
        return exito;
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = false;
        if (this.raiz != null) {
           eliminarAux(this.raiz, elem,null);
        }
        return exito;
    }

    private NodoArbol eliminarAux(NodoArbol nodo, Comparable elem, NodoArbol padre) {
        int res = elem.compareTo(nodo.getElem());
        NodoArbol hijo = null;
        if (nodo!=null){
            if (res == 0) {
                hijo = nodo;
                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                //es hoja
                caso1(padre, hijo.getElem());
            } 
            else {
                if (hijo.getIzquierdo() != null && hijo.getDerecho() == null) {
                    //el elemento a eliminar tiene hijo izquierdo
                    caso2(padre, hijo, 'I');
                }
                else {
                    //el elemento a eliminar tiene hijo derecho
                    if (hijo.getDerecho() != null && hijo.getIzquierdo() == null) {
                        caso2(padre, hijo, 'D');
                    } 
                    else {
                        //el elemento tiene a los 2 hijos
                        caso3(hijo);
                    }
            }
            }
        } else {
            if (res < 0) {
                eliminarAux(nodo.getIzquierdo(), elem, nodo);
            } else {
                eliminarAux(nodo.getDerecho(), elem, nodo);
            }
        }
        
        
        }
        return hijo;
    }

    private void caso1(NodoArbol nodoP,Comparable elemEliminar){
        //es hoja
        // busco si hay que eliminar el lado derecho o izquierdo
        if (nodoP.getIzquierdo()!=null && nodoP.getIzquierdo().getElem().equals(elemEliminar)){
            nodoP.setIzquierdo(null);
        }else{
            if(nodoP.getDerecho()!=null && nodoP.getDerecho().getElem().equals(elemEliminar)){
                nodoP.setDerecho(null);
            }
        }
    }
    private void caso2(NodoArbol nodoP,NodoArbol elemEliminar, char hijo){
        //la variable char almacena el hijo que tiene, sitiene un izquierdo o
        //tiene un hijo
        if (nodoP.getIzquierdo()!=null && nodoP.getIzquierdo().getElem().equals(elemEliminar.getElem())){
            //si el elemento que quiero eliminar tiene hijo izquierdo
            if(hijo=='I'){
                nodoP.setIzquierdo(elemEliminar.getIzquierdo());
            }
            //si el elemento que quiero eliminar tiene hijo derecho
            else{
                nodoP.setIzquierdo(elemEliminar.getDerecho());
            }
            
        }else{
            if(nodoP.getDerecho().getElem().equals(elemEliminar.getElem()) ){
               //si el elemento que quiero eliminar tiene hijo izquierdo
                if(hijo=='I'){
                    nodoP.setDerecho(elemEliminar.getIzquierdo());
                }
                //si el elemento que quiero eliminar tiene hijo derecho
                else{
                    nodoP.setDerecho(elemEliminar.getDerecho());
                }
            }
        }
    }
    private void caso3(NodoArbol hijo){
        NodoArbol nuevoNodo=encontrarCandidato(hijo.getIzquierdo());
        eliminarAux(this.raiz, nuevoNodo.getElem(),null);
        //despues de eliminar el elemento que era candidato seteamos el elemento
        hijo.setElem(nuevoNodo.getElem());
        
        
    }
    private NodoArbol encontrarCandidato(NodoArbol hijoIzquierdo){
        //busco el mayor elemento del lado izquierdo der arbol
        NodoArbol candidato=null;
        if (hijoIzquierdo.getDerecho()==null){
            candidato=hijoIzquierdo;
        }
        else{
            candidato=encontrarCandidato(hijoIzquierdo.getDerecho());
        }
        return candidato;
    }

    public boolean esVacio(){
        return (this.raiz==null);
    }
    public void vaciar(){
        this.raiz=null;
    }
     
    public Lista listar() {
        //este metodo devuelve una lista con los elementos en forma ordenada
        //creamos la lista que vamos a usar
        Lista lista = new Lista();
        //invocamos al metodo listarAux
        listarAux(this.raiz, lista, 1);
        return lista;
    }

    private int listarAux(NodoArbol nodo, Lista lista, int pos) {
        //este metodo privado y recursivo devuelve la posicion en la cual debemos insertar un elemento
        if (nodo != null) {
            //siempre que el nodo recibido sea distinto de null
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                //si llegamos a una hoja
                //vamos a insertarlo e incrementar pos
                lista.insertar(nodo.getElem(), pos);
                pos++;
            } else {
                //si no estamos en una hoja
                //seguimos recorriendo el arbol/subArbol por el lado izquierdo
                pos = listarAux(nodo.getIzquierdo(), lista, pos);
                //insertamos el nodo actual, que viene a ser el nodo padre
                lista.insertar(nodo.getElem(), pos);
                pos++;
                //incrementamos pos y repetimos el proceso con el lado derecho
                pos = listarAux(nodo.getDerecho(), lista, pos);
            }

        }
        return pos;
    }

    public Comparable minElem (){
        Comparable elem=null;
        if (this.raiz!=null){
            elem=buscarMinElem(this.raiz);
        }
        return elem;
    }
    
    private Comparable buscarMinElem(NodoArbol nodo){
        Comparable elem;
        //si llegamos a una hoja encontramos el minimo elemento y lo almaceneamos
        if (nodo.getIzquierdo()==null){
            elem=nodo.getElem();
        }
        else{
            //sino seguimos bajando por el lado izquierdo del arbol
            elem=buscarMinElem(nodo.getIzquierdo());
        }
        return elem;
    }
     public Comparable maxElem (){
        Comparable elem=null;
        if (this.raiz!=null){
            elem=buscarMaxElem(this.raiz);
        }
        return elem;
    }
    
    private Comparable buscarMaxElem(NodoArbol nodo){
        Comparable elem;
        //si llegamos a una hoja encontramos el maximo elemento y lo almaceneamos
        if (nodo.getDerecho()==null){
            elem=nodo.getElem();
        }
        else{
            //sino seguimos bajando por el lado derecho del arbol
            elem=buscarMaxElem(nodo.getDerecho());
        }
        return elem;
    }
    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo){
        Lista listaRango= new Lista ();
        listar2(this.raiz, listaRango, 1, elemMinimo, elemMaximo);
        return listaRango;
        
    }
  
    
    private int listar2(NodoArbol nodo, Lista lista, int pos, Comparable elemMin, Comparable elemMax) {
        if (nodo != null) {
            //si el elemento maximo es menor al elemento del nodo
            if (elemMax.compareTo(nodo.getElem()) < 0) {
                pos = listar2(nodo.getIzquierdo(), lista, pos, elemMin, elemMax);
            } else {
                if ((elemMin.compareTo(nodo.getElem()) <= 0) && elemMax.compareTo(nodo.getElem()) >= 0) {
                    lista.insertar(nodo.getElem(), pos);
                    pos++;
                }
                else{
                    if (elemMin.compareTo(nodo.getElem()) >0) {
                    //si estas en un nodo que es menor al elemento minimo buscado llama a la izquierda 
                    pos = listar2(nodo.getDerecho(), lista, pos, elemMin, elemMax);
                    }
                } 
            }
           
        }
         return pos;
    }
     public ArbolBB clone() {
        //este metodo devuelve un arbol identico al arbol original 
        //creamos e inicializamos el arbol clone
        ArbolBB clone = new ArbolBB();
        if (this.raiz != null) {
            clone.raiz = new NodoArbol(this.raiz.getElem(), null, null);;
            //invocamos al metodo auxClon
            auxClon(this.raiz, clone.raiz);

        }
        return clone;

    }

    private void auxClon(NodoArbol nodo, NodoArbol aux2) {
        //este metodo privado y recursivo crea el clone del arbol original
        //precondicion nodo distinto de null
        if (nodo.getIzquierdo() != null) {
            //si existe un nodo izquierdo
            //creamos un nodo nuevo a la izquierda del nodo actual del clon
            aux2.setIzquierdo(new NodoArbol(nodo.getIzquierdo().getElem(), null, null));
            //invocamos recursivamente al metodo
            auxClon(nodo.getIzquierdo(), aux2.getIzquierdo());

        }
        if (nodo.getDerecho() != null) {
            //si existe un nodo derecho
            //creamos un nodo nuevo a la derecha del nodo actual del clon
            aux2.setDerecho(new NodoArbol(nodo.getDerecho().getElem(), null, null));
            //invocamos recursivamente al metodo
            auxClon(nodo.getDerecho(), aux2.getDerecho());
        }

    }
    public String toString() {
        //este metodo devuelve un String con todos los elementos del arbol
        return concatenar(this.raiz);
    }

    private String concatenar(NodoArbol n) {
        //este metodo privado y recursivo devuelve un String con el contenido del arbol/subArbol
        //creamos e inicializamos las cadenas auxiliares
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
     
 
}
