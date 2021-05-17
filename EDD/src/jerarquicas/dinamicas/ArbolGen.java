/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

/**
 *
 * @author Anto
 */
public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        //atributo de la clase
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre) {
        boolean exito = false;
        //si el arbol esta vacio crea un nuevo nodo y lo define como raiz
        if (this.raiz == null) {
            this.raiz = new NodoGen(elemNuevo, null, null);
            exito=true;
        } else {
            //sino busca al elemento padre y crea un hijo
            NodoGen padre = obtenerNodo(this.raiz, elemPadre);
            if (padre != null) {
                //el nodo padre apunta al nuevo nodo
                //si padre ya tenia hijos este nuevo nodo apunta a uno de sus hermanos y sino apunta a null
                padre.setHijoIzquierdo(new NodoGen(elemNuevo, null, padre.getHijoIzquierdo()));
                exito = true;
            }
        }
        return exito;
    }

    private NodoGen obtenerNodo(NodoGen nodo, Object elemPadre) {
        //este metodo busca un elemento en el arbol y retorna el nodo que lo contiene
        NodoGen resultado = null;
        if (nodo != null) {
            //si el nodo recibido por parametro tiene al elemento buscado retornamos a ese nodo
            if (nodo.getElem().equals(elemPadre)) {
                resultado = nodo;
            } else {
                //sino lo buscamos en sus hijos llamando recursivamente con cada uno de ellos 
                //hasta encontrarlo o hasta no tener mas hermanos
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && resultado == null) {
                    resultado = obtenerNodo(hijo, elemPadre);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Object elem) {
        //este metodo devuelve true si el elemento recibido por parametro se encuentra en el arbol y false si no
        return (obtenerNodo(this.raiz, elem)!=null);
    }
    public Lista ancestros(Object elem) {
        //este metodo retorna una lista con los ancestros del elemento recibido por parametro
        Lista listaAncestros = new Lista();
        //llamada al metodo recursivo privado
        buscarAncestros(this.raiz, elem, listaAncestros, 1);
        return listaAncestros;
    }

    private boolean buscarAncestros(NodoGen nodo, Object elem, Lista lista, int pos) {
        //este metodo busca los ancestros del elemento recibido por parametro y retorna una lista con todos ellos
        boolean encontrado = false;
        if (nodo != null) {
            //si encontramos el elemento encontrado se vuelve true
            if (nodo.getElem().equals(elem)) {
                encontrado = true;
            }
            else{
                //sino insertamos en la lista el elemento
                lista.insertar(nodo.getElem(), pos);
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && !encontrado) {
                    encontrado = buscarAncestros(hijo, elem, lista, pos + 1);
                    hijo = hijo.getHermanoDerecho();
                }
                //si nunca se encontro el elemento eliminamos el elemento que habiamos insertado
                if (!encontrado){
                    lista.eliminar(pos);
                }
                
            }
        }

        return encontrado;
    }

    public boolean esVacio() {
        //este metodo devuelve true si el arbol esta vacio y false si no lo esta
        return (this.raiz == null);
    }

    public void vaciar() {
        //este metodo vacia el arbol
        this.raiz = null;
    }

     public int altura (){
        //este metodo retorna la altura del arbol
        return alturaRecursivo(this.raiz);        
    }
    private int alturaRecursivo(NodoGen n) {
           int alturaActual=-1, alturaDelHermano=-1;
           if(n!=null) {
               if(n.getHijoIzquierdo()==null) {
                       alturaActual=0;
               }
               alturaActual=alturaRecursivo(n.getHijoIzquierdo())+1;
               NodoGen nodoAux=n.getHermanoDerecho();
               while(nodoAux!=null) {
                       alturaDelHermano=alturaRecursivo(n.getHermanoDerecho());
                       alturaActual=Math.max(alturaActual, alturaDelHermano);
                       nodoAux=nodoAux.getHermanoDerecho();
               }
           }
           return alturaActual;
       }

    public int nivel(Object elem) {
        //este metodo retorna el nivel del elemento buscado en arbol
        return buscarNivel(this.raiz, elem, -1);
    }

    private int buscarNivel(NodoGen nodo, Object elem, int nivel) {
        //este metodo busca el nivel del elemento recibido por parametro y lo retorna 
        int encontrado = -1;
        if (nodo != null) {
            //si el elemento esta en el nodo actual encontrado ahora almacena el nivel en el que esta
            if (nodo.getElem().equals(elem)) {
                encontrado = nivel + 1;
            } else {
                //sino hay que recorrer todos los hermanos hasta que encontremos el elemento buscado o hasta no tener mas hijos
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (encontrado == -1 && hijo != null) {
                    encontrado = buscarNivel(hijo, elem, nivel + 1);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return encontrado;
    }

    public Object padre(Object elem) {
        //este metodo devuelve el padre del elemento buscado
        Object padre = null;
        //si el arbol no esta vacio y ni tampoco es el elemento buscado llamamos al metodo
        if (this.raiz!=null &&!this.raiz.getElem().equals(elem)) {
            padre = padreAux(this.raiz, elem);
        }
        return padre;

    }

    private Object padreAux(NodoGen nodo, Object elem) {
        Object padre = null;
        if (nodo != null && nodo.getHijoIzquierdo() != null) {
            //verifica si alguno los hijos es el elemento buscado
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null && padre == null) {
                if (hijo.getElem().equals(elem)) {
                    padre = nodo.getElem();
                }
                hijo = hijo.getHermanoDerecho();
            }
            //si no lo encontró
            if (padre == null) {
                //llama recursivamente con cada hijo 
                hijo = nodo.getHijoIzquierdo();
                while (padre == null && hijo != null) {
                    padre = padreAux(hijo, elem);
                    hijo = hijo.getHermanoDerecho();
                }

            }
        }
        return padre;
    }


    public Lista listarInorden() {
        //este metodo devuelve una lista con los elementos del arbol listados en inorden
        Lista lista = new Lista();
        //llamada al metodo recursivo privado
        listarInordenAux(this.raiz, lista);
        return lista;
    }

    private void listarInordenAux(NodoGen nodo, Lista lista) {
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() != null) {
                listarInordenAux(nodo.getHijoIzquierdo(), lista);
            }
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            if (nodo.getHijoIzquierdo() != null) {
                NodoGen hijo = nodo.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarInordenAux(hijo, lista);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPreorden() {
        //este metodo devuelve una lista con los elementos del arbol listados en preorden
        Lista lista = new Lista();
        //llamada al metodo recursivo privado
        listarPreordenAux(this.raiz, lista, 1);
        return lista;
    }

   
    private int listarPreordenAux(NodoGen nodo, Lista lista, int pos) {
        if (nodo != null) {
            //visitamos la raiz 
            lista.insertar(nodo.getElem(), pos);
            //llamamos con los hijos
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                pos = listarPreordenAux(hijo, lista, pos + 1);
                hijo = hijo.getHermanoDerecho();
            }
            
        }
        return pos;
    }
    

    public Lista listarPosorden() {
        //este metodo devuelve una lista con los elementos del arbol listados en posOrden
        Lista lista = new Lista();
        //llamada al metodo privado recursivo
        listarPosordenAux(this.raiz, lista, 1);
        return lista;
    }

     private int listarPosordenAux(NodoGen nodo, Lista lista, int pos) {
        if (nodo != null) {
            //llama con los hijos
            NodoGen hijo=nodo.getHijoIzquierdo();
            while (hijo != null) {
                pos = listarPosordenAux(hijo, lista, pos);
                hijo = hijo.getHermanoDerecho();
            }
            //cuando llega a la hoja inserta
            lista.insertar(nodo.getElem(), pos);
            pos++;
        }
        return pos;
    }

    public Lista listarPorNiveles() {
        //este metodo devuelve una lista con los elementos del arbol listados por niveles
        Lista lista = new Lista();
        if (this.raiz != null) {
            Cola q = new Cola();
            q.poner(this.raiz);
            int i = 1;
            while (!q.esVacia()) {
                NodoGen nodo = (NodoGen) q.obtenerFrente();
                q.sacar();
                //insertamos el elemento
                lista.insertar(nodo.getElem(), i);
                i++;
                //insertamos los hijos
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null) {
                    q.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return lista;
    }

    public ArbolGen clone() {
        ArbolGen clone = new ArbolGen();
        if (this.raiz != null) {
            clone.raiz = new NodoGen(this.raiz.getElem(), null, null);
            clonarRecursivo(this.raiz, clone.raiz);
        }
        return clone;
    }

    private void clonarRecursivo(NodoGen n, NodoGen m) {
        if (n != null) {
            if (n.getHijoIzquierdo() != null) {
                m.setHijoIzquierdo(new NodoGen(n.getHijoIzquierdo().getElem(), null, null));
                clonarRecursivo(n.getHijoIzquierdo(), m.getHijoIzquierdo());
                NodoGen nodoAux = n.getHijoIzquierdo().getHermanoDerecho();
                NodoGen nodoAux2 = m.getHijoIzquierdo();
                while (nodoAux != null) {
                    nodoAux2.setHermanoDerecho(new NodoGen(nodoAux.getElem(), null, null));
                    clonarRecursivo(nodoAux, nodoAux2.getHermanoDerecho());
                    nodoAux = nodoAux.getHermanoDerecho();
                    nodoAux2 = nodoAux2.getHermanoDerecho();
                }
            }
        }
    }

    @Override
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen nodo) {
        //este metodo retorna una cadena con todos los elementos del arbol
        String cadena = "";
        if (nodo != null) {
            cadena += nodo.getElem().toString() + "->";
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena += hijo.getElem().toString() + ",";
                hijo = hijo.getHermanoDerecho();
            }
            hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return cadena;
    }
    public int grado(){
       //este metodo retorna el grado del arbol
       int grado=-1;
       //si el arbol no esta vacio llamamos al metodo privado
       if (this.raiz!=null){
           grado= calcularGrado2(this.raiz,0);
       }
       return grado;
    }
    private int calcularGrado(NodoGen nodo,int mayorGrado){
        int grado=0;
        //si el nodo no es hoja
        if(nodo!=null && nodo.getHijoIzquierdo()!=null){
           NodoGen hijo=nodo.getHijoIzquierdo();
           while(hijo!=null){
               grado++;
               //llama recursivamente con cada uno de ellos y obtiene el mayorGrado
               mayorGrado=Math.max(grado, calcularGrado(hijo,mayorGrado));
               //avanza con el hermano
               hijo=hijo.getHermanoDerecho();
           }
        }
        return mayorGrado;
    }
    private int calcularGrado2 (NodoGen nodo,int mayorGrado){
        int grado=0;
        //si el nodo no es hoja
        if (nodo!=null && nodo.getHijoIzquierdo()!=null){
            NodoGen hijo=nodo.getHijoIzquierdo();
            //recorro la lista de hermanos contandolos
            while(hijo!=null){
               grado++;
               hijo=hijo.getHermanoDerecho();
            }
            hijo=nodo.getHijoIzquierdo();
            //llamo recursivamente con cada uno de ellos y voy guardando el mayorGrado hasta el momento
            while (hijo!=null){
                mayorGrado=Math.max(grado, calcularGrado(hijo,mayorGrado));
                hijo=hijo.getHermanoDerecho();
            }
        }
        return mayorGrado;
    }
    public int gradoSubarbol(Object elem){
        //este metodo devuelve el grado de el subarbol que tiene al elemento como raiz
        int grado=-1;
        NodoGen nodo=obtenerNodo(this.raiz,elem);
        //si el elemento esta en el arbol llama al metodo para calcular el grado
        if (nodo!=null){
            grado=calcularGrado(nodo,0);
        }
        return grado;
    }
}
