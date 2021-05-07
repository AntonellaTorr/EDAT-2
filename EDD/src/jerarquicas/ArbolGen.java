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
    
    public ArbolGen(){
        this.raiz=null;
    }
    public boolean insertar(Object elemNuevo, Object elemPadre){
        boolean exito=false;
        if (this.raiz==null){
            this.raiz=new NodoGen (elemNuevo, null, null);
        }
        else{
            NodoGen padre=obtenerNodo(this.raiz, elemPadre);
            if (padre!=null){
                //hago que el padre apunte a mi nodo nuevo y que el nodo nuevo apunte al hijo izquierdo que ya existia antes
                //o a null si no existia 
                padre.setHijoIzquierdo(new NodoGen(elemNuevo,null, padre.getHijoIzquierdo()));
                exito=true;
            }  
        }
        return exito;
    }
    private NodoGen obtenerNodo(NodoGen nodo,Object elemPadre){
        NodoGen resultado=null;
        if(nodo!=null){
            if (nodo.getElem().equals(elemPadre)){
                resultado=nodo;
            }
            else{
                NodoGen hijo= nodo.getHijoIzquierdo();
                while (hijo!=null && resultado==null){
                    resultado=obtenerNodo(hijo, elemPadre);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }
    
    public boolean pertenece(Object elem){
        return buscarElem(this.raiz, elem);
    }
 
        private boolean buscarElem(NodoGen nodo,Object elemBuscado){
        //este metodo privado busca un elemento en el arbol de forma recursiva
        boolean resultado=false;
        if(nodo!=null){
            //verifico si en el nodo en el que estoy esta el elem
            if (nodo.getElem().equals(elemBuscado)){
                resultado=true;
            }
            else{
                //sino llama con los hijos
                NodoGen hijo= nodo.getHijoIzquierdo();
                while (hijo!=null && !resultado){
                    resultado=buscarElem(hijo,elemBuscado);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }
    public Lista ancestros(Object elem){
       Lista listaAncestros= new Lista ();
       if (!this.raiz.getElem().equals(elem)){
           buscarAncestros(this.raiz, elem, listaAncestros,1);
       }
        
       return listaAncestros;
    }
    private boolean buscarAncestros (NodoGen nodo,Object elem, Lista lista, int pos){
        boolean encontrado=false;
        if (nodo!=null){
            NodoGen hijo= nodo.getHijoIzquierdo();
            while (hijo!=null && !encontrado){
              if (hijo.getElem().equals(elem)){
                  lista.insertar(nodo.getElem(), pos);
                  encontrado=true;
              }
              hijo=hijo.getHermanoDerecho();
              
              }
            if(!encontrado){
                lista.insertar(nodo.getElem(), pos);
                hijo=nodo.getHijoIzquierdo();
                while (hijo!=null && !encontrado){
                     encontrado=buscarAncestros(hijo, elem, lista, pos+1);
                     if (!encontrado){
                         lista.eliminar(pos+1);
                         hijo=hijo.getHermanoDerecho();
                     }
                }
           
        }
         }
       
        return encontrado;
    }
    public int altura (){
        return alturaAux(this.raiz,-1,-1);        
    }
    private int alturaAux(NodoGen nodo, int alt, int mayorAltura){
        int altura=-1;
        if (nodo!=null){
            //si el nodo es una hoja
            if (nodo.getHijoIzquierdo()==null){
                //calculo la altura hasta ese momento
                altura=alt+1;
                //compara la mayorAltura obtenida hasta ahora con la que se calculo recien
                //y se queda con la mayor
                mayorAltura= Math.max(altura, mayorAltura);
            }
            else{
                //llama con cada uno de sus hijos
                NodoGen hijo=nodo.getHijoIzquierdo();
                while (hijo!=null){
                    altura=alturaAux(hijo,alt+1, mayorAltura);
                    mayorAltura= Math.max(altura, mayorAltura);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
        
        return mayorAltura;
    }
    public boolean esVacio(){
        return (this.raiz==null);
    }
    public void vaciar(){
        this.raiz=null;
    }
    
    public int nivel (Object elem){
        return buscarNivel(this.raiz, elem,-1);
    }
    private int buscarNivel(NodoGen nodo, Object elem, int nivel){
        int encontrado=-1;
        if(nodo!=null){
            if (nodo.getElem().equals(elem)){
                encontrado=nivel+1;
            }
            else{
                NodoGen hijo=nodo.getHijoIzquierdo();
                //hay que recorrer todos los hermanos hasta que no hayan mas o hasta que encontremos el elemento buscado
                while (encontrado==-1 && hijo!=null){
                    encontrado=buscarNivel(hijo,elem,nivel+1);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
        return encontrado;
    }
    public Object padre (Object elem){
        return padreAux(this.raiz, elem);
    }
     
    private Object padreAux (NodoGen nodo, Object elem){
        Object padre=null;
        if (nodo!=null && nodo.getHijoIzquierdo()!=null){
            //verifica si alguno los hijos es el elemento buscado
            NodoGen hijo=nodo.getHijoIzquierdo();
            while (hijo!=null && padre==null){
                    if (hijo.getElem().equals(elem)){
                        padre=nodo.getElem();
                    }
                    hijo=hijo.getHermanoDerecho();
                }
            
            //si no lo encontró
            if (padre==null){
                //llama recursivamente con cada hijo 
                hijo=nodo.getHijoIzquierdo();
                while (padre==null && hijo!=null){
                     padre=padreAux(hijo, elem);
                     hijo=hijo.getHermanoDerecho();
                }
                
            }
        }
        return padre;
    }
    public Lista listarInorden (){
      Lista lista= new Lista();
      listarInordenAux(this.raiz,lista);
      return lista;
    }
    private void listarInordenAux(NodoGen nodo, Lista lista){
        if (nodo!=null){
            if (nodo.getHijoIzquierdo()!=null){
                listarInordenAux(nodo.getHijoIzquierdo(),lista);
            }
            lista.insertar(nodo.getElem(), lista.longitud()+1);
            if (nodo.getHijoIzquierdo()!=null){
                NodoGen hijo= nodo.getHijoIzquierdo().getHermanoDerecho();
                while (hijo!=null){
                    listarInordenAux(hijo,lista);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
    }
    public Lista listarPreorden(){
        Lista lista= new Lista ();
        listarPreordenAux(this.raiz,lista,1);
        return lista;
    }
    private int listarPreordenAux (NodoGen nodo, Lista lista, int pos){
        if(nodo!=null){
            //visitamos la raiz 
            lista.insertar(nodo.getElem(), pos);
            if (nodo.getHijoIzquierdo()!=null){
                //llamamos recursivamente con el hijo izquierdo
                pos=listarPreordenAux(nodo.getHijoIzquierdo(),lista,pos+1);
                //llamamos con los hermanos
                NodoGen hijo=nodo.getHijoIzquierdo().getHermanoDerecho();
                while (hijo!=null){
                    pos=listarPreordenAux(hijo, lista, pos+1);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
        return pos;
    } 
    public Lista listarPosorden (){
       Lista lista= new Lista ();
       listarPosordenAux(this.raiz, lista, 1);
       return lista;
    }
    private int listarPosordenAux (NodoGen nodo, Lista lista, int pos){
        if (nodo!=null){
            if(nodo.getHijoIzquierdo()!=null){
                //llama recursivamente con su hijo izquierdo
                pos=listarPosordenAux(nodo.getHijoIzquierdo(),lista, pos);
                //llamada con los hermanos del hijo izquierdo
                NodoGen hijo=nodo.getHijoIzquierdo().getHermanoDerecho();
                while (hijo!=null){
                    pos=listarPosordenAux(hijo, lista, pos);
                    hijo=hijo.getHermanoDerecho();
                }
            }
            //cuando llega a la hoja inserta
            lista.insertar(nodo.getElem(), pos);
            pos++;
        }
        return pos;
    }
    public Lista listarPorNiveles (){
        Lista lista =new Lista();
        if (this.raiz!=null){
            Cola q= new Cola ();
            q.poner(this.raiz);
            int i=1;
            while (!q.esVacia()){
                NodoGen nodo= (NodoGen) q.obtenerFrente();
                q.sacar();
                //insertamos el elemento
                lista.insertar(nodo.getElem(), i);
                i++;
                //insertamos los hijos
                NodoGen hijo=nodo.getHijoIzquierdo();
                while (hijo!=null){
                    q.poner(hijo);
                    hijo=hijo.getHermanoDerecho();
                }
            }
        }
        return lista;
    }
    public ArbolGen clone (){
        ArbolGen clone= new ArbolGen ();
        if (this.raiz!=null){
            clone.raiz=new NodoGen (this.raiz.getElem(),null,null);
            cloneAux(this.raiz, clone.raiz);
        }
        return clone;
    }
    private void cloneAux(NodoGen nodo, NodoGen nodo2){
        if (nodo.getHijoIzquierdo()!=null){
            //creo al hijo izquierdo
            nodo2.setHijoIzquierdo(new NodoGen (nodo.getHijoIzquierdo().getElem(),null,null));
            //avanzo hacia el izquierdo en cada arbol
            nodo=nodo.getHijoIzquierdo();
            nodo2=nodo2.getHijoIzquierdo();
            //creo a los demas hijos
            NodoGen hijo= nodo.getHermanoDerecho();
            NodoGen hijo2=nodo2;
            while (hijo!=null){
                hijo2.setHermanoDerecho(new NodoGen(hijo.getElem(),null,null));
                hijo=hijo.getHermanoDerecho();
                hijo2=hijo2.getHermanoDerecho();
            }
            //llamo recursivamente con cada hijo
            hijo=nodo;
            hijo2=nodo2;
            while (hijo!=null){
                cloneAux(hijo,hijo2);
                hijo=hijo.getHermanoDerecho();
                hijo2=hijo2.getHermanoDerecho();
            }
            
        }
    }

  
    @Override
    public String toString (){
        return toStringAux(this.raiz);
    }
    private String toStringAux (NodoGen nodo){
        String cadena="";
        if (nodo!=null){
            cadena+= nodo.getElem().toString() +"->";
            NodoGen hijo=nodo.getHijoIzquierdo();
            while (hijo!=null){
                cadena+=hijo.getElem().toString()+",";
                hijo=hijo.getHermanoDerecho();
            }
            hijo=nodo.getHijoIzquierdo();
            while (hijo!=null){
                cadena+= "\n"+ toStringAux(hijo);
                hijo=hijo.getHermanoDerecho();
            }
        }
        return cadena;
    }

        
}
