/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;
import java.util.HashSet;

/**
 *
 * @author Anto
 */
public class Grafo {
    private NodoVert inicio;
        
    public Grafo (){
        this.inicio=null;
    }
    public boolean insertarVertice(Object nuevoVertice){
        //busca si el vertice ya esta presente en el grafo
        NodoVert n= ubicarVertice(nuevoVertice);
        boolean exito=false;
        //si no lo esta lo inserta
        if (n==null){
            this.inicio= new NodoVert(nuevoVertice, this.inicio);
            exito=true;
        }
        return exito;
    }
    private NodoVert ubicarVertice(Object elem){
        //busca el vertice que tiene al elem 
        NodoVert aux= this.inicio;
        while (aux!=null && !aux.getElem().equals(elem)){
            aux=aux.getSigVertice();
        }
        return aux;
    }
    public boolean eliminarVertice(Object vertice){
        NodoVert aux=this.inicio;
        boolean exito=false;
        //si el vertice a eliminar se encuentra al principio de la lista seteo el inicio al siguiente
        if (this.inicio.getElem().equals(vertice)){
            this.inicio=this.inicio.getSigVertice();
            exito=true;
        }
        else{
            //sino busco el vertice
            while (aux!=null && !exito){
                //si el vertice siguiente al que estoy parado tiene el vertice a eliminar le seteo el siguiente al proximo
                if(aux.getSigVertice().getElem().equals(vertice)){
                    //elimino todos los arcos que tenian a vertice
                    eliminarArcos(aux.getSigVertice());
                    //elimino al vertice de la lista
                    aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    exito=false;
                }
                aux=aux.getSigVertice();
            }
        }
        return exito;
    }
    private void eliminarArcos(NodoVert n){
        NodoAdy adyacente=n.getPrimerAdy();
        while(adyacente!=null){
            eliminar(n, adyacente.getVertice());
            eliminar(adyacente.getVertice(),n);
            adyacente=adyacente.getSigAdyacente();
        }
    }
    public boolean existeVertice(Object verticeBuscado){
        return ubicarVertice(verticeBuscado)!=null;
    }
    public boolean insertarArco (Object origen, Object destino, int etiqueta){
        boolean exito=false;
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        //si ambos vertices se encuentran en el grafo
        if (nodoO!=null && nodoD!=null){
            
            nodoO.setPrimerAdy(new NodoAdy (nodoD, nodoO.getPrimerAdy(),etiqueta));
            nodoD.setPrimerAdy(new NodoAdy (nodoO, nodoD.getPrimerAdy(),etiqueta));
            exito=true;
        }
        return exito;
    }
    public boolean eliminarArco(Object origen, Object destino){
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        boolean exito=false;
        //si existen ambos vertices llama al metodo eliminar
        if (nodoO!=null && nodoD!=null){
           exito= eliminar(nodoO, nodoD);
           eliminar(nodoD, nodoO);
            
            
        }
        return exito;
    }
    private boolean eliminar(NodoVert nodoO,NodoVert nodoD){
        boolean encontrado=false;
        NodoAdy aux=nodoO.getPrimerAdy();
        if (aux.getVertice().equals(nodoD)){
            nodoO.setPrimerAdy(nodoO.getPrimerAdy().getSigAdyacente());
        }
        else{
            while(!encontrado && aux!=null){
                if(aux.getSigAdyacente()!=null && aux.getSigAdyacente().getVertice().equals(nodoD)){
                    aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    encontrado=true;
                }
                aux=aux.getSigAdyacente();
            }
        }
        return encontrado;
    }
    
