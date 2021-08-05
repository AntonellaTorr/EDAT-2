/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;
import java.util.*;

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
        //este metodo devuelve false si el elemento a insertar ya se encuentra en el grafo y true si no es asi
        NodoVert n= ubicarVertice(nuevoVertice);
        boolean exito=false;
        //si no esta lo inserta
        if (n==null){
            this.inicio= new NodoVert(nuevoVertice, this.inicio);
            exito=true;
        }
        return exito;
    }
    private NodoVert ubicarVertice(Object elem){
        //este metodo busca el vertice en la lista  
        NodoVert aux= this.inicio;
        while (aux!=null && !aux.getElem().equals(elem)){
            aux=aux.getSigVertice();
        }
        return aux;
    }
    public boolean eliminarVertice(Object vertice){
        //este metodo devuelve true si el elemento se encontraba en el grafo y false si no lo hacia
        NodoVert aux=this.inicio;
        boolean exito=false;
        //si el vertice a eliminar se encuentra al principio de la lista 
        if (this.inicio.getElem().equals(vertice)){
            //se eliminan todos los arcos que tiene como destino al vertice a eliminar
            eliminarArcos(this.inicio);
            this.inicio=this.inicio.getSigVertice();
            exito=true;
        }
        else{
            //sino busco el vertice
            while (aux!=null && !exito){
                //si el vertice siguiente al que estoy parado tiene el vertice a eliminar le seteo el siguiente al proximo
                if(aux.getSigVertice()!=null && aux.getSigVertice().getElem().equals(vertice)){
                    //se eliminan todos los arcos que tiene como destino al vertice a eliminar
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
        //este metodo elimina todos los arcos que tiene como destino al nodo recibido por parametro
        NodoAdy adyacente=n.getPrimerAdy();
        while(adyacente!=null){
            eliminarArcoAux(adyacente.getVertice(),n);
            adyacente=adyacente.getSigAdyacente();
        }
    }
        public boolean eliminarArco(Object origen, Object destino){
        /*Este metodo devuelve true si ambos elementos existian y habia un arco entre ellos
         false en el caso contrario        
        */
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        boolean exito=false;
        //si existen ambos vertices llama al metodo eliminar
        if (nodoO!=null && nodoD!=null){
           exito= eliminarArcoAux(nodoO, nodoD);
           eliminarArcoAux(nodoD, nodoO);
        }
        return exito;
    }
    private boolean eliminarArcoAux(NodoVert nodoO,NodoVert nodoD){
        /*este metodo elimina el arco entre el nodoO y el nodoD devuelve true si lo encuentra
        false en el caso contrario*/
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
     public boolean insertarArco (Object origen, Object destino, int etiqueta){
        /*Este metodo inserta un arco entre los dos elementos recibidos por parametro
          Devuelve true si ambos vertices existen y si ya no existia un arco entre ellos
          Devuele false en el caso contrario
         */
        boolean exito=false;
        //se ubican los vertices de origen y destino
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        //si ambos vertices se encuentran en el grafo
        if (!existeArco(origen,destino)&& nodoO!=null && nodoD!=null){
            nodoO.setPrimerAdy(new NodoAdy (nodoD, nodoO.getPrimerAdy(),etiqueta));
            nodoD.setPrimerAdy(new NodoAdy (nodoO, nodoD.getPrimerAdy(),etiqueta));
            exito=true;
        }
        return exito;
    }
    public boolean existeVertice(Object verticeBuscado){
        /*retorna true si existe el vertice en el grafo false en el caso contrario*/
        return ubicarVertice(verticeBuscado)!=null;
    }
 

    
    public boolean existeArco(Object origen, Object destino){
        /*este metodo retorna true si existe un arco entre los dos elementos ingresados
        devuelve false en el caso en que no exista alguno de los dos elementos en el grafo
        o no exista el arco entre ellos 
        */
        NodoVert nodoO=ubicarVertice(origen);
        NodoVert nodoD=ubicarVertice(destino);
        return verificarArco(nodoO, nodoD);
        
    }
    private boolean verificarArco(NodoVert nodoO, NodoVert nodoD){
        /*este metodo busca el arco entre los nodos recibidos por parametro*/
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
        /*este metodo retorna true si existe un camino entre ambos elementos ingresados por parametro
        devuelve false en el caso en que alguno de los dos elementos no se encuentre en el grafo o si 
        no existe el camino 
        */
        //busca el nodo origen de el camino
        NodoVert o=ubicarVertice(origen);
        HashSet visitados= new HashSet ();
        return buscarCamino(o, destino,visitados);
    }

    private boolean buscarCamino(NodoVert n, Object destino,HashSet visitados){
        /*metodo auxiliar recursivo que busca la existencia de un camino entre el elemento que contiene el nodo n y el destino
        retorna true en el caso de que lo encuentre y false si no lo hace*/
        boolean encontrado=false;
        if (n!=null){
            visitados.add(n.getElem());
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
        /*este metodo lista en profundidad los vertices del grafo*/
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
        /*metodo auxiliar recursivo que va listando en profundidad todos los vertices del grafo*/
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
        /*este metodo devuelve una lista con el camino mas largo en el grafo
        retorna una lista vacia en el caso en que alguno de los dos elementos ingresado por parametro no exista
        o si no existe un camino entre ellos*/
        NodoVert n=ubicarVertice(origen);
        Lista caminoMax= new Lista();
        Lista visitados= new Lista();
        return  caminoMasLargoAux(n, visitados, destino, 0, caminoMax);
        
    }
     private Lista caminoMasLargoAux(NodoVert n, Lista visitados, Object destino, int longitudMayor, Lista caminoMax){
        /*este metodo auxiliar recursivo busca el camino mas largo entre el elemento que tiene n y el de destino
        retorna el caminoMaximo encontrado*/
        int longitudActual;
        if (n!=null){
            longitudActual=visitados.longitud();
            visitados.insertar(n.getElem(),longitudActual+1);
            //si se encontro un camino
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
        /*este metodo devuelve una lista con el camino mas corto en el grafo
        retorna una lista vacia en el caso en que alguno de los dos elementos ingresado por parametro no exista
        o si no existe un camino entre ellos*/
        NodoVert n=ubicarVertice(origen);
        Lista caminoMin= new Lista();
        Lista visitados= new Lista();
        return  caminoMasCortoAux(n, visitados, destino, 0, caminoMin);
        
    }
     private Lista caminoMasCortoAux(NodoVert n, Lista visitados, Object destino, int longitudMin, Lista caminoMin){
         /*este metodo auxiliar recursivo busca el camino mas corto entre el elemento que tiene n y el de destino
        retorna el caminoMin encontrado*/
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
                        //si todavia no se ha encontrado algun camino o si la longitudActual no supera la de el camino minimo encontrado entonces llamada
                        if (longitudMin==0 || longitudActual+1<longitudMin){
                            caminoMin=caminoMasCortoAux(v.getVertice(), visitados,destino,longitudMin,caminoMin);
                            longitudMin=caminoMin.longitud();
                            //se reestablece la lista a lo que era antes de llamar recursivamente 
                            visitados.eliminar(visitados.longitud());
                        }
                        
                    }
                    v=v.getSigAdyacente();
                }
            }
        }
        return caminoMin;
   
     }
     public Lista listarEnAnchura(){
        /*este devuelve una lista con los vertices del grafo listados en anchura*/
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
        /*este metodo auxiliar recursivo lista en anchura los vertices del grafo*/
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
        /*este metodo devuelve una cadena con los vertices del grafo y sus respectivos nodos adyacentes*/
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
        /*retorna true si no hay vertices el grafo
        false en el caso contrario
        */
        return this.inicio==null;
    }
    public void vaciar(){
        /*vacia el grafo*/
        this.inicio=null;
    }
    @Override
    public Grafo clone(){
        HashMap mapNodos= new HashMap();
        /*este metodo devuelve un grafo, el cual es una copia del original*/
        Grafo clone= new Grafo();
        if (this.inicio!=null){
            //copia el inicio de la lista de vertices
            clone.inicio=new NodoVert(this.inicio.getElem(), null);
            NodoVert c=clone.inicio;
            NodoVert n=this.inicio;
            mapNodos.put(n, c);
            //copia de todos los vertices en la lista
            n=n.getSigVertice();
            while (n!=null){
                c.setSigVertice(new NodoVert (n.getElem(),null));
                c=c.getSigVertice();
                mapNodos.put(n, c);
                n=n.getSigVertice();
               
            }
            c=clone.inicio;
            n=this.inicio;
            //copia de los vertices adyacentes
            while(n!=null){
                copiaNodosAdy(n,c,mapNodos);
                n=n.getSigVertice();
                c=c.getSigVertice();
            }
            
        }
     
        return clone;
    }
    private void copiaNodosAdy (NodoVert n, NodoVert c,HashMap mapNodos ){
        /*este metodo clona los nodos adyacentes del nodo c recibido por parametro*/
        NodoAdy aux=n.getPrimerAdy();
        if (aux!=null){
            //copia el primer nodo adyacente
            c.setPrimerAdy(new NodoAdy ((NodoVert)mapNodos.get(aux.getVertice()),null,aux.getEtiqueta()));
            NodoAdy auxC=c.getPrimerAdy();
            aux=aux.getSigAdyacente();
            //si tiene mas adyacentes los copia
            while (aux!=null){
                auxC.setSigAdyacente(new NodoAdy ((NodoVert)mapNodos.get(aux.getVertice()),null,aux.getEtiqueta()));
                aux=aux.getSigAdyacente();
                auxC=auxC.getSigAdyacente();
            }
        }
    }

    public Lista buscarAdyacentes(Object h){
        /*este metodo almacena en una lista todos los nodos adyacentes al elemento ingresado por parametro, en las posiciones impares,
        y sus respectivas etiquetas en las posiciones pares
        Devuelve una lista vacia en el caso en que el elemento no exista en el grafo
        */
        NodoVert u= ubicarVertice(h);
        Lista adyacentes= new Lista ();
        int pos=1;
        if (u!=null){
            //recorre la lista de adyacentes listandolos 
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
        /*este metodo devuelve true si posible llegar de h a h2 sin superar el peso recibido por parametro
        devuelve false:
        -en el caso en que alguno o los dos elementos no esten el grafo
        -en el caso en que no existan caminos bajo las condiciones dadas */
        NodoVert o=ubicarVertice(h);
        Lista visitados= new Lista ();
        //llamada a metodo auxiliar
        return esPosibleLlegar(o, h2,visitados,peso,0);
    }
    private boolean esPosibleLlegar(NodoVert n, Object destino,Lista visitados,int peso, int pesoTot){
        /*este metodo averigua si es posible llegar de n a destino sin superar el peso recibido por parametro*/
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
        /*este metodo devuelve todos los caminos entre h y h2 sin pasar por h3 y sin superar el peso recibido por parametro
        devuelve la cola vacia en el caso en que no exista h o h2 o en el caso en que no existan caminos bajo las condiciones
        dadas*/
        Cola caminos=new Cola();
        if (!h.equals(h3)){
            //ubicamos el nodo que contiene a h
            NodoVert o=ubicarVertice(h);
            //creacion de listas auxiliares
            Lista visitados= new Lista ();
            sinPasarPor(o, h2,h3,visitados,peso,0,caminos);
        }
        return caminos;
        
    }
    private void sinPasarPor(NodoVert n, Object destino,Object h3,Lista visitados,int peso, int pesoTot,Cola caminos){
        /*este metodo devuelve los caminos entre el elemento que contiene n y destino sin pasar por h3 y sin superar el peso
        recibido por parametro*/
        if (n!=null){
            visitados.insertar(n.getElem(),visitados.longitud()+1);
            //si se encontro un camino lo inserta en la cola
            if(n.getElem().equals(destino)){
               Lista cam=visitados.clone();
               caminos.poner(cam);
            }
            else{
                NodoAdy ad=n.getPrimerAdy();
                while (ad!=null){
                    //si no visitamos al nodo todavia y si no es igual al h3 
                    if(!ad.getVertice().getElem().equals(h3) && visitados.localizar(ad.getVertice().getElem())<0 ){
                            //si para pasar al nodo adyacente no se supera el puntaje maximo llamamos recursivamente
                            if (pesoTot+ad.getEtiqueta()<=peso){
                                 sinPasarPor(ad.getVertice(),destino,h3, visitados,peso,pesoTot+ad.getEtiqueta(),caminos);
                                 visitados.eliminar(visitados.longitud());
                            }
                    }
                    ad=ad.getSigAdyacente();
                }
            }
            
        }
     
    }
    
 

    public boolean puedePasar(Object hA,Object hD, int pesoAct){
        /*este metodo devuelve true si se puede pasar desde hA a h2 con el peso actual
        devuelve false en el caso en que tanto hA como hD no existan en el grafo o en el 
        caso en que el pesoAct sea mayor a la etiqueta*/
        NodoVert o=ubicarVertice(hA);
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
    /*este metodo devuelve true en el caso en que existan los elementos origen y destino y
     exista un arco entre ellos, por lo que se puede cambiar el peso. false en el caso contrario
    */
    boolean encontrado=false;
    NodoVert o=ubicarVertice(origen);
    if (o!=null){
        NodoAdy ad=o.getPrimerAdy();
        while (!encontrado && ad!=null){
            //si se encuentra el destino se cambia el peso
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
