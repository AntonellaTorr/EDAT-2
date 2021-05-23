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
    Comparable elem;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;
    
   public NodoAVL (Comparable elem, NodoAVL izquierdo, NodoAVL derecho){
       this.elem=elem;
       this.izquierdo=izquierdo;
       this.derecho=derecho;
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
       return izquierdo;
   }
}