    public boolean existeArco(Object origen, Object destino){
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        return verificarArco(nodoO, nodoD);
        
    }
    private boolean verificarArco(NodoVert nodoO, NodoVert nodoD){
        boolean encontrado=false;
        NodoAdy aux=nodoO.getPrimerAdy();
        //busca en la lista de nodos adyacentes de el nodoO al nodoD 
        while(!encontrado && aux!=null){
            if(aux.getVertice().equals(nodoD)){
                encontrado=true;
            }
            aux=aux.getSigAdyacente();
        }
        return encontrado;
    }
    public boolean cambiarEtiqueta(Object origen, Object destino, int nuevaEtiqueta){
        boolean encontrado=false;
        NodoVert o=ubicarVertice(origen);
        if (o!=null){
            NodoAdy ad=o.getPrimerAdy();
            while (!encontrado && ad!=null){
                if (ad.getVertice().getElem().equals(destino)){
                    ad.setEtiqueta(nuevaEtiqueta);
                    encontrado=true;
                }
            }
                   
        }
        return encontrado;
    }
    public boolean existeCamino (Object origen, Object destino){
        //busca el nodo origen de el camino
        NodoVert o=ubicarVertice(origen);
        HashSet visitados= new HashSet ();
        return buscarCamino(o, destino,visitados);
    }

    private boolean buscarCamino(NodoVert n, Object destino,HashSet visitados){
        boolean encontrado=false;
        if (n!=null){
            //añade en visitados el nodo actual
            visitados.add(n.getElem());
            //busca los caminos en los nodos adyacentes
            NodoAdy ad=n.getPrimerAdy();
            //si el elemento del adyacente coincide con el de destino se encontro un camino
            if (ad.getVertice().getElem().equals(destino)){
                    encontrado=true;
            }
            //sino llama recursivamente con cada adyacente hasta encontrarlo o hasta que se termine la lista de los mismos
            while (ad!=null && !encontrado){
                if(!visitados.contains(ad.getVertice().getElem())){
                        encontrado= buscarCamino(ad.getVertice(),destino, visitados);
                }
                ad=ad.getSigAdyacente();
            }
        }
        return encontrado;
    }

    

