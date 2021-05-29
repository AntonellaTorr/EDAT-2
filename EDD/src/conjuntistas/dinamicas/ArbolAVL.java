/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas.dinamicas;
import lineales.dinamicas.Lista;

/**
 *
 * @author Anto
 */
public class ArbolAVL {
    private NodoAVL raiz;
    public ArbolAVL (){
        this.raiz=null;
    }
     public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem, null, null,0);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, elem);
            this.raiz.recalcularAltura();
            chequearBalanceo(this.raiz,null);
            this.raiz.recalcularAltura();
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL nodo, Comparable elem) {
        boolean exito = false;
        int resultado = elem.compareTo(nodo.getElem());
        //si el elemento a insertar es menor al elemento que se encuentra en el nodo bajamos a la izquierda
        if (resultado < 0){
            //si no tiene hijo izquierda inserta el elemento nuevo
            if (nodo.getIzquierdo() == null) {
                nodo.setIzquierdo(new NodoAVL(elem, null, null, 0));
                exito = true;
                nodo.recalcularAltura();
            } else {
                //si tiene llama recursivamente con el 
                exito = insertarAux(nodo.getIzquierdo(), elem);
                nodo.recalcularAltura();
                chequearBalanceo(nodo.getIzquierdo(),nodo);
                nodo.recalcularAltura();
               
            }
        } else {
            if (resultado > 0) {
                //si no tiene hijo derecho inserta el elemento nuevo
                if (nodo.getDerecho() == null) {
                    nodo.setDerecho(new NodoAVL(elem, null, null, 0));
                    exito = true;
                    nodo.recalcularAltura();
                    
                } else {
                    exito = insertarAux(nodo.getDerecho(), elem);
                    nodo.recalcularAltura();
                    chequearBalanceo(nodo.getDerecho(),nodo);
                    nodo.recalcularAltura();
                }
            }

        }
        return exito;
    }
    public boolean eliminar(Comparable elem) {
        boolean exito=eliminarAux(this.raiz, elem, null);
        this.raiz.recalcularAltura();
        chequearBalanceo(this.raiz,null);
        this.raiz.recalcularAltura();
        return exito;
        
    }

     private boolean eliminarAux(NodoAVL nodo, Comparable elem, NodoAVL padre){
        NodoAVL hijo = null;
        boolean encontrado = false;
        if (nodo != null) {
            int res = elem.compareTo(nodo.getElem());
            //si se encontro el elemento que se quiere eliminar verifica que caso le aplica
            if (res == 0) {
                hijo = nodo;
                encontrado = true;
                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                    //es hoja
                    caso1(padre, hijo);                  
                    
                } else {
                    if (hijo.getIzquierdo() != null && hijo.getDerecho() != null) {
                        caso3(hijo);
                    } else {
                        if (hijo.getIzquierdo() != null) {
                            caso2(padre, hijo, hijo.getIzquierdo());
                        } else {
                            caso2(padre, hijo, hijo.getDerecho());
                        }
                    }
                }
            } else {
                //sino llama al lado que corresponde
                if (res < 0) {
                    encontrado = eliminarAux(nodo.getIzquierdo(), elem, nodo);
                    nodo.recalcularAltura();
                    chequearBalanceo(nodo,padre);
                    nodo.recalcularAltura();
                } else {
                    encontrado = eliminarAux(nodo.getDerecho(), elem, nodo);
                    nodo.recalcularAltura();
                    chequearBalanceo(nodo,padre);
                    nodo.recalcularAltura();

                }

            }
           
        }
         return encontrado;

    }
    private void caso1(NodoAVL padre, NodoAVL hijo) {
        if (padre == null) {
            this.raiz = null;
        } else {
            if (padre.getIzquierdo() != null && padre.getIzquierdo().getElem().compareTo(hijo.getElem()) == 0) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
            
        }
    }

    private void caso2(NodoAVL padre, NodoAVL hijo, NodoAVL nieto) {

        //nos fijamos si el elemento a eliminar es hijo izquierdo o derecho
        if (padre.getIzquierdo() != null && padre.getIzquierdo().getElem().equals(hijo.getElem())) {
            //procedemos a eliminar
            padre.setIzquierdo(nieto);
        } else {
            padre.setDerecho(nieto);
        }
    }

    private void caso3(NodoAVL hijo) {
        NodoAVL nuevoNodo = encontrarCandidato(hijo.getIzquierdo(), hijo);
        hijo.setElem(nuevoNodo.getElem());
    }

    private NodoAVL encontrarCandidato(NodoAVL hijoIzquierdo, NodoAVL padre) {
        //busco el mayor elemento del lado izquierdo der arbol
        NodoAVL candidato = null;
        if (hijoIzquierdo.getDerecho() == null) {
            candidato = hijoIzquierdo;
            if (candidato.getIzquierdo() == null) {
                caso1(padre, candidato);
            } else {
                caso2(padre, candidato, candidato.getIzquierdo());
            }
        } else {
            //si el hijo derecho no es null avanzo
            candidato = encontrarCandidato(hijoIzquierdo.getDerecho(), hijoIzquierdo);
        }
        return candidato;
    }

   
    
  
    private int chequearBalanceo(NodoAVL nodo, NodoAVL padre) {
        int balanceo=-1;
        int altIzq=-1, altDer=-1;
        if (nodo!=null){
            if (nodo.getIzquierdo()!=null){
                altIzq=nodo.getIzquierdo().getAltura();
            }
            if (nodo.getDerecho()!=null){
                altDer=nodo.getDerecho().getAltura();
            }
            balanceo= altIzq-altDer;
            //si esta desbalanceado ya sea a la izquierda o a la derecha llama a rebalancear
            if (balanceo==2 || balanceo==-2){
                rebalancear(balanceo, nodo, padre);
            }   
        }
        return balanceo;
    }
   private void rebalancear (int balanceo,NodoAVL nodo, NodoAVL padre){
       int balanceoHijo;
        //si esta desbalanceado hacia la izquierda verifico el balanceo de su hijo ziq
            if (balanceo == 2) {
                balanceoHijo = chequearBalanceo(nodo.getIzquierdo(),nodo);
                if (balanceoHijo == 0 || balanceoHijo == 1) {
                   if (padre==null){
                       this.raiz = rotarDerecha(nodo); //OK 
                   }
                   else{
                       padre.setDerecho(rotarDerecha(nodo)); //OK
                   }
                   
                } else {
                    if (padre==null){
                       this.raiz= rotacionIzquierdaDerecha(nodo); //OK
                   }
                    else{
                        padre.setIzquierdo(rotacionIzquierdaDerecha(nodo)); //OK
                    }
                }
            } else {
                //si esta desbalanceado hacia la derecha verifico el balanceo de su hijo der
                if (balanceo == -2) {
                    balanceoHijo = chequearBalanceo(nodo.getDerecho(),nodo);
                    if (balanceoHijo == 0 || balanceoHijo == -1) {
                        //hay que setear el nodo padre a la nueva Raiz
                        if (padre==null){
                            this.raiz= rotarIzquierda(nodo); //OK
                        }
                        else{
                            padre.setDerecho(rotarIzquierda(nodo)); //OK
                        }
                        
                    } else {
                        if (padre==null){
                           this.raiz = rotacionDerechaIzquierda(nodo); //OK
                        }
                        else{
                            padre.setDerecho(rotacionDerechaIzquierda(nodo)); //OK
                        }
                    }
                }

            }
    }
    private NodoAVL rotarIzquierda(NodoAVL r){
        //añadir recalcularAltura
        NodoAVL h=r.getDerecho();
        NodoAVL temp=h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    private NodoAVL rotarDerecha(NodoAVL r){
        NodoAVL h=r.getIzquierdo();
        NodoAVL temp=h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        
        h.recalcularAltura();
        r.recalcularAltura();
        return h;
    }
    private NodoAVL rotacionIzquierdaDerecha(NodoAVL r){
        //esta bien que r cambie su hijo Izquierdo por la nueva raiz del subarbol
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo()));
        return rotarDerecha(r);
    }
    private NodoAVL rotacionDerechaIzquierda(NodoAVL r){
        r.setDerecho(rotarDerecha(r.getDerecho()));
        return rotarIzquierda(r);
    }
     public String toString() {
        return concatenar(this.raiz);
    }

    private String concatenar(NodoAVL n) {
        //MOSTRAR ALTURA DEL NODO TAMBIEN 
        //este metodo privado y recursivo devuelve un String con el contenido del arbol/subArbol
        //creamos e inicializamos las cadenas auxiliares
        String cadenaAux = "", cadena = "arbol vacio";
        if (n != null) {
            //si el nodo ingresado no es null
            cadena = "";
            //pondremos el padre primero 
            cadena += "\nNodo padre: " + n.getElem() ;
            //luego pondremos si tiene o no tiene hijos izquierdo o derecho
            if (n.getIzquierdo() != null) {
                cadena += " HI:" + n.getIzquierdo().getElem() + " ";
            } else {
                cadena += " HI:- ";
            }
            
            if (n.getDerecho() != null) {
                cadena += "HD: " + n.getDerecho().getElem()  + " Altura: "+n.getAltura()+ "\n";
            } else {
                cadena += "HD:-" + " Altura: " + n.getAltura() +"\n";
            }
            //si tenia hijo izquierdo iremos repitiendo hasta que ya no hayan mas elementos del lado derecho del arbol 
            if (n.getIzquierdo() != null) {
                cadenaAux = concatenar(n.getIzquierdo());
                cadena += cadenaAux;
            }
            //repetimos el mismo procedimiento para el lado derecho
            if (n.getDerecho() != null) {
                cadenaAux = concatenar(n.getDerecho());
                cadena += cadenaAux;
            }
        }
        return cadena;
    }
    

 
 
}

