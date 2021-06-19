/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

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
        boolean encontrado=false, exito=false;
        if (nodoO!=null && nodoD!=null){
            NodoAdy aux=nodoO.getPrimerAdy();
            if (aux.getVertice().equals(nodoD)){
                nodoO.setPrimerAdy(nodoO.getPrimerAdy().getSigAdyacente());
            }
            else{
                while(!encontrado){
                    if(aux.getSigAdyacente().getVertice().equals(nodoD)){
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                        encontrado=true;
                    }
                }
            }
            aux=nodoD.getPrimerAdy();
            if (aux.getVertice().equals(nodoO)){
                nodoD.setPrimerAdy(nodoD.getPrimerAdy().getSigAdyacente());
            }
            else{
                encontrado=false;
                while(!encontrado){
                    if(aux.getSigAdyacente().getVertice().equals(nodoO)){
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                        encontrado=true;
                    }
                }
            }
           exito=true; 
            
        }
        return exito;
    }

}
