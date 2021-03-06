/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;
import lineales.dinamicas.Lista;
import funciones.Funciones;


public class TablaHash {
    private static final int TAMANIO = 100;
    private Nodo [] hash;
    private int cant;
    
    public TablaHash (){
        this.hash = new Nodo[this.TAMANIO];
        cant=0;
    }
    
     public boolean insertar(Object elem){
        int pos= elem.hashCode();
        Nodo aux=this.hash[pos];
        boolean encontrado=false;
        while (!encontrado && aux!=null){
            encontrado=aux.getElem().equals(elem);
            aux=aux.getEnlace();
        }
        if (!encontrado){
            this.hash[pos]= new Nodo(elem,this.hash[pos]);
            this.cant++;
        }
        return !encontrado;
    }
   
  
     public boolean pertenece (Object elem){
        //busca la posicion en la que se podria encontrar el elemento
        int pos= elem.hashCode();
        Nodo aux=this.hash[pos];
        boolean encontrado=false;
        while (!encontrado && aux!=null){
            encontrado=aux.getElem().equals(elem);
            aux=aux.getEnlace();
        }
        //si lo encuentra va a retornar true y sino false
        return encontrado;
    }
  
    public boolean eliminar (Object elem){
        //busca la posicion del elemento 
        int pos= elem.hashCode();
        Nodo aux=this.hash[pos];
        boolean encontrado=false;
        //si el elemento se encuentra en la primer posicion
        if (aux!=null && aux.getElem().equals(elem)){
            this.hash[pos]=this.hash[pos].getEnlace();
        }
        else{
            while (!encontrado && aux!=null){
            //verifica si el elemento siguiente tiene al elemento buscado
            encontrado=aux.getEnlace().getElem().equals(elem);
                if (encontrado){
                    //si lo tenia borra dicho enlace 
                    aux.setEnlace(aux.getEnlace().getEnlace());
                    //disminuye la cantidad de elementos en la table
                    this.cant--;
                }
                else{
                    //sino no lo encontro sigue avanzando 
                    aux=aux.getEnlace();
                }
            }
        }
        return encontrado;
        
    }
    public boolean esVacia(){
        return (cant==0);
    }
    
    public Lista listar (){
        int pos=0, pos2=1;
        Lista lista= new Lista ();
        //recorre la tabla hash0
        while(pos<TAMANIO){
            //recorre la lista de cada pos
            Nodo aux=this.hash[pos];
            while(aux!=null){
                lista.insertar(aux.getElem(), pos2);
                pos2++;
                aux=aux.getEnlace();
            }
            pos++;
        }
        return lista;
        
    }
    public TablaHash clone(){
        TablaHash clone= new TablaHash ();
        int pos=0;
        while(pos<this.TAMANIO){
            clone.hash[pos]= new Nodo (this.hash[pos].getElem(), null);
            Nodo aux=this.hash[pos];
            while(aux!=null){
                clone.hash[pos].setEnlace(new Nodo(this.hash[pos].getEnlace().getElem(), clone.hash[pos]));
                aux=aux.getEnlace();
            }
            pos++;
        }
        return clone;
        
    }
    
    
}
