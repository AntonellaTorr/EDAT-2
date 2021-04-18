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
public class Pila {
    //atributo de la clase
    private Nodo tope;
    
    //constructor
    public Pila() {
        this.tope = null;
    }
    
    public boolean apilar(Object elem) {
        //Crea el nuevo nodo con el objeto que recibe como parametro
        Nodo nuevo = new Nodo(elem, this.tope);
        this.tope = nuevo;
        //como siempre se pueden seguir apilando elementos va a devolver true
        return true;
    }
    public boolean desapilar() {
        boolean exitoDesapilar = false;
        //si la pila no esta vacia va a desapilar
        if (!this.esVacia()) {
            //consigo el enlace al nodo que ahora quedara de tope
            this.tope=(this.tope.getEnlace());
            exitoDesapilar = true;
        } 
        return exitoDesapilar;
    }
    public Object obtenerTope (){
        Object tope = null;
        //si la pila no esta vacia retorna el elemento en el tope
        if (!this.esVacia()){
            tope=this.tope.getElem();
        }
        return tope;
    }
    public boolean esVacia (){
        //si el tope es nulo entonces la pila esta vacia
        return this.tope==null;
    }
    public void vaciar (){
        //para vaciarla ponemos el enlace en nulo asi ya no apunta a ningun nodo
        this.tope=null;
    }
    @Override
    public Pila clone (){
        //Creo la pila clon vacia
        Pila pilaClon= new Pila();
        //Si la pila original no esta vacia comienzo a clonarla
        if (!this.esVacia()){
            //Nodo para mi pila clon
            Nodo aux2=new Nodo (this.tope.getElem(),null);
            //Al tope de la pila clon le asigno el nodo que acabo de crear
            pilaClon.tope=aux2;
            //Obtengo el siguiente nodo de la pila
            Nodo aux1=this.tope.getEnlace();
            //Mientras que la pila tengo algun nodo mas 
            while (aux1!=null){
               //De nuevo creo el nodo para poner detras del tope de la pila clon
               Nodo aux3=new Nodo(aux1.getElem(),null);
               //al nodo que estaba en el tope le pongo la referencia al nuevo nodo que cree
               aux2.setEnlace(aux3);
               //me muevo en la pila clon
               aux2=aux3;
               //sigo avanzando en la pila original 
               aux1=aux1.getEnlace();
            }
        }
        return pilaClon;
    }
    @Override
    public String toString(){
        String cadena="";
        //si la pila esta vacia retornamos el mensaje mencionado
        if (this.tope==null){
            cadena="Pila vacia";
        }
        else{
            Nodo aux=this.tope;
            cadena="[";
            //mientras que sigamos teniendo elementos en la pila
            while (aux!=null){
                //obtenemos los elementos de la pila, los convertimos a String y los concatenemos en nuestra cadena
                cadena+=aux.getElem().toString();
                //avanzamos al nodo siguiente
                aux=aux.getEnlace();
                //mientras la pila tenga mas de 1 elemento ponemos la coma para separar los elementos
                if (aux!=null){
                    cadena+=",";
                }
            }
            cadena+="]";
        }
        return cadena;             
    }
    
    
   
    
    
}
