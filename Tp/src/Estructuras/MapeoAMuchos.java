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
        int pos = dominio.hashCode()%TAMANIO,res;
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
        int pos=dominio.hashCode()%TAMANIO;
        NodoHashMapeo aux=this.hash[pos];
        boolean exito=false,encontrado=false;
        //busca el nodo que contiene al dominio
        while (!encontrado && aux!=null){
            encontrado=aux.equals(dominio);
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
                   aux=aux.getEnlace();//VERIFICAR 
                }
                exito=true;
            }
            
        }    
        return exito;
        
    }

   public Lista obtenerValores(Object dominio){
       //busca la posicion de el elemento en la tabla 
        int pos=dominio.hashCode()%TAMANIO;
        NodoHashMapeo aux=this.hash[pos];
        boolean encontrado=false;
        Lista valores= new Lista ();
        //busca el nodo que contiene al dominio
        while (!encontrado && aux!=null){
            encontrado=aux.equals(dominio);
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
}
