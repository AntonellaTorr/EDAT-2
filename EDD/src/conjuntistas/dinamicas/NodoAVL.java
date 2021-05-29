/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;

/**
 *
 * @author Anto
 */
public class NodoAVL {
    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    
   public NodoAVL (Comparable elem, NodoAVL izquierdo, NodoAVL derecho,int altura){
       this.elem=elem;
       this.izquierdo=izquierdo;
       this.derecho=derecho;
       this.altura=altura;
            
   }
   
   public void setElem(Comparable elem){
       this.elem=elem;
   }
   public void setIzquierdo(NodoAVL izquierdo){
       this.izquierdo=izquierdo;
   }
   public void setDerecho(NodoAVL derecho){
       this.derecho=derecho;
   }
 
   
   public void recalcularAltura(){
       //alturaIz y der por defecto, sino buscandola en el nodo
       int alturaIzq= -1, alturaDer=-1;
       if (this.izquierdo!=null){
           alturaIzq=this.izquierdo.altura;
       }
       if (this.derecho!=null){
           alturaDer=this.derecho.altura;
       }
       this.altura=(Math.max(alturaIzq,alturaDer))+1; 
        
   }
  
   public Comparable getElem(){
       return elem;
   }
   public int getAltura(){
       return altura;
   }
   public NodoAVL getIzquierdo(){
       return izquierdo;
   }
   public NodoAVL getDerecho(){
       return derecho;
   }
}
