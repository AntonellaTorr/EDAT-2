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
        NodoVert n= ubicarVertice(nuevoVertice);
        boolean exito=false;
        if (n==null){
            this.inicio= new NodoVert(nuevoVertice, this.inicio);
            exito=true;
        }
        return exito;
    }
    private NodoVert ubicarVertice(Object elem){
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
                    aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    exito=false;
                }
                aux=aux.getSigVertice();
            }
        }
        return exito;
    }
    public boolean existeVertice(Object verticeBuscado){
        return ubicarVertice(verticeBuscado)!=null;
    }
    public boolean insertarArco (Object origen, Object destino, int etiqueta){
        boolean exito=false;
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
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
        if (nodoO!=null && nodoD!=null){
           eliminar(nodoO, nodoD);
           eliminar(nodoD, nodoO);
           exito=true; 
            
        }
        return exito;
    }
    private void eliminar(NodoVert nodoO,NodoVert nodoD){
        boolean encontrado=false;
        NodoAdy aux=nodoO.getPrimerAdy();
        if (aux.getVertice().equals(nodoD)){
            nodoO.setPrimerAdy(nodoO.getPrimerAdy().getSigAdyacente());
        }
        else{
            while(!encontrado && aux!=null){
                if(aux.getSigAdyacente().getVertice().equals(nodoD)){
                    aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    encontrado=true;
                }
                aux=aux.getSigAdyacente();
            }
        }
    }
    
    public boolean existeArco(Object origen, Object destino){
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        boolean exito=verificarArco(nodoO, nodoD);
        return exito;
        
    }
    private boolean verificarArco(NodoVert nodoO, NodoVert nodoD){
        boolean encontrado=false;
        NodoAdy aux=nodoO.getPrimerAdy();
        while(!encontrado && aux!=null){
            if(aux.getVertice().equals(nodoD)){
                encontrado=true;
            }
            aux=aux.getSigAdyacente();
        }
        return encontrado;
    }
    public boolean existeCamino (Object origen, Object destino){
        NodoVert o=ubicarVertice(origen);
        HashSet visitados= new HashSet ();
        
        return buscarCamino(o, destino,visitados);

    }

    private boolean buscarCamino(NodoVert n, Object destino,HashSet visitados){
        boolean encontrado=false;
        if (n!=null){
            visitados.add(n.getElem());
            NodoAdy ad=n.getPrimerAdy();
            if (ad.getVertice().getElem().equals(destino)){
                    encontrado=true;
            }
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
        profundidadCamino(n, visitados, destino, 0, caminoMax);
        return caminoMax;
        
    }
     private int profundidadCamino(NodoVert n, Lista visitados, Object destino, int longitudMayor, Lista caminoMax){
        int longitudActual;
        if (n!=null){
            longitudActual=visitados.longitud();
            visitados.insertar(n.getElem(),longitudActual+1);
            if(n.getElem().equals(destino)){
                if (longitudActual>=longitudMayor){
                    caminoMax=visitados.clone();
                    longitudMayor=longitudActual;
                }
            }
            else{
                NodoAdy v=n.getPrimerAdy();
                while (v!=null){
                    if(visitados.localizar(v.getVertice().getElem())<0){
                        longitudMayor=profundidadCamino(v.getVertice(), visitados,destino,longitudMayor,caminoMax);
                        visitados.eliminar(longitudActual);
                    }
                    v=v.getSigAdyacente();
                }
            }
        }
        return longitudMayor;
       
    }
     public HashSet listarEnAnchura(){
        HashSet visitados= new HashSet ();
        NodoVert u= this.inicio;
        while (u!=null){
            if(!visitados.contains(u)){
                anchuraDesde(u,visitados);
            }
            u=u.getSigVertice();
        }
        return visitados;
     }
     private void anchuraDesde(NodoVert u, HashSet visitados){
        Cola q= new Cola ();
        visitados.add(u);
        q.poner(u);
        while (!q.esVacia()){
          
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
            clone.inicio=new NodoVert(this.inicio.getElem(), null);
            NodoVert c=clone.inicio;
            NodoVert n=this.inicio.getSigVertice();
            while (n!=null){
                 c.setSigVertice(new NodoVert (n.getElem(),null));
                 c=c.getSigVertice();
                 n=n.getSigVertice();
            }
            
        }
     
        return clone;
    }

}
