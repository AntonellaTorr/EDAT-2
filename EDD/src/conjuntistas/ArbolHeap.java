/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

/**
 *
 * @author Anto
 */
//insertar y eliminar son de orden log n, cima tiene 0(1)
public class ArbolHeap {

    private int TAMANIO = 15;
    private Comparable[] heap;
    private int ultimo;

    //forma de parar lo subo a la raiz momentaneamente intercambiandolo por el menor de sus hijos
    public ArbolHeap() {
        this.heap = new Comparable[this.TAMANIO];
        this.ultimo = 0;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (this.ultimo < this.TAMANIO - 1) {
            this.heap[this.ultimo + 1] = elem;
            hacerSubir(this.ultimo+1);
            this.ultimo++;
            exito = true;
        }
        return exito;
    }
    private void hacerSubir(int posHijo){
        int posPadre=posHijo/2;
        boolean salir=false;
        while (!salir){
            if (posPadre>0){
                Comparable temp = this.heap[posPadre];
                //si el hijo es menor al padre hay que intercambiar
                if (this.heap[posHijo].compareTo(this.heap[posPadre])<0){
                    this.heap[posPadre]=this.heap[posHijo];
                    this.heap[posHijo]=temp;
                    posHijo=posPadre;
                    posPadre=posHijo/2;
                    
                }
                //si el hijo es menor al padre o si llegamos a la raiz termina 
               if (posPadre>0){
                    if (this.heap[posPadre].compareTo(this.heap[posHijo])<0 || posHijo==1){
                    salir=true;
                    }
                }
               else{
                   salir=true;
               }
                   
            }
            else{
                salir=true;
            }
        }
    }
    public boolean eliminarCima() {
        boolean exito=false;
        //si el arbol no esta vacio 
        if(this.ultimo!=0){
            this.heap[1]=this.heap[ultimo];
            this.ultimo--;
            hacerBajar(1);
            exito=true;
        }
        return exito;
    }
 
    private void hacerBajar(int posPadre) {
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;
        while (!salir) {
            posH = posPadre * 2;
            if (posH <= this.ultimo) {
                if (posH < this.ultimo) {
                    if (this.heap[posH + 1].compareTo(temp) < 0) {
                        posH++;
                    }
                }    
                if (this.heap[posH].compareTo(temp) < 0) {
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp;
                    posPadre = posH;
                } else {
                    salir = true;
                }
                
            } else {
                salir = true;
            }
        }
    }    
        
    public Comparable recuperarCima(){
        Comparable cima=null;
        if (this.ultimo!=0){
            cima=this.heap[1];
        }
        return cima;
    }
    public boolean esVacio(){
        return (this.ultimo==0);
    }
    public String toString (){
        String cadena="";
        int i=1;
        if(this.ultimo!=0){
            while (i<=this.ultimo){
                cadena+="Padre: "+this.heap[i];
                if (i*2<=this.ultimo){
                    cadena+=" HI: "+this.heap[i*2];
                }
                else{
                    cadena+=" HI:-";
                }
                if ((i*2)+1<=this.ultimo){
                    cadena+=" HD: "+this.heap[(i*2)+1]+"\n";
                }
                else{
                    cadena+=" HD:-\n";
                }
                
                i++;
            }
        }
        return cadena;
    }
     public ArbolHeap clone() {
        //este metodo devuelve un clon del arbolHeap
        //creamos el arbol vacio
        ArbolHeap arbolClone= new ArbolHeap();
        //clonamos el arbol
        arbolClone.heap = this.heap.clone();
        return arbolClone;
    }
}
