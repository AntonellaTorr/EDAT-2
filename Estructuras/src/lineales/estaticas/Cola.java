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
        boolean exito;
        if (((this.fin+1)%this.TAMANIO) == this.frente){
            exito=false;
        }
        else{
            this.arreglo[this.fin]=elem;
            this.fin=(this.fin+1)%this.TAMANIO;
            exito=true;
        }
        return exito;
    }
    public boolean sacar(){
        boolean exito;
        if (this.esVacia()){
            exito=false;
        }
        else{
            this.arreglo[this.frente]=null;
            this.frente=(this.frente+1)%this.TAMANIO;
            exito=true;
        }
        return exito;
    }
    public boolean esVacia(){
        return frente==fin;
    }
    public Object obtenerFrente(){
        if (!this.esVacia()){
            return this.arreglo[this.frente];
        }
        else{
            return null;
        }
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
        int i=this.frente;
        while (i!=this.fin){
            colaClon.arreglo[i]=this.arreglo[i];
             i=(i+1)%this.TAMANIO;
        }
        return colaClon;
    }
   
    public String toString (){
        String cadena="";
        int i=this.frente;
        if (!this.esVacia()){
            cadena="[";
            while (i!=this.fin){
                cadena+=this.arreglo[i].toString()+ " ";
                i=(i+1)% this.TAMANIO;
            }
            cadena+="]";
        }
        else{
            cadena="La cola esta vacia";
        }
        
        return cadena;
    }
    
    
    
    
    
    
}
