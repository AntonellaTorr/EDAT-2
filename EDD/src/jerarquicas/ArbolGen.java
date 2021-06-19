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
 * @author QuirogaNicolas FAI-2978, Torres Bianca Antonella FAI-2991
 */
public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        //atributo de la clase
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre) {
        //este metodo inserta un elemento en el arbol 
        boolean exito = false;
        //si el arbol esta vacio crea un nuevo nodo y lo define como raiz
        if (this.raiz == null) {
            this.raiz = new NodoGen(elemNuevo, null, null);
            exito = true;
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

    private NodoGen obtenerNodo(NodoGen nodo, Object elem) {
        //este metodo busca un elemento en el arbol y retorna el nodo que lo contiene
        NodoGen resultado = null;
        if (nodo != null) {
            //si el nodo recibido por parametro tiene al elemento buscado almacenamos ese nodo para luego retornarlo
            if (nodo.getElem().equals(elem)) {
                resultado = nodo;
            } else {
                //sino lo buscamos en sus hijos llamando recursivamente con cada uno de ellos 
                //hasta encontrarlo o hasta no tener mas hermanos
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && resultado == null) {
                    resultado = obtenerNodo(hijo, elem);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Object elem) {
        //este metodo devuelve true si el elemento recibido por parametro se encuentra en el arbol y false si no
        return (obtenerNodo(this.raiz, elem) != null);
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
            } else {
                //sino insertamos en la lista el elemento
                lista.insertar(nodo.getElem(), pos);
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && !encontrado) {
                    encontrado = buscarAncestros(hijo, elem, lista, pos + 1);
                    hijo = hijo.getHermanoDerecho();
                }
                //si nunca se encontro el elemento eliminamos el elemento que habiamos insertado
                if (!encontrado) {
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
           
     
    public int altura() {
        //este metodo retorna la altura del arbol
        return alturaRecursivo(this.raiz);
    }
    


    private int alturaRecursivo(NodoGen n) {
        //este metodo recursivo retorna la mayor altura encontrada en el arbol
        //creamos dos variables de tipo int donde guardaremos las alturas
        //inicializamos en -1 considerando que que se puede recibir un arbol vacio
        int alturaActual = -1, alturaDelHermano = -1;
        if (n != null) {
            //mientras el nodo actual sea diferente de null
            if (n.getHijoIzquierdo() == null) {
                //si llegamos a una hoja asignamos 0 a la altura actual para devolverla
                alturaActual = 0;
            } else {
                //si el nodo hijo no es null entonces llamaremos recursivamente con el y le sumaremos 1 a su retorno
                alturaActual = alturaRecursivo(n.getHijoIzquierdo()) + 1;
                //creamos una variable de tipo NodoGen que nos ayudara a recorrer todos los hermanos del nodo n
                NodoGen nodoAux = n.getHermanoDerecho();
                while (nodoAux != null) {
                    //mientras que el hermano no sea null vamos a invocar recursivamente con el y asignarlo a la variable alturaDelHermano
                    alturaDelHermano = alturaRecursivo(n.getHermanoDerecho());
                    //compararemos ambas alturas obtenidas y nos quedaremos con la mayor
                    alturaActual = Math.max(alturaActual, alturaDelHermano);
                    nodoAux = nodoAux.getHermanoDerecho();
                }
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
        if (this.raiz != null && !this.raiz.getElem().equals(elem)) {
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
            //llama recursivamente con el primer hijo del nodo
            if (nodo.getHijoIzquierdo() != null) {
                listarInordenAux(nodo.getHijoIzquierdo(), lista);
            }
            //visita al nodo
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            //llama recursivamente con los otros hijos de nodo
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
            NodoGen hijo = nodo.getHijoIzquierdo();
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

    @Override
    public ArbolGen clone() {
        //este metodo devuelve una copia exacta de un determinado arbol
        //creamos el nuevo arbol
        ArbolGen clone = new ArbolGen();
        if (this.raiz != null) {
            //si el arbol a copiar no esta vacio vamos a copiar el elemento en su raiz
            clone.raiz = new NodoGen(this.raiz.getElem(), null, null);
            //y procederemos a copiar el resto llamando a un metodo recursivo
            clonarRecursivo(this.raiz, clone.raiz);
        }
        return clone;
    }

    private void clonarRecursivo(NodoGen n, NodoGen m) {
        //este metodo recibe por parametro dos NodoGen e ira copiando para crear el clone
        //siendo en un comienzo n la raiz del arbol a copiar y m la raiz de la copia
        if (n != null) {
            //si el nodo n es diferente de null
            if (n.getHijoIzquierdo() != null) {
                //si el hijo izquierdo es distinto de null
                //seteamos el hijo izuierdo de m
                m.setHijoIzquierdo(new NodoGen(n.getHijoIzquierdo().getElem(), null, null));
                //llamaremos recursivamente con el hijo izquierdo de n y el hijo izquierdo recien seteado de m
                clonarRecursivo(n.getHijoIzquierdo(), m.getHijoIzquierdo());
                //creamos dos variables de tipo NodoGen que nos ayudaran a recorrer los hermanos del hijo de n y setear los hermanos del hijo de m
                NodoGen nodoAux = n.getHijoIzquierdo().getHermanoDerecho();
                NodoGen nodoAux2 = m.getHijoIzquierdo();
                while (nodoAux != null) {
                    //mientras que nodoAux no sea null
                    //vamos a agregarle un hermano al hijo de m
                    nodoAux2.setHermanoDerecho(new NodoGen(nodoAux.getElem(), null, null));
                    //llamaremos recursivamente
                    clonarRecursivo(nodoAux, nodoAux2.getHermanoDerecho());
                    //y finalmente actualizaremos las variables nodoAux y nodoAux2
                    nodoAux = nodoAux.getHermanoDerecho();
                    nodoAux2 = nodoAux2.getHermanoDerecho();
                }
            }
        }
    }

    @Override
    public String toString() {
        //este metodo devuelve una cadena con todos los elementos del arbol
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen nodo) {
        //este metodo retorna una cadena con todos los elementos del arbol
        String cadena = "";
        if (nodo != null) {
            //visitamos al nodo
            cadena += nodo.getElem().toString() + "->";
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena += hijo.getElem().toString() + ",";
                hijo = hijo.getHermanoDerecho();
            }
            //llamadas recursivas para cada hijo
            hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                cadena += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return cadena;
    }

    public int grado() {
        //este metodo retorna el grado del arbol
        int grado = -1;
        //si el arbol no esta vacio llamamos al metodo privado
        if (this.raiz != null) {
            grado = calcularGrado(this.raiz, 0);
        }
        return grado;
    }

    private int calcularGrado(NodoGen nodo, int mayorGrado) {
        int grado = 0;
        //si el nodo no es hoja
        if (nodo != null && nodo.getHijoIzquierdo() != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                grado++;
                //llama recursivamente con cada uno de ellos y obtiene el mayorGrado
                mayorGrado = Math.max(grado, calcularGrado(hijo, mayorGrado));
                //avanza con el hermano
                hijo = hijo.getHermanoDerecho();
            }
        }
        return mayorGrado;
    }

    public int gradoSubarbol(Object elem) {
        //este metodo devuelve el grado de el subarbol que tiene al elemento como raiz
        int grado = -1;
        NodoGen nodo = obtenerNodo(this.raiz, elem);
        //si el elemento esta en el arbol llama al metodo para calcular el grado
        if (nodo != null) {
            grado = calcularGrado(nodo, 0);
        }
        return grado;
    }
      
    
     public boolean verificarCamino(Lista lista){
        return verificarAux(lista, this.raiz, 1,lista.longitud());   
    }
   
    private boolean verificarAux(Lista lista, NodoGen nodo, int pos, int max){
        boolean exito=false;
        while (!exito && nodo!=null){
            //si el elemento concide
            if (nodo.getElem().equals(lista.recuperar(pos))){
                //si esta en la ultima posicion de la lista y todo coincide hasta el momento debe salir y retorna true
                if (pos==max){
                    exito=true;
                }else{
                    //sino sigue verificando
                    exito=verificarAux(lista, nodo.getHijoIzquierdo(),pos+1,max);
                    if(!exito){
                        exito=verificarAux(lista,nodo.getHermanoDerecho(),pos,max);
                    }
                }
            }
            else{
                
                //sino busca entre los hermanos
                nodo=nodo.getHermanoDerecho();
                
            }
        }
        return exito;
    }
    public boolean verificarCamino2(Lista lista){
        return verificarAux2(lista, this.raiz, 1,lista.longitud());   
    }
     private boolean verificarAux2(Lista lista, NodoGen nodo, int pos, int max){
        boolean exito=false;
        while (!exito && nodo!=null){
            //si el elemento concide
            if (nodo.getElem().equals(lista.recuperar(pos))){
                //si esta en la ultima posicion de la lista y todo coincide hasta el momento debe salir y retorna true
                if (pos==max && nodo.getHijoIzquierdo()==null){
                    exito=true;
                }else{
                    //sino sigue verificando
                    exito=verificarAux(lista, nodo.getHijoIzquierdo(),pos+1,max);
                }
            }
            else{
                
                //sino busca entre los hermanos
                nodo=nodo.getHermanoDerecho();
                
            }
        }
        return exito;
    }
    public Lista listarEntreNiveles (int niv1, int niv2){
        Lista lista= new Lista();
        return listarAux(this.raiz, lista, niv1,niv2,0);
    }
     private Lista listarAux(NodoGen nodo, Lista lista, int niv1, int niv2, int nivAct) {
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() != null&& nivAct>=niv1 && nivAct<niv2) {
                listarAux(nodo.getHijoIzquierdo(), lista,niv1,niv2,nivAct+1);
            }
            if (nivAct>=niv1 && nivAct<=niv2){
                lista.insertar(nodo.getElem(), lista.longitud() + 1);
            }
            
            if (nodo.getHijoIzquierdo() != null && nivAct<niv2) {
                NodoGen hijo = nodo.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarAux(hijo, lista,niv1,niv2,nivAct+1);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return lista;
    }
    public Lista listarHastaNivel (int nivMax) {
        Lista lista= new Lista();
        return listarNiv(this.raiz, lista, nivMax,-1,1);
    } 
    private Lista listarNiv(NodoGen nodo, Lista lista,int nivMax, int nivAct, int pos){
        if (nodo != null && nivAct>=0) {
            //visitamos la raiz 
            if(nivAct<=nivMax){
                lista.insertar(nodo.getElem(), pos);
            }
            if (nivAct<nivMax){
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null) {
                listarNiv(hijo, lista, nivMax, nivAct+1,pos+1);
                hijo = hijo.getHermanoDerecho();
                }     
            }
           

       
        }
        return lista;
    }
    public boolean insertarEnPos(Object elem, Object padre, int pos) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoGen(elem, null, null);
            exito = true;
        } else {
            NodoGen nodo = obtenerNodo(this.raiz, padre);
            if (pos == 1) {
                nodo.setHijoIzquierdo(new NodoGen(elem, null, nodo.getHijoIzquierdo()));
                exito = true;
            } else {
                NodoGen hijo = nodo.getHijoIzquierdo();
                int posAct = 1;
                while (hijo != null && posAct < pos - 1) {
                    hijo = hijo.getHermanoDerecho();
                    posAct++;
                }
                if (hijo != null) {
                    //creo el enlace al nuevo elemento, el nuevo elemento se engancha con el otro hermano en la lista
                    hijo.setHermanoDerecho(new NodoGen(elem, null, hijo.getHermanoDerecho()));
                    exito = true;
                }

            }
        }
        return exito;
    }
}
