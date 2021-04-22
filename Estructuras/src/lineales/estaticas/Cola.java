/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author Anto
 */
public class Cola {
    private Object [] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO=10;
    
    public Cola (){
        this.arreglo=new Object [this.TAMANIO];
        this.frente=0;
        this.fin=0;
    }
    public boolean poner (Object elem){
        boolean exito=false;
        if (((this.fin+1)%this.TAMANIO) != this.frente){
            this.arreglo[this.fin]=elem;
            this.fin=(this.fin+1)%this.TAMANIO;
            exito=true;
        }
        return exito;
    }
    public boolean sacar(){
        boolean exito=false;
        if (this.fin!=this.frente){
            this.arreglo[this.frente]=null;
            this.frente=(this.frente+1)%this.TAMANIO;
            exito=true;
        }
        return exito;
    }
    public boolean esVacia(){
        return (this.frente==this.fin);
    }
    public Object obtenerFrente(){
        Object frente=null;
        if (this.frente!=this.fin){
            frente= this.arreglo[this.frente];
        }
        return frente;
    }
    public void vaciar (){
        for (int i=this.frente;i<this.fin+1;i++){
            this.arreglo[i]=null;
        }
        this.fin=0;
        this.frente=0;
    }
    public Cola clone (){
        //creo la cola vacia
        Cola colaClon= new Cola ();
        //seteo los valores de frente y fin con los mismo de la pila original
        colaClon.frente=this.frente;
        colaClon.fin=this.fin;
        colaClon.arreglo=this.arreglo.clone();
        return colaClon;
    }
   
    public String toString (){
        String cadena="La cola vacia";
        int i=this.frente;
        if (!this.esVacia()){
            cadena="[";
            while (i!=this.fin){
                cadena+=this.arreglo[i].toString();
                i=(i+1)% this.TAMANIO;
                if (i!=this.fin){
                    cadena+=" ";
                }
            }
            cadena+="]";
        }
        return cadena;
    }
    
    
    
    
    
    
}
