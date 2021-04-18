/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;



/**
 *
 * @author Anto
 */
public class Cola {
    //atributos de la clase
    private Nodo frente;
    private Nodo fin;
    
    //constructor
    public Cola (){
        this.frente=null;
        this.fin=null;
    }
    //metodos basicos
    public boolean poner(Object elem){
        //Este metodo pone un elemento en la cola
        Nodo nuevo= new Nodo(elem,null);
        //si la cola esta vacia
        if (this.frente==null){
            //hay que hacer que fin y frente apunten al nodo nuevo
            this.frente=nuevo; 
        }
        else{
            //hago el enlace del ultimo nodo con el nuevo
            this.fin.setEnlace(nuevo);
        }
        this.fin=nuevo; 
        //como la cola nunca se llena retornamos true
        return true;
    }
    public boolean sacar (){
        //este metodo quita un elemento de la cola
        boolean exito=true;
        //si la cola esta vacia no podemos quitar ningun elemento
        if (this.frente==null){
            exito=false;
        }
        else{
            //hacemos que el frente apunte al segundo elemento
            this.frente=this.frente.getEnlace();
            //en el caso e que la cola no tuviera mas elemento luego de sacar el primero seteamos el frente tambien
            if (this.frente==null){
                 this.fin=null;
            }
        }
        return exito;
    }
    public Object obtenerFrente (){
        //este metodo devuelve el objeto que se encuentra en el comienzo de la cola
        Object frente;
        if(this.esVacia()){
            //la cola no tiene ningun elemento
            frente= null;
        }
        else{
            //obtenemos el elemento
            frente=this.frente.getElem();
        }
        return frente;
    }
    public boolean esVacia(){
        //este metodo verifica si la cola esta vacia
        return this.frente==null;
    }
    public void vaciar (){
        //este metodo vacia la cola 
        //verificar si es necesario que el frente tambien apunte a nulo
        this.fin=null;
        this.frente=null;
    }

    public Cola clone (){
        //creo la cola vacia
        Cola clone= new Cola ();
        if (!this.esVacia()){
            //primer nodo auxiliar que va a recorrer los elementos de la cola original
            Nodo aux1=this.frente;
            //nodo para la cola
            Nodo aux2= new Nodo (aux1.getElem(),null);
            //hago que el frente de la cola nueva apunte al primer nodo que cree 
            clone.frente=aux2;
            //avanzo en cola original 
            aux1=aux1.getEnlace();
            //continuo mientras tenga elementos en la cola para clonar
            while (aux1!=null){
                //hago que el ultimo nodo de la cola clone apunte a un nuevo nodo 
                aux2.setEnlace(new Nodo(aux1.getElem(),null));
                //avanzo en la cola clone
                aux2=aux2.getEnlace();
                //avanzo en la cola original 
                aux1=aux1.getEnlace();
                                
            }
            //una vez finalizado todo el proceso de clonar todos los nodos de la cola original
            //hago que el fin de la cola apunte al ultimo nodo
            clone.fin=aux2;
        }
        return clone;
    }
    
    @Override
    public String toString (){
        //este metodo que es a fines de debuggin crea una cadena con todos los elementos de la cola
        String cadena="";
        if (this.esVacia()){
            cadena="Cola vacia";
        }
        else{ 
            cadena="[";
            Nodo aux=this.frente;
            //mientras existan elementos en la cola
            while(aux!=null){
                cadena+= aux.getElem().toString();
                aux=aux.getEnlace();
                if (aux!=null){
                    cadena+=",";
                }
            }
            cadena+="]";
           
        }
        return cadena;
    }
    
   
    
    
    
    
    
}

