/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;

/**
 *
 * @author Quiroga Nicolas, Torres Bianca Antonella
 */
public class Lista {

    Nodo cabecera;

    public Lista() {
        //atributo de la clase
        cabecera = null;

    }

    public boolean insertar(Object elem, int pos) {
        //este metodo inserta un objeto en una posicion dada de la lista
        boolean exito = false;
        int longitud = this.longitud();
        //caso posible
        if (pos >= 1 && pos <= longitud + 1) {
            //caso especial cuando hay que insertar al comienzo de la lista
            if (pos == 1) {
                //creo el nodo nuevo y hago que la cabecera lo apunte
                this.cabecera = new Nodo(elem, this.cabecera);;
            } else {
                /*creo una variable auxiliar llamada aux que va a recorrer la lista hasta llegar una posicion antes a la
               recibida por parametro*/
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    //aux esta una posicion antes del lugar donde queremos insertar el nodo
                    aux = aux.getEnlace();
                    i++;
                }
                //creamos el nuevo nodo y hacemos que apunte al siguiente nodo si existe y sino a null
                Nodo nuevoNodo = new Nodo(elem, aux.getEnlace());
                //al nodo anterior al nuevo le seteamos el enlace para que apunte al nodo nuevo
                aux.setEnlace(nuevoNodo);
            }
            exito = true;

        }
        return exito;
    }

    public boolean eliminar(int pos) {
        //este metodo elimina un elemento en la posicion ingresada por parametro
        boolean exito = false;
        int i;
        //si la posicion es valida procedemos
        if (pos >= 1 && pos <= this.longitud()) {
            /*caso especial el primer elemento, hago que el nodo que era apuntado por la cabecera
            ahora no lo apunte nadie asi el garbage collector se lo lleva, y la cabecera ahora apunta
            al segundo nodo*/
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                /*creo una variable auxiliar llamada aux que va a recorrer la lista hasta llegar una posicion antes a la
               recibida por parametro*/
                Nodo aux = this.cabecera;
                i = 2;
                while (i < pos) {
                    aux = aux.getEnlace();
                    i++;
                }
                //para eliminar elemento hago que no sea apuntado por nadie asi el garbage collector se lo lleva
                aux.setEnlace(aux.getEnlace().getEnlace());

            }
            exito = true;
        }
        return exito;
    }

    public Object recuperar(int pos) {
        //este metodo recupera un objeto en la posicion recibida por parametro
        int i = 2;
        Object elem;
        //el nodo aux apunta a la cabecera
        if (pos < 1 || pos > this.longitud()) {
            elem = null;
        } else {
            Nodo aux = this.cabecera;
            if (pos == 1) {
                elem = this.cabecera.getElem();
            } else {
                //llego hasta la posicion de la cual quiero recuperar el elemento
                while (i <= pos) {
                    //avanzo en los nodos
                    aux = aux.getEnlace();
                    i++;
                }
                //una vez que llegue a la posicion recupero el elemento
                elem = aux.getElem();
            }
        }

        return elem;

    }

    public int localizar(Object elem) {
        //este metodo localiza un elemento en la lista, si lo encuentra devuelve la posicion en la que lo encontro sino -1
        int encontrado = -1;
        //En el peor de los casos recorre toda la lista y no la encuentra
        //avanzo mientras que no encontre el elemento y la lista no sea vacia
        if (this.cabecera != null) {
            int i = 1;
            Nodo aux = cabecera;
            while (encontrado == -1 && aux != null) {
                //si lo encuentra devuelve la posicion donde lo encontro
                if (aux.getElem().equals(elem)) {
                    encontrado = i;
                } //si no lo encuentra continua buscando
                else {
                    aux = aux.getEnlace();
                    i++;
                }

            }
        }

        return encontrado;
    }

    public void vaciar() {
        //este metodo vacia toda la lista 
        this.cabecera = null;
    }

    public boolean esVacia() {
        //este metodo devuelve true si la lista esta vacia false si no lo esta
        return (this.cabecera == null);
    }

    public Lista clone() {
        //este metodo de vuelve una copia de la lista orignal
        Lista clone = new Lista();
        //si la lista no esta vacia procedo a copiar elemento por elemento sino retorno la lista vacia
        if (!this.esVacia()) {
            //aux1 es la variable auxiliar que se va a ir moviendo en la lista original
            //aux2 es la variable que se va a ir moviendo en la lista clone
            //creo el primer elemento
            //hago que la cabecera lo apunte
            clone.cabecera = new Nodo(this.cabecera.getElem(), null);
            Nodo aux2 = clone.cabecera;
            Nodo aux1 = this.cabecera;
            //avanzo al 2 elemento
            aux1 = aux1.getEnlace();
            //mientras que la lista tenga elementos 
            while (aux1 != null) {
                //creo nuevos nodos y hago que estos sean apuntados por aux?
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                //avanzo en lista original y auxiliar
                aux2 = aux2.getEnlace();
                aux1 = aux1.getEnlace();
            }
        }
        return clone;
    }

    public int longitud() {
        //este metodo devuelve la longitud de la lista
        int i = 0;
        //nodo auxiliar apuntando a la cabecera
        Nodo aux = this.cabecera;
        //mientras que hayan elementos en la lista voy a pasar por cada uno de ellos contandolos
        while (aux != null) {
            aux = aux.getEnlace();
            i++;

        }
        return i;
    }

    public String toString() {
        //creamos una variable de tipo String con el texto predeterminado "lista vacia"
        String cadena = "lista vacia";
        if (!this.esVacia()) {
            //si la pila no esta vacia entonces se borrara el texto predeterminado para que se copien los elementos
            cadena = "[";
            Nodo aux = this.cabecera;
            while (aux != null) {
                //si el proximo elemento por agregar no es nulo entonces lo agregamos a la variable de tipo String 
                cadena += aux.getElem();
                //actualizamos la variable auxiliar puntero
                aux = aux.getEnlace();
                if (aux != null) {
                    cadena += ",";
                }
            }
            cadena += "]";
        }
        return cadena;
    }


}
    
    
    

