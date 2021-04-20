/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Nodo;
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
        //max math entre ambos
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

   public Object padre (Object elem){
       Object padre=null;
       if (this.raiz!=null){
           //si se busca en padre de la raiz devolvemos null
           if (!(this.raiz.getElem().equals(elem))){
              padre=auxPadre(this.raiz,elem).getElem(); 
           }
       }
       return padre;
   }
   private NodoArbol auxPadre (NodoArbol nodo,Object elem ){
       NodoArbol padre=null;
       //estoy parada un nodo mas arriba de los que comparo
       if ( (nodo.getIzquierdo()!=null && nodo.getIzquierdo().getElem().equals(elem))|| (nodo.getDerecho()!=null && nodo.getDerecho().getElem().equals(elem))){
           padre=nodo;
       }
       else{
           if (nodo.getIzquierdo()!=null){
               padre=auxPadre (nodo.getIzquierdo(), elem);
               //si no lo encontre de ese lado llamo al otro
               if (padre==null && nodo.getDerecho()!=null){
                    padre=auxPadre(nodo.getDerecho(),elem);
               }
              
           }
       }
       return padre;
   }
           //nodo=Anterior
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
    public Lista listarNiveles (){
        //este metodo devuelve una lista con los elementos del arbol almacenados por niveles
        Lista lista= new Lista ();
        int i=1;
        Cola Q= new Cola ();
        Q.poner(this.raiz);
        while (!Q.esVacia()){
            //obtenemos el nodo de la cola
            NodoArbol nodoActual= (NodoArbol) Q.obtenerFrente();
            //lo sacamos
            Q.sacar();
            //lo insertamos en la lista
            lista.insertar(nodoActual.getElem(), i);
            //si tiene hijo izquierdo lo pongo en la cola
            if (nodoActual.getIzquierdo()!=null){
                Q.poner(nodoActual.getIzquierdo());
            }
            //si tiene hijo derecho lo pongo en la cola 
            if (nodoActual.getDerecho()!=null){
                Q.poner(nodoActual.getDerecho());
            }
            i++;
         
        }
        return lista;
    }

    public void vaciar (){
        this.raiz=null;
    }
    @Override
   public String toString (){
       String cadena="Arbol vacio";
       if (this.raiz!=null){
           cadena="";
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
    public ArbolBin clone (){
        ArbolBin clone= new ArbolBin ();
        if (this.raiz!=null){
            NodoArbol aux2=new NodoArbol (this.raiz.getElem(),null,null);
            clone.raiz=aux2;
            auxClon (this.raiz, clone, aux2);
            
        }
        return clone;
       
    }
    private void auxClon (NodoArbol nodo, ArbolBin clone, NodoArbol aux2){
        //precondicion nodo distinto de null
        //si eixste un nodo izquierdo creo un nodo nuevo a la izquierda de mi nodo raiz
        if (nodo.getIzquierdo()!=null){
            aux2.setIzquierdo( new NodoArbol (nodo.getIzquierdo().getElem(),null,null));
        }
        //si eixste un nodo derecho creo un nodo nuevo a la derecho de mi nodo raiz
        if (nodo.getDerecho()!=null){
             aux2.setDerecho(new NodoArbol (nodo.getDerecho().getElem(),null,null));
        }
        //si el izquierdo existe llamo a auxClon con ese nodo
        if (nodo.getIzquierdo()!=null){
            
            auxClon (nodo.getIzquierdo(),clone, aux2.getIzquierdo());
        }
        //si el derecho existe llamo a auxClon con ese nodo
        if (nodo.getDerecho()!=null){
             auxClon (nodo.getDerecho(),clone, aux2.getDerecho());
        }
        
    }
    public Lista frontera (){
        Lista lista= new Lista ();
        //si el arbol no es vacio
        if (this.raiz!=null){
            //verifico si la raiz es una hoja
            if (this.raiz.getDerecho()==null && this.raiz.getIzquierdo()==null){
                lista.insertar(this.raiz, 1);
            }
            //si no lo es llamo al metodo aux
            else{
                fronteraAux(this.raiz,lista,1);
            }
        }
        return lista;
    }
    
    private int fronteraAux(NodoArbol nodo,Lista lista,int pos){
        //pongo en la lista el elemento que sea hoja
        if (nodo.getDerecho()==null && nodo.getIzquierdo()==null){
            lista.insertar(nodo.getElem(), pos);
            pos++;
        }
        else{
            //llamo recursivamente con cada lado
            //puede que tenga hijo de un solo lado asi que verifico para no llamar con un elemento nulo
            if (nodo.getIzquierdo()!=null){
                fronteraAux(nodo.getIzquierdo(), lista, pos);
            }
            if (nodo.getDerecho()!=null){
                fronteraAux(nodo.getDerecho(),lista,pos);
            }  
        }
       return pos;
    }
 }
