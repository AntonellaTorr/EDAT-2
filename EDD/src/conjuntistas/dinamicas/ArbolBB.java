/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;
import conjuntistas.dinamicas.NodoArbol;

/**
 *
 * @author Anto
 */
public class ArbolBB {
    private NodoArbol raiz;
    public ArbolBB (){
        this.raiz=null;
    }
    public boolean insertar(Comparable elem){
        return insertarAux(this.raiz, elem);
    }
    private boolean insertarAux(NodoArbol nodo, Comparable elem){
        boolean exito=false;
        if (nodo!=null){
            int resultado=elem.compareTo(nodo.getElem());
            //si el elemento es mas grande que el nodo se va hacia la derecha
            if (resultado>0){
                //si el nodo no tiene elemento en el lado derecho lo inserta sino llama recursivamente
                if (nodo.getDerecho()==null){
                    nodo.setDerecho(new NodoArbol (elem, null,null));
                    exito=true;
                }
                else{
                    exito=insertarAux(nodo.getDerecho(),elem);
                }
            }
            //si el elemento es menor el nodo se va hacia el lado izquierdo
            else{
                if (resultado<0){
                    //si este no tiene hijo izquierdo insertar el nuevo elemento
                    if (nodo.getIzquierdo()==null){
                        nodo.setIzquierdo(new NodoArbol (elem, null,null));
                        exito=true;
                    }
                    else{
                        //si tiene llama recursivamente con el 
                         exito=insertarAux(nodo.getIzquierdo(), elem);
                    }

                }
            }
        
        }
        return exito;
    }
    public void pertenece (Comparable elem){
        
    }
}
