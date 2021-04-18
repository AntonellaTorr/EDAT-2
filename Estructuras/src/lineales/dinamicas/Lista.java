/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;

/**
 *
 * @author Anto
 */
public class Lista {
    Nodo cabecera;
    
    public Lista (){
        //atributo de la clase
        cabecera=null;

    }
    public boolean insertar(Object elem, int pos){
        //este metodo inserta un objeto en una posicion dada de la lista
        boolean exito=false;
        int longitud=this.longitud ();
        //caso posible
        if (pos>=1 && pos<=longitud+1){
            //caso especial cuando hay que insertar al comienzo de la lista
            if(pos==1){
                //creo el nodo nuevo y hago que la cabecera lo apunte
                Nodo nuevo= new Nodo(elem,this.cabecera);
                this.cabecera=nuevo;
            }
            else{
               /*creo una variable auxiliar llamada aux que va a recorrer la lista hasta llegar una posicion antes a la
               recibida por parametro*/
               Nodo aux=cabecera;
               int i=1;
               while (i<pos-1){
                    //aux esta una posicion antes del lugar donde queremos insertar el nodo
                    aux=aux.getEnlace();
                    i++;
                }
                //creamos el nuevo nodo y hacemos que apunte al siguiente nodo si existe y sino a null
               Nodo nuevoNodo= new Nodo (elem, aux.getEnlace());
               //al nodo anterior al nuevo le seteamos el enlace para que apunte al nodo nuevo
               aux.setEnlace(nuevoNodo);
            } 
        exito= true;  

        }
        return exito;
    }
    public boolean eliminar (int pos){
        //este metodo elimina un elemento en la posicion ingresada por parametro
        boolean exito=false;
        int i, longitud= this.longitud();
        //si la posicion es valida procedemos
        if (pos>=1 && pos<=longitud ){
            /*caso especial el primer elemento, hago que el nodo que era apuntado por la cabecera
            ahora no lo apunte nadie asi el garbage collector se lo lleva, y la cabecera ahora apunta
            al segundo nodo*/
            if (pos==1){
                cabecera=cabecera.getEnlace();
            }
            else{
               /*creo una variable auxiliar llamada aux que va a recorrer la lista hasta llegar una posicion antes a la
               recibida por parametro*/
                Nodo aux=cabecera;
                i=2;
                while (i<pos){
                  aux=aux.getEnlace();
                  i++;
                }
                //para eliminar elemento hago que no sea apuntado por nadie asi el garbage collector se lo lleva
                aux.setEnlace(aux.getEnlace().getEnlace());
                
            }
            exito=true;
        }
        return exito;    
    }
    public Object recuperar (int pos){
        //precondicion pos es valida
        //este metodo recupera un objeto en la posicion recibida por parametro
        int i=2;
        Object elem;
        //el nodo aux apunta a la cabecera
        Nodo aux=this.cabecera;
        if (pos==1){
            elem=this.cabecera.getElem();
        }
        else{
            //llego hasta la posicion de la cual quiero recuperar el elemento
            while (i<=pos){
                //avanzo en los nodos
                aux=aux.getEnlace();
                i++;
            }
            //una vez que llegue a la posicion recupero el elemento
            elem=aux.getElem();
          
        }
        return elem;
       
    }
    public int localizar (Object elem){
        //este metodo localiza un elemento en la lista, si lo encuentra devuelve la posicion en la que lo encontro sino -1
        int encontrado=-1,i=1;
        Nodo aux=cabecera;
        //En el peor de los casos recorre toda la lista y no la encuentra
        //avanzo mientras que no encontre el elemento y la lista no sea vacia
        while (encontrado==-1 && aux.getElem()!=null){
            if(aux.getElem().equals(elem)){
                encontrado=i;
            }
            else{
                aux=aux.getEnlace();
                i++;
            }
            
        }
        return encontrado;
    }
    
    public void vaciar (){
        //este metodo vacia toda la lista 
        this.cabecera=null;
    }
    public boolean esVacia (){
        //este metodo devuelve true si la lista esta vacia false si no lo esta
        return (this.cabecera==null);
    }
    public Lista clone (){
        //este metodo de vuelve una copia de la lista orignal
        Lista clone = new Lista ();
        //si la lista no esta vacia procedo a copiar elemento por elemento sino retorno la lista vacia
        if (!this.esVacia()){
            //aux1 es la variable auxiliar que se va a ir moviendo en la lista original
            Nodo aux1=this.cabecera;
            //aux2 es la variable que se va a ir moviendo en la lista clone
            //creo el primer elemento
            Nodo aux2= new Nodo(aux1.getElem(),null);
            //hago que la cabecera lo apunte
            clone.cabecera=aux2;
            //avanzo al 2 elemento
            aux1=aux1.getEnlace();
            //mientras que la lista tenga elementos 
            while (aux1!= null){
                //creo nuevos nodos y hago que estos sean apuntados por aux?
                aux2.setEnlace(new Nodo (aux1.getElem(),null));
                //avanzo en lista original y auxiliar
                aux2=aux2.getEnlace();
                aux1=aux1.getEnlace();
            }
        }
        return clone;
    }
    public int longitud (){
        //este metodo devuelve la longitud de la lista
        int i=0;
        //nodo auxiliar apuntando a la cabecera
        Nodo aux=this.cabecera;
        //mientras que hayan elementos en la lista voy a pasar por cada uno de ellos contandolos
        while (aux!=null){
            aux=aux.getEnlace();
            i++;
        
        }
        return i;
    }
    @Override
    public String toString (){
        //metodo a fines de debuggin que devuelve una cadena con todos los elementos de los nodos
        String cadena="";
        if (!this.esVacia()){
            cadena+="[";
            Nodo aux=cabecera;
            cadena+=aux.getElem()+",";
            aux=aux.getEnlace();
            while (aux!=null){
                cadena+=aux.getElem();
                aux=aux.getEnlace();
                if (aux!=null){
                    cadena+=",";
                }
            }
            cadena+="]";
        }
        else{
            cadena="La lista esta vacia";
        }
        return cadena;
       
        
    }
    public  void eliminarAparaciones (Object elem){
        int i=1;
        //elimino las apariciones en el comienzo de la lista
        while (this.cabecera!=null && this.cabecera.getElem().equals(elem)){
            this.cabecera=this.cabecera.getEnlace();
        }
        
        Nodo aux=this.cabecera;
        
        while (aux!=null && aux.getEnlace()!=null){
            if (aux.getEnlace().getElem().equals(elem)){
               aux.setEnlace(aux.getEnlace().getEnlace());
            }
            //avanzo cuando encuentro un elemento que no debo eliminar
            else{
                aux=aux.getEnlace();
            }
           
        }
    }
    public void invertir (){
      Nodo fin=invertirLista(this.cabecera);
      fin.setEnlace(null);
    }

    private Nodo invertirLista (Nodo aux){
       if (aux.getEnlace()==null){
           //si llego al ultimo nodo cambio la cabecera
          this.cabecera=aux;
       }
       else{
           Nodo aux3=invertirLista(aux.getEnlace());
           aux3.setEnlace(aux);
       }
       return aux;
    }
    //1 2 3
    //aux-> 1
    //aux3= invertir 2 (3)(2)
    //2 linea
    //aux3=invertir 3
    //retorna 3
    
}
    
    
    
    

