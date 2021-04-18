/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;
import lineales.dinamicas.Lista;
/**
 *
 * @author Anto
 */
public class ArbolBin {
    NodoArbol raiz;
    
    public ArbolBin (){
        this.raiz=null;
    }
    public boolean insertar (Object nuevoElem, Object elemPadre, char lugar){
        boolean exito=true;
        if (this.raiz==null){
            this.raiz=new NodoArbol (nuevoElem,null,null);
        }
        else{
            NodoArbol nodoPadre=obtenerNodo (this.raiz, elemPadre);
            if (nodoPadre!=null){
                if( lugar=='I' && nodoPadre.getIzquierdo()==null){
                    nodoPadre.setIzquierdo(new NodoArbol (nuevoElem,null,null));
                }
                else{
                    if(lugar=='D' && nodoPadre.getDerecho()==null){
                        nodoPadre.setDerecho(new NodoArbol (nuevoElem, null,null));
                    }
                    else{
                        exito=false;
                    }
                }
            }
            else{
                exito=false;
            }
        }
        return exito;
    }
    private NodoArbol obtenerNodo (NodoArbol n, Object buscado){
        NodoArbol resultado=null;
        if (n!=null){
            if (n.getElem().equals(buscado)){
                resultado=n;
            }
            else{
                resultado=obtenerNodo (n.getIzquierdo(),buscado);
                if (resultado==null){
                    resultado=obtenerNodo (n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }
    public boolean esVacio (){
        return (this.raiz==null);
    }
    
    public int altura (){
        //este metodo devuelve la altura del arbol desde la raiz 
        int altura=-1;
        if (this.raiz!=null){
            altura=calcularLongitud(this.raiz);
        }
        return altura;
    }
    private int calcularLongitud(NodoArbol nodo){
        //este metodo calcula la longitud de un arbol
        int longitudDer=-1, longitudIzq=-1, mayorLongitud;
        if ( nodo.getIzquierdo()==null && nodo.getDerecho()==null){
                longitudDer=0;
                longitudIzq=0;
        }
        else{
            if (nodo.getIzquierdo()!=null){
                    longitudIzq=1+calcularLongitud(nodo.getIzquierdo ());
            }
            if (nodo.getDerecho()!=null){
                    longitudDer= 1+calcularLongitud(nodo.getDerecho ());
            }
               
            }
        if (longitudDer>=longitudIzq){
            mayorLongitud=longitudDer;
        }
        else{
            mayorLongitud=longitudIzq;
        }
        return mayorLongitud;
    }
    public int nivel (Object elem){
        //este metodo retorn el nivel donde se encuentra un determinado elemento en el arbol
       int nivel=-1;
       //si el arbol no esta vacio 
       if (this.raiz!=null){
           nivel=encontrarNivel(this.raiz, elem,-1);
        }
       
       return nivel;
    }

    private int encontrarNivel (NodoArbol nodo, Object elem, int nivel){
        int encontrado=-1;
        //si el nodo es una hoja y el elemento esta en dicho nivel retornamos 0
        if (nodo.getElem().equals(elem)){
            encontrado= nivel+1;
        }
        else {
            if (nodo.getIzquierdo()==null &&  nodo.getDerecho()==null ){
                encontrado=-1;
            }
            else{
                if (nodo.getIzquierdo ()!=null){
                    encontrado=encontrarNivel(nodo.getIzquierdo(),elem,nivel+1);
                }
                if (nodo.getDerecho()!=null && encontrado==-1 ){
                    //i=nivel;
                    encontrado=encontrarNivel(nodo.getDerecho(),elem,nivel+1);
                }  
            }   
        }
        return encontrado;
}

   

    public Lista listarPreorden (){
        Lista lista=null;
        if (this.raiz!=null){
            lista=new Lista ();
            recursivoPreorden (this.raiz,lista,1);
        }
        return lista;
    }
    public int recursivoPreorden (NodoArbol nodo,Lista lista, int pos){
        if (nodo!=null){
            lista.insertar(nodo.getElem(), pos);
            if (nodo.getIzquierdo() !=null){
                pos=recursivoPreorden (nodo.getIzquierdo(),lista,pos+1);
            }
            if (nodo.getDerecho() !=null){
                pos=recursivoPreorden (nodo.getDerecho(),lista,pos+1);
            }
        }
        return pos; 
    }
     public Lista listarInorden (){
        Lista lista=null;
        if (this.raiz!=null){
            lista=new Lista ();
            recursivoInorden (this.raiz,lista,1);
        }
        return lista;
    }
    public int recursivoInorden (NodoArbol nodo,Lista lista, int pos){
        if (nodo!=null){
            //si llego a la hoja del lado izquierdo imprimo y aumento pos
            if (nodo.getIzquierdo()==null && nodo.getDerecho()==null){
                lista.insertar(nodo.getElem(), pos);
                pos++;
            }
            else{
                //
                if (nodo.getIzquierdo() !=null){
                    pos=recursivoInorden (nodo.getIzquierdo(),lista,pos); 
                }
                lista.insertar(nodo.getElem(), pos);
                pos++;
                if (nodo.getDerecho() !=null){
                    pos=recursivoInorden (nodo.getDerecho(),lista,pos);
                }
                
            }

        }
        return pos;
    }
        public Lista listarPosorden (){
        Lista lista=null;
        if (this.raiz!=null){
            lista=new Lista ();
            recursivoPosorden (this.raiz,lista,1);
        }
        return lista;
    }
    public int recursivoPosorden (NodoArbol nodo,Lista lista, int pos){
      if (nodo!=null){
            //si llego a la hoja del lado izquierdo imprimo y aumento pos
            if (nodo.getIzquierdo()==null && nodo.getDerecho()==null){
                lista.insertar(nodo.getElem(), pos);
                pos++;
            }
            else{
                //
                if (nodo.getIzquierdo() !=null){
                    pos=recursivoPosorden (nodo.getIzquierdo(),lista,pos); 
                }
                
                if (nodo.getDerecho() !=null){
                    pos=recursivoPosorden (nodo.getDerecho(),lista,pos);
                }
                lista.insertar(nodo.getElem(), pos);
                pos++;
                
            }

        }
        return pos;
    }
    public void vaciar (){
        this.raiz=null;
    }
    @Override
   public String toString (){
       String cadena="";
       if (this.raiz!=null){
           cadena=stringAux(this.raiz, cadena);
       }
       return cadena;
   }
    private String stringAux (NodoArbol nodo, String cadena){
       //precondicion nodo debe ser distinto de nulo
       String cadena2=cadena;
       cadena2+="Nodo:"+nodo.getElem();
       //si existe el hijo izquierdo lo concatena
       if (nodo.getIzquierdo()!=null){
            cadena2+=" HI:"+ nodo.getIzquierdo().getElem();
       }
       //sino concatena -
       else{
           cadena2+=" HI:-";
       }
       //si existe el hijo derecho lo concatena
       if (nodo.getDerecho()!=null){
            cadena2+=" HD:"+ nodo.getDerecho().getElem()+"\n";
       }
       //sino concatena-
       else{
            cadena2+=" HD:- \n";
       }
       //si tiene hijo izquierdo llama recursivamente con el
       if (nodo.getIzquierdo()!=null){
           cadena2=stringAux(nodo.getIzquierdo(),cadena2);
       }
       //si tiene hijo derecho llama recursivamente con el 
       if (nodo.getDerecho()!=null){
            cadena2=stringAux(nodo.getDerecho(),cadena2);
       }
        return cadena2;
  
    }
}
