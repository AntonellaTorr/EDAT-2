package jerarquicas;

import jerarquicas.NodoArbol;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

/**
 *
 * @author Quiroga Nicolas, Torres Bianca Antonella
 */
public class ArbolBin {

    //atributo de la clase
    NodoArbol raiz;

    public ArbolBin() {
        //este metodo crea un arbol vacio
        //creamos un arbol inicializando su raiz en null 
        this.raiz = null;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        //este metodo privado busca un elemento y devuelve el nodo que lo contiene
        //creamos e inicializamos la variable resultado en null
        NodoArbol resultado = null;
        if (n != null) {
            //si el nodo actual es diferente de null procedemos, sino devolvemos null
            if (n.getElem().equals(buscado)) {
                //si el elemento del nodo actual es igual al elemento que estamos buscando 
                //se lo asignamos a nuestra variable resultado que funcionara como un puntero
                resultado = n;
            } else {
                //si el nodo actual no es lo que buscamos 
                //procedemos a buscar en el lado izquierdo de nuestro arbol/subArbol
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                if (resultado == null) {
                    //si el nodo no fue encontrado en el lado izquierdo
                    //procedemos a buscar en el lado derecho del arbol/subArbol
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean insertar(Object nuevoElem, Object elemPadre, char lugar) {
        //este metodo tiene como objetivo insertar un elemento en nuestro arbol
        //creamos e inicializamos una variable boolean en false
        boolean exito = true;
        if (this.raiz == null) {
            //si nuestro arbol esta vacio
            //el nuevo elemento lo insertaremos en la raiz
            this.raiz = new NodoArbol(nuevoElem, null, null);
        } else {
            //si el arbol no esta vacio 
            //buscaremos el nodo padre
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                //si el padre existe y no tiene un hijo en el lugar donde queremos poner nuestro elemento  
                //entonces procedemos a poner el hijo en su lugar, sea derecho o izquierdo
                if (lugar == 'I' && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new NodoArbol(nuevoElem, null, null));
                } else {
                    if (lugar == 'D' && nodoPadre.getDerecho() == null) {
                        nodoPadre.setDerecho(new NodoArbol(nuevoElem, null, null));
                    } else {
                        //en caso de no poder hacerlo le asignamos false a nuestra variable booleana
                        exito = false;
                    }
                }
            } else {
                //tambien consideramos el caso en el que el nodo padre no existe
                exito = false;
            }
        }
        return exito;
    }

    public boolean esVacio() {
        //este metodo devuelve un valor booleano indicando si el arbol esta vacio 
        return (this.raiz == null);
    }

    public void vaciar() {
        //este metodo vacia el arbol
        //esto lo logramos con solo setear la raiz en null
        this.raiz = null;
    }

    public int nivel(Object elem) {
        //este metodo retorna el nivel donde se encuentra un determinado elemento en el arbol
        //llamamos a un metodo privado reucursivo
        return encontrarNivel(this.raiz, elem, -1);
    }

    private int encontrarNivel(NodoArbol nodo, Object elem, int nivel) {
        //este metodo privado y recursivo recibe por parametro un nodo a partir del cual empezara a buscar, el elemento que buscara
        //y el nivel en el que se encuentra
        //creamos e inicializamos en -1 la variable encontrado
        int encontrado = -1;
        if (nodo != null) {
            //siempre y cuando el nodo actual no es igual a null
            if (nodo.getElem().equals(elem)) {
                //si lo encuentra retorna el nivel donde fue encontrado
                encontrado = nivel + 1;
            } else {
                //si el contenido del nodo actual no es igual al contenido buscando
                //entonces buscamos en el lado izquierdo del arbol
                encontrado = encontrarNivel(nodo.getIzquierdo(), elem, nivel + 1);
                if (encontrado == -1) {
                    //si el elemento todavia no fue encontrado 
                    //buscamos en el lado derecho 
                    encontrado = encontrarNivel(nodo.getDerecho(), elem, nivel + 1);
                }
            }
        }

        return encontrado;
    }

    public int altura() {
        //este metodo retorna la altura del arbol 
        //invoca a un metodo privado recursivo
        return medirAltura(this.raiz);
    }

    private int medirAltura(NodoArbol n) {
        //metodo privado que mide la altura de un arbol 
        //recibe el un nodo por parametro
        //creamos e inicializamos dos variables longitud en -1
        int longitud = -1, longitud2 = -1;
        if (n != null) {
            //siempre y cuando el nodo actual sea diferente de null
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                //si el nodo en el cual nos encontramos no tiene hijos 
                //significa que estamos situados en una hoja
                //asignamos 0 a la variable longitud
                longitud = 0;
            } else {
                //si no nos encontramos en una hoja le sumamos 1 al resultado que sera devuelto al invocar recursivamente 
                longitud = medirAltura(n.getIzquierdo()) + 1;
                longitud2 = medirAltura(n.getDerecho()) + 1;

            }
        }
        //finalmente retornamos la mayor longitud encontrada
        //puesto que puede haber un lado del arbol mas alto que el otro
        return Math.max(longitud, longitud2);
    }

    public Object padre(Object elem) {
        //este metodo recibe un elemento, busca el nodo que lo contenga y devuelve el contenido de su nodo padre 
        return obtenerElemPadre(this.raiz, elem);
    }

    private Object obtenerElemPadre(NodoArbol n, Object x) {
        //este metodo privado y recursivo busca un elemento dado para, una vez encontrado, devolver el elemento que contiene su padre
        Object elemento = null;
        if (n != null && !this.raiz.getElem().equals(x)) {
            //siempre y cuando el nodo actual sea distinto de null 
            //tambien consideramos el caso en el que se calcula el padre de la raiz(lo cual deberia devolver null)
            if ((n.getIzquierdo() != null && n.getIzquierdo().getElem().equals(x)) || (n.getDerecho() != null && n.getDerecho().getElem().equals(x))) {
                //si el hijo izquierdo o derecho contiene al elemento que buscamos
                //entonces vamos a devolver el elemento en el que nos encontramos actualmente
                elemento = n.getElem();
            } else {
                //si no se cumple, llamamos recursivamente para buscar en el lado izquierdo de nuestro arbol/subArbol
                elemento = obtenerElemPadre(n.getIzquierdo(), x);
                if (elemento == null) {
                    //si un vez realizado este proceso todavia no lo encontramos 
                    //buscamos en el lado derecho del arbol/subArbol
                    elemento = obtenerElemPadre(n.getDerecho(), x);
                }
            }
        }
        return elemento;
    }

    public Lista listarPreorden() {
        //este metodo devuelve una lista con los elementos de nuestro arbol listados en preOrden
        //creamos la lista que vamos a usar
        Lista lista = new Lista();
        //invocamos al metodo recursivoPreorden
        recursivoPreorden(this.raiz, lista, 1);
        return lista;
    }

    public int recursivoPreorden(NodoArbol nodo, Lista lista, int pos) {
        //este metodo privado y recursivo devuelve la posicion en la cual debemos insertar un elemento
        if (nodo != null) {
            //siempre que el nodo recibido sea distinto de null
            //vamos a insertar el elemento de dicho nodo en la posicion recibida 
            lista.insertar(nodo.getElem(), pos);
            if (nodo.getIzquierdo() != null) {
                //si el hijo izquierdo no es null
                //lo vamos a agregar llamando recursivamente
                pos = recursivoPreorden(nodo.getIzquierdo(), lista, pos + 1);
            }
            if (nodo.getDerecho() != null) {
                //si el hijo derecho no es null
                //lo vamos a agregar llamando recursivamente
                pos = recursivoPreorden(nodo.getDerecho(), lista, pos + 1);
            }
        }
        return pos;
    }

    public Lista listarInorden() {
        //este metodo devuelve una lista con los elementos de nuestro arbol listados en inOrden
        //creamos la lista que vamos a usar
        Lista lista = new Lista();
        //invocamos al metodo recursivoInorden
        recursivoInorden(this.raiz, lista, 1);
        return lista;
    }

    public int recursivoInorden(NodoArbol nodo, Lista lista, int pos) {
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
                pos = recursivoInorden(nodo.getIzquierdo(), lista, pos);
                //insertamos el nodo actual, que viene a ser el nodo padre
                lista.insertar(nodo.getElem(), pos);
                pos++;
                //incrementamos pos y repetimos el proceso con el lado derecho
                pos = recursivoInorden(nodo.getDerecho(), lista, pos);
            }

        }
        return pos;
    }

    public Lista listarPosorden() {
        //este metodo devuelve una lista con los elementos de nuestro arbol listados en posOrden
        //creamos la lista que vamos a usar
        Lista lista = new Lista();
        //invocamos al metodo recursivoPosorden
        recursivoPosorden(this.raiz, lista, 1);
        return lista;
    }

    public int recursivoPosorden(NodoArbol nodo, Lista lista, int pos) {
        //este metodo privado y recursivo devuelve la posicion en la cual debemos insertar un elemento
        //la variable pos indica en que posicion de la lista debemos insertar el elemento
        if (nodo != null) {
            //siempre que el nodo recibido sea distinto de null
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                //si llegamos a una hoja
                //vamos a insertarlo e incrementar pos
                lista.insertar(nodo.getElem(), pos);
                pos++;
            } else {
                //si no estamos en una hoja seguimos recorriendo de manera recursiva
                pos = recursivoPosorden(nodo.getIzquierdo(), lista, pos);
                pos = recursivoPosorden(nodo.getDerecho(), lista, pos);
                //una vez agregados todos los hijos 
                //vamos a insertar el padre e incrementar pos
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
            //si la raiz del arbol es diferente de null
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
                if (nodoActual.getIzquierdo() != null) {
                    //si tiene hijo izquierdo lo pongo en la cola
                    Q.poner(nodoActual.getIzquierdo());
                }
                if (nodoActual.getDerecho() != null) {
                    //si tiene hijo derecho lo pongo en la cola 
                    Q.poner(nodoActual.getDerecho());
                }
                i++;

            }

        }
        return lista;
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

    public ArbolBin clone() {
        //este metodo devuelve un arbol identico al arbol original 
        //creamos e inicializamos el arbol clone
        ArbolBin clone = new ArbolBin();
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

    public Lista frontera() {
        //este metodo devuelve una lista que contiene a todas las hojas del arbol 
        //creamos la lista donde almacenaremos los elementos 
        Lista listado = new Lista();
        //invocamos al metodo listarFrontera
        listarFrontera(this.raiz, listado, 1);
        return listado;
    }

    private int listarFrontera(NodoArbol n, Lista listado, int pos) {
        //este metodo recursivo devolvera la lista original con todos los elementos de las hojas
        if (n != null) {
            //siempre que el nodo actual sea distinto de null
            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                //si nos encontramos situados en una hoja 
                //vamos a insertarlo en la lista
                listado.insertar(n.getElem(), pos);
                pos++;
            } else {
                //si no estamos en una hoja
                if (n.getIzquierdo() != null) {
                    //y el hijo izquierdo es distinto de null
                    //vamos a invocar recursivamente con dicho nodo
                    pos = listarFrontera(n.getIzquierdo(), listado, pos);
                }
                if (n.getDerecho() != null) {
                    //si el hijo derecho es distinto de null
                    //vamos a invocar recursivamente con dicho nodo
                    pos = listarFrontera(n.getDerecho(), listado, pos);
                }
            }
        }
        return pos;
    }
    public void alterarParte(Object hi, Object hd, Object pp){
        
    }
    private boolean alterar(NodoArbol nodo, Object hi, Object hd, Object pp){
        boolean encontrado=false;
        if (nodo!=null){
            if (nodo.getElem().equals(pp)){
                encontrado=true;
                if (nodo.getIzquierdo()!=null){
                    nodo.getIzquierdo().setElem(hi);
                }
                else{
                    nodo.setIzquierdo(new NodoArbol (hi, null,null));
                }
                if (nodo.getDerecho()!=null){
                    nodo.getDerecho().setElem(hd);
                }
                else{
                    nodo.setDerecho(new NodoArbol (hd, null,null));
                }
                
            }
            encontrado=alterar(nodo.getIzquierdo(),hi, hd, pp);
            if (!encontrado){
                encontrado=alterar(nodo.getDerecho(), hi,hd, pp);
            }
        }
        return encontrado;
    }
}
