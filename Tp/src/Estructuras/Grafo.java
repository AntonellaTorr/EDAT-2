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
        NodoVert n= ubicarVertice(nuevoVertice,this.inicio);
        boolean exito=false;
        //si no lo esta lo inserta
        if (n==null){
            this.inicio= new NodoVert(nuevoVertice, this.inicio);
            exito=true;
        }
        return exito;
    }
    private NodoVert ubicarVertice(Object elem,NodoVert n){
        NodoVert aux= n;
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
            eliminarArcos(this.inicio);
            this.inicio=this.inicio.getSigVertice();
            
            exito=true;
        }
        else{
            //sino busco el vertice
            while (aux!=null && !exito){
                //si el vertice siguiente al que estoy parado tiene el vertice a eliminar le seteo el siguiente al proximo
                if(aux.getSigVertice()!=null && aux.getSigVertice().getElem().equals(vertice)){
                    //elimino al vertice de la lista
                    eliminarArcos(aux.getSigVertice());
                    aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    exito=true;
                   
                }
                aux=aux.getSigVertice();
            }
        }
      
        
        return exito;
    }
    private void eliminarArcos(NodoVert n){
        //este metodo elimina todos los arcos que tiene como origen/destino al nodo recibido por parametro
        NodoAdy adyacente=n.getPrimerAdy();
        while(adyacente!=null){
            eliminarArcoAux(n, adyacente.getVertice());
            eliminarArcoAux(adyacente.getVertice(),n);
            adyacente=adyacente.getSigAdyacente();
        }
    }
     public boolean insertarArco (Object origen, Object destino, int etiqueta){
        boolean exito=false;
        //se ubican los vertices de origen y destino
        NodoVert nodoO=ubicarVertice(origen,this.inicio);
        NodoVert nodoD=ubicarVertice(destino,this.inicio);
        //si ambos vertices se encuentran en el grafo
        if (nodoO!=null && nodoD!=null){
            nodoO.setPrimerAdy(new NodoAdy (nodoD, nodoO.getPrimerAdy(),etiqueta));
            nodoD.setPrimerAdy(new NodoAdy (nodoO, nodoD.getPrimerAdy(),etiqueta));
            exito=true;
        }
        return exito;
    }
    public boolean existeVertice(Object verticeBuscado){
        return ubicarVertice(verticeBuscado,this.inicio)!=null;
    }
 
    public boolean eliminarArco(Object origen, Object destino){
        NodoVert nodoO=ubicarVertice(origen,this.inicio);
        NodoVert nodoD=ubicarVertice(destino,this.inicio);
        boolean exito=false;
        //si existen ambos vertices llama al metodo eliminar
        if (nodoO!=null && nodoD!=null){
           exito= eliminarArcoAux(nodoO, nodoD);
           eliminarArcoAux(nodoD, nodoO);
        }
        return exito;
    }
    private boolean eliminarArcoAux(NodoVert nodoO,NodoVert nodoD){
        //este metodo elimina el arco entre el nodoO y el nodoD
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
        NodoVert nodoO=ubicarVertice(origen,this.inicio);
        NodoVert nodoD=ubicarVertice(destino,this.inicio);
        return verificarArco(nodoO, nodoD);
        
    }
    private boolean verificarArco(NodoVert nodoO, NodoVert nodoD){
        boolean encontrado=false;
        if (nodoO!=null && nodoD!=null){
            NodoAdy aux=nodoO.getPrimerAdy();
            //busca en la lista de nodos adyacentes de el nodoO al nodoD 
            while(!encontrado && aux!=null){
                //si lo encuentra entonces hay un arco entre ambos nodos y retorna true
                if(aux.getVertice().equals(nodoD)){
                    encontrado=true;
                }
                aux=aux.getSigAdyacente();
            }
        }
        
        return encontrado;
    }

    public boolean existeCamino (Object origen, Object destino){
        //busca el nodo origen de el camino
        NodoVert o=ubicarVertice(origen,this.inicio);
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
            else{
                //sino llama recursivamente con cada adyacente hasta encontrarlo o hasta que se termine la lista de los mismos
                while (ad!=null && !encontrado){
                    if(!visitados.contains(ad.getVertice().getElem())){
                        encontrado= buscarCamino(ad.getVertice(),destino, visitados);
                    }
                    ad=ad.getSigAdyacente();
                 }
            }
            
        }
        return encontrado;
    }

    public HashSet listarEnProfundidad(){
        HashSet visitados= new HashSet ();
        NodoVert u= this.inicio;
        while (u!=null){
            //si el visitados no contiene al nodo entonces llama a profundidad
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
            //si v es distinto de null y visitados no lo contiene entonces llama recursivamente
            while (v!=null){
                if(!visitados.contains(v.getVertice().getElem())){
                    profundidadDesde(v.getVertice(), visitados);
                }
                v=v.getSigAdyacente();
            }
        }
       
    }
    public Lista caminoMasLargo(Object origen, Object destino){
        NodoVert n=ubicarVertice(origen,this.inicio);
        Lista caminoMax= new Lista();
        Lista visitados= new Lista();
        return  caminoMasLargoAux(n, visitados, destino, 0, caminoMax);
        
    }
     private Lista caminoMasLargoAux(NodoVert n, Lista visitados, Object destino, int longitudMayor, Lista caminoMax){
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
                    //si el nodo no se encuentra en visitados llama recursivamente 
                    if(visitados.localizar(v.getVertice().getElem())<0){
                        caminoMax=caminoMasLargoAux(v.getVertice(), visitados,destino,longitudMayor,caminoMax);
                        longitudMayor=caminoMax.longitud();
                        //restablecemos la lista a lo que era antes de el llamado recursivo 
                        visitados.eliminar(visitados.longitud());
                    }
                    v=v.getSigAdyacente();
                }
            }
        }
        return caminoMax;
       
    }
     public Lista caminoMasCorto(Object origen, Object destino){
        NodoVert n=ubicarVertice(origen,this.inicio);
        Lista caminoMin= new Lista();
        Lista visitados= new Lista();
        return  caminoMasCortoAux(n, visitados, destino, 0, caminoMin);
        
    }
     private Lista caminoMasCortoAux(NodoVert n, Lista visitados, Object destino, int longitudMin, Lista caminoMin){
        int longitudActual;
        if (n!=null){
            longitudActual=visitados.longitud();
            visitados.insertar(n.getElem(),longitudActual+1);
            //si llegamos al nodo destino entonces se ha encontrado un camino
            if(n.getElem().equals(destino)){
                //si todavia no habia ningun camino almacenado en caminoMin o si la longitud del camino actual es menor a la de el almacenado
                //almacenamos los nodos visitados hasta ahora
                if (longitudMin==0 || (longitudActual<longitudMin)){
                    caminoMin=visitados.clone();  
                }
            }
            else{
                NodoAdy v=n.getPrimerAdy();
                while (v!=null){
                    if(visitados.localizar(v.getVertice().getElem())<0){
                        caminoMin=caminoMasCortoAux(v.getVertice(), visitados,destino,longitudMin,caminoMin);
                        longitudMin=caminoMin.longitud();
                        //se reestablece la lista a lo que era antes de llamar recursivamente 
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
            //si el nodo no fue visitado se llama en ancchura
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
            //mientras u tenga nodos adyacentes
            while (v!=null){
                //si no fueron visitados 
                if (visitados.localizar(v.getVertice().getElem())<0){
                    //se insertan en la lista y en la cola
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
           cadena+= "Vertice-> "+aux.getElem() +",";
           NodoAdy ad=aux.getPrimerAdy();
           //mientras hayan adyacentes
           while (ad!=null){
               cadena+= "\n adyacente= "+ad.getVertice().getElem() +" etiqueta= "+ad.getEtiqueta()+ "\n";
               ad=ad.getSigAdyacente();
           }
           cadena+="\n";
           //avanza en la lista de vertices
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
        //este metodo clona los nodos adyacentes del nodo c recibido por parametro
        NodoAdy aux=n.getPrimerAdy();
        if (aux!=null){
            //copia el primer nodo adyacente
            c.setPrimerAdy(new NodoAdy (ubicarVertice(aux.getVertice().getElem(),inicioClone),null,aux.getEtiqueta()));
            NodoAdy auxC=c.getPrimerAdy();
            aux=aux.getSigAdyacente();
            //si tiene mas adyacentes los copia
            while (aux!=null){
                auxC.setSigAdyacente(new NodoAdy (ubicarVertice(aux.getVertice().getElem(),inicioClone),null,aux.getEtiqueta()));
                aux=aux.getSigAdyacente();
                auxC=auxC.getSigAdyacente();
            }
        }
    }

    public Lista buscarAdyacentes(Object h){
        //este metodo almacena en una lista todos los nodos adyacentes y su respectiva etiqueta
        //agregar arreglo de vertice y 
        NodoVert u= ubicarVertice(h,this.inicio);
        Lista adyacentes= new Lista ();
        int pos=1;
        if (u!=null){
            NodoAdy ad= u.getPrimerAdy();
            while (ad!=null){
                adyacentes.insertar(ad.getVertice().getElem(),pos);
                pos++;
                adyacentes.insertar(ad.getEtiqueta(),pos);
                pos++;
                ad=ad.getSigAdyacente();
            }
        }
        return adyacentes;
    }
    public boolean esPosibleLlegar (Object  h, Object h2, int peso){
        //este metodo averigua si es posible llegar de h a h2 sin superar el puntaje recibido por parametro
        NodoVert o=ubicarVertice(h,this.inicio);
        Lista visitados= new Lista ();
        //llamada a metodo auxiliar
        return esPosibleLlegar(o, h2,visitados,peso,0);
    }
    private boolean esPosibleLlegar(NodoVert n, Object destino,Lista visitados,int peso, int pesoTot){
        boolean encontrado=false;
        if (n!=null){
            visitados.insertar(n.getElem(),1);
            if(n.getElem().equals(destino)){
                encontrado=true;
            }
            else{
                NodoAdy ad=n.getPrimerAdy();
                //si todavia no se encontro el camino y existen mas adyacentes
                while (ad!=null && !encontrado){
                    //si no visitamos al nodo todavia
                    if(visitados.localizar(ad.getVertice().getElem())<0 ){
                        //si el puntaje acumulado no supera el puntaje que se paso parametro llamamos
                        if (pesoTot+ad.getEtiqueta()<=peso){
                            encontrado= esPosibleLlegar(ad.getVertice(),destino, visitados,peso,pesoTot+ad.getEtiqueta());
                            visitados.eliminar(1);
                        }
                    }
                    ad=ad.getSigAdyacente();
                }
            }  
        }
         return encontrado;
    }
    public Cola sinPasarPor (Object h, Object h2, Object h3, int peso){
        //este metodo devuelve todos los caminos entre h y h2 sin pasar por h3 y sin superar el peso recibido por parametro
        //ubicamos el nodo que contiene a h
        NodoVert o=ubicarVertice(h,this.inicio);
        //creacion de listas auxiliares
        Lista visitados= new Lista ();
        Cola caminos=new Cola();
        sinPasarPor(o, h2,h3,visitados,peso,0,caminos);
        return caminos;
    }
    private void sinPasarPor(NodoVert n, Object destino,Object h3,Lista visitados,int puntaje, int puntajeTot,Cola caminos){
        if (n!=null){
            visitados.insertar(n.getElem(),visitados.longitud()+1);
            //inserta en la lista de camino actual
            //si se encontro un camino se inserta en la lista de caminos
            if(n.getElem().equals(destino)){
               Lista cam=visitados.clone();
               caminos.poner(cam);
            }
            else{
                NodoAdy ad=n.getPrimerAdy();
                while (ad!=null){
                    //si no visitamos al nodo todavia y si no es igual al h3 
                    if(visitados.localizar(ad.getVertice().getElem())<0 && !ad.getVertice().getElem().equals(h3)){
                            //si para pasar al nodo adyacente no se supera el puntaje maximo llamamos recursivamente
                            if (puntajeTot+ad.getEtiqueta()<=puntaje){
                                 sinPasarPor(ad.getVertice(),destino,h3, visitados,puntaje,puntajeTot+ad.getEtiqueta(),caminos);
                                 visitados.eliminar(visitados.longitud());
                            }
                    }
                    ad=ad.getSigAdyacente();
                }
            }
            
        }
     
    }
    
 

    public boolean puedePasar(Object hA,Object hD, int pesoAct){
        NodoVert o=ubicarVertice(hA,this.inicio);
        boolean exito=false, cortar=false;
        if (o!=null){
            NodoAdy ad=o.getPrimerAdy();
            while (!exito && !cortar && ad!=null){
                //si encuentra el vertice 
                if (ad.getVertice().getElem().equals(hD)){
                    //y si el peso es suficiente 
                    if (ad.getEtiqueta()<=pesoAct){
                        exito=true;
                    }
                    //sino deja de recorrer
                    else{
                        cortar=true;
                    }
                }
                ad=ad.getSigAdyacente();
            }
        }
        return exito;
        
    }
    public boolean cambiarPeso(Object origen, Object destino, int nuevoPeso){
    boolean encontrado=false;
    NodoVert o=ubicarVertice(origen,this.inicio);
    if (o!=null){
        NodoAdy ad=o.getPrimerAdy();
        while (!encontrado && ad!=null){
            if (ad.getVertice().getElem().equals(destino)){
                ad.setEtiqueta(nuevoPeso);
                encontrado=true;
            }
            ad=ad.getSigAdyacente();
        }

    }
    return encontrado;
    }

}