    public HashSet listarEnProfundidad(){
        HashSet visitados= new HashSet ();
        NodoVert u= this.inicio;
        while (u!=null){
            if(!visitados.contains(u)){
                profundidadDesde(u,visitados);
            }
            u=u.getSigVertice();
        }
        
        return visitados;
    }
    private void profundidadDesde(NodoVert n, HashSet visitados){
        if (n!=null){
            visitados.add(n.getElem());
            NodoAdy v=n.getPrimerAdy();
            while (v!=null){
                if(!visitados.contains(v.getVertice().getElem())){
                    profundidadDesde(v.getVertice(), visitados);
                }
                v=v.getSigAdyacente();
            }
        }
       
    }
    public Lista caminoMasLargo(Object origen, Object destino){
        NodoVert n=ubicarVertice(origen);
        Lista caminoMax= new Lista();
        Lista visitados= new Lista();
        return  profundidadCaminoMasLargo(n, visitados, destino, 0, caminoMax);
        
    }
     private Lista profundidadCaminoMasLargo(NodoVert n, Lista visitados, Object destino, int longitudMayor, Lista caminoMax){
         //intentar mejorar los llamados a longitud
        int longitudActual;
        if (n!=null){
            longitudActual=visitados.longitud();
            visitados.insertar(n.getElem(),longitudActual+1);
            if(n.getElem().equals(destino)){
                if (longitudActual>=longitudMayor){
                    caminoMax=visitados.clone();                    
                }
            }
            else{
                NodoAdy v=n.getPrimerAdy();
                while (v!=null){
                    if(visitados.localizar(v.getVertice().getElem())<0){
                        caminoMax=profundidadCaminoMasLargo(v.getVertice(), visitados,destino,longitudMayor,caminoMax);
                        longitudMayor=caminoMax.longitud();
                        visitados.eliminar(visitados.longitud());
                    }
                    v=v.getSigAdyacente();
                }
            }
        }
        return caminoMax;
       
    }
     public Lista caminoMasCorto(Object origen, Object destino){
        NodoVert n=ubicarVertice(origen);
        Lista caminoMin= new Lista();
        Lista visitados= new Lista();
        return  profundidadCaminoMasCorto(n, visitados, destino, 0, caminoMin);
        
    }
     private Lista profundidadCaminoMasCorto(NodoVert n, Lista visitados, Object destino, int longitudMin, Lista caminoMin){
        int longitudActual;
        if (n!=null){
            longitudActual=visitados.longitud();
            visitados.insertar(n.getElem(),longitudActual+1);
            if(n.getElem().equals(destino)){
                if (longitudMin==0 || (longitudActual<longitudMin)){
                    caminoMin=visitados.clone();  
                }
            }
            else{
                NodoAdy v=n.getPrimerAdy();
                while (v!=null){
                    if(visitados.localizar(v.getVertice().getElem())<0){
                        caminoMin=profundidadCaminoMasCorto(v.getVertice(), visitados,destino,longitudMin,caminoMin);
                        longitudMin=caminoMin.longitud();
                        visitados.eliminar(visitados.longitud());
                    }
                    v=v.getSigAdyacente();
                }
            }
        }
        return caminoMin;
   
     }
     public Lista listarEnAnchura(){
        Lista visitados= new Lista ();
        NodoVert u= this.inicio;
        while (u!=null){
            if(visitados.localizar(u.getElem())<0){
                anchuraDesde(u,visitados);
            }
            u=u.getSigVertice();
        }
        return visitados;
     }
    private void anchuraDesde(NodoVert verticeInicial,Lista  visitados){
        Cola q= new Cola ();
        q.poner(verticeInicial);
        visitados.insertar(verticeInicial.getElem(), visitados.longitud()+1);
        while (!q.esVacia()){
            NodoVert u=(NodoVert) q.obtenerFrente();
            q.sacar();
            NodoAdy v=u.getPrimerAdy();
            while (v!=null){
                if (visitados.localizar(v.getVertice().getElem())<0){
                    visitados.insertar(v.getVertice().getElem(), visitados.longitud()+1);
                    q.poner(v.getVertice());
                }
                v=v.getSigAdyacente();
            }
        
           
     }
     }
    @Override
    public String toString (){
        NodoVert aux=this.inicio;
        String cadena="";
        while (aux!=null){
           cadena+= "Vertice= "+aux.getElem();
           NodoAdy ad=aux.getPrimerAdy();
           while (ad!=null){
               cadena+= " adyacente= "+ad.getVertice().getElem() +" etiqueta= "+ad.getEtiqueta();
               ad=ad.getSigAdyacente();
           }
           cadena+="\n";
           aux=aux.getSigVertice();
        }
        return cadena;
        
    }
    public boolean esVacio(){
        return this.inicio==null;
    }
    public void vaciar(){
        this.inicio=null;
    }
    @Override
    public Grafo clone(){
        Grafo clone= new Grafo();
        if (this.inicio!=null){
            //copia el inicio de la lista de vertices
            clone.inicio=new NodoVert(this.inicio.getElem(), null);
            NodoVert c=clone.inicio;
            NodoVert n=this.inicio;
            //copia de todos los vertices en la lista
            n=n.getSigVertice();
            while (n!=null){
                c.setSigVertice(new NodoVert (n.getElem(),null));
                c=c.getSigVertice();
                n=n.getSigVertice();
            }
            c=clone.inicio;
            n=this.inicio;
            //copia de los vertices adyacentes
            while(n!=null){
                copiaNodosAdy(n,c,clone.inicio);
                n=n.getSigVertice();
                c=c.getSigVertice();
            }
            
            
        }
     
        return clone;
    }
    private void copiaNodosAdy (NodoVert n, NodoVert c, NodoVert inicioClone){
        NodoAdy aux=n.getPrimerAdy();
        //copia de los ndos adyacentes
        if (aux!=null){
            c.setPrimerAdy(new NodoAdy (ubicarVertice(aux.getVertice().getElem(),inicioClone),null,aux.getEtiqueta()));
            NodoAdy auxC=c.getPrimerAdy();
            aux=aux.getSigAdyacente();
            while (aux!=null){
                auxC.setSigAdyacente(new NodoAdy (ubicarVertice(aux.getVertice().getElem(),inicioClone),null,aux.getEtiqueta()));
                aux=aux.getSigAdyacente();
                auxC=auxC.getSigAdyacente();
            }
        }
    }
    private NodoVert ubicarVertice(Object elem, NodoVert n){
        NodoVert aux= n;
        while (aux!=null && !aux.getElem().equals(elem)){
            aux=aux.getSigVertice();
        }
        return aux;
    }


}
