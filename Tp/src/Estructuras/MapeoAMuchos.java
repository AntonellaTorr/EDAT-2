/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Anto
 */
public class MapeoAMuchos {
     private static final int TAMANIO = 100;
    private NodoHashMapeo[] hash;
    private int cant;

    public MapeoAMuchos () {
        this.hash = new NodoHashMapeo[this.TAMANIO];
        cant = 0;
    }
    public boolean insertar (Object dominio, Object dato){
        int pos = Math.abs(dominio.hashCode()%TAMANIO),res;
        NodoHashMapeo aux = this.hash[pos];
        boolean exito = false;
        while (!exito && aux != null) {
            exito = aux.getDominio().equals(dominio);
            if (!exito){
                 aux = aux.getEnlace();
            }  
        }
        if (exito){
            res=aux.getRango().localizar(dato);
            //si no estaba el dato en la lista de rango
            if (res<0){
              aux.añadirElementoRango(dato);
              exito=true;
              this.cant++;
            }
            else{
                exito=false;
            }
        }
        else{
            this.hash[pos] = new NodoHashMapeo(dominio, this.hash[pos]);
            this.hash[pos].añadirElementoRango(dato);
            this.cant++;
            exito=true;
        }
        return exito;
    }
    
    public boolean desasociar(Object dominio,Object rango){
        int pos = Math.abs(dominio.hashCode()%TAMANIO);
        NodoHashMapeo aux=this.hash[pos];
        boolean exito=false,encontrado=false;
        //busca el nodo que contiene al dominio
        while (!encontrado && aux!=null){
            encontrado=aux.getDominio().equals(dominio);
            if (!encontrado){
                aux=aux.getEnlace();
            }
        }
        //si se encuentra busca el rango en la lista
        if (encontrado){
            int res=aux.getRango().localizar(rango);
            //si se encuentra el rango en la lista se elimina
            if (res>0){
                aux.getRango().eliminar(res);
                //si luego de eliminar el rango la lista queda vacia tmbn se elimina el dominio
                if (aux.getRango().esVacia()){
                   eliminar(aux.getDominio());
                }
                exito=true;
            }
            
        }    
        return exito;
        
    }
     private boolean eliminar (Object dominio){
        //busca la posicion del elemento 
        int pos = Math.abs(dominio.hashCode()%TAMANIO);
        NodoHashMapeo aux=this.hash[pos];
        boolean encontrado=false;
        //si el elemento se encuentra en la primer posicion
        if (aux!=null && aux.getDominio().equals(dominio)){
            this.hash[pos]=this.hash[pos].getEnlace();
        }
        else{
            while (!encontrado && aux!=null){
            //verifica si el elemento siguiente tiene al elemento buscado
            encontrado=aux.getEnlace().getDominio().equals(dominio);
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

   public Lista obtenerValores(Object dominio){
       //busca la posicion de el elemento en la tabla 
        int pos = Math.abs(dominio.hashCode()%TAMANIO);
        NodoHashMapeo aux=this.hash[pos];
        boolean encontrado=false;
        Lista valores= new Lista ();
        //busca el nodo que contiene al dominio
        while (!encontrado && aux!=null){
            encontrado=aux.getDominio().equals(dominio);
            if (!encontrado){
                aux=aux.getEnlace();
            }
            
            
        }
        //si se encuentra almacena la lista que contiene a los rangos para luego retonarla 
        if (encontrado){
            valores= aux.getRango();
        }
        return valores;
   }
   public String toString (){
       int pos=0;
       String cadena="";
       NodoHashMapeo aux;
       while (pos<TAMANIO){
           aux=this.hash[pos];
           if (aux!=null){
                cadena+="Dominio "+this.hash[pos].getDominio() +"rango "+this.hash[pos].getRango() +"\n";
           }
           pos++;
           
       }
       return cadena;
       
   }
}
