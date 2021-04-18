package test;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;



/**
 *
 * @author Anto
 * 
 */


public class TestingDinamicas {

    
      public static void main(String[] arg) {
        Lista lista1= new Lista ();
        
        lista1.insertar(9,1);
        lista1.insertar(6,2);
        lista1.insertar(5,3);
        lista1.insertar(0,4);
        lista1.insertar(9,5);
        lista1.insertar(5,6);
        lista1.insertar(0,7);
//        lista1.insertar('E',7);
//        lista1.insertar('F',8);
        
        System.out.println (lista1.toString());
        System.out.println (comprobar(lista1));
    }
    public void pruebaLista (){
        
    }
    public static Lista concatenarLista (Lista lista1, Lista lista2){
        //este metodo concatena 2 listas y devuelve una nueva lista con los elementos concatenados
        //creo la nueva lista
        Lista nuevaLista= new Lista() ;
        //inserto los elementos de la primer lista
        nuevaLista=lista1.clone();       
        int i=1,longitudLista2=lista2.longitud(), pos=lista1.longitud()+1;
        //mientras resten elementos por insertarse en la lista vamos a seguir
        //se va a iterar la misma cantidad de veces que la misma cantidad de los elementos 
        //posicionamos los nuevos elementos en la lista en pos, una variable que nos da el valor de el ultimo elemento posicionado en la lista 
        while (i<=longitudLista2){
            //insertamos en la lista nueva los elementos de la 2 lista
            nuevaLista.insertar(lista2.recuperar(i), pos);
            i++;
            pos++;
        }
        return nuevaLista;
    }


 public static boolean comprobar(Lista lista){
        Cola colaAux=new Cola ();
        Pila pilaAux= new Pila ();
        int i=1, elem, longitud=lista.longitud();
        boolean formato=false;
        //recupero el primer elemento
        elem=(int)lista.recuperar(i);
        //mientras no encuentro un 0 y no me paso de la longitud de la lista pongo en la cola y en la pila
        while ( (elem!=0) && (i<longitud) ){
            colaAux.poner(elem);
            pilaAux.apilar(elem);
            i++;
            //ponerle un if
            if(i<=longitud){
                elem=(int)lista.recuperar(i);
            }
        }
        //si el elemento es igual a 0 entonces sigo para comparar los elementos de la lista con los de la cola
        if (elem==0){
           i++;
           while ( (i<=longitud) && (!colaAux.esVacia()) && (colaAux.obtenerFrente()==lista.recuperar(i)) ){
               colaAux.sacar();
               i++;
           }
           if ((i<= longitud) && ((int)lista.recuperar(i)==0)){
               i++;
                while ((i<=longitud) && (!pilaAux.esVacia()) && (pilaAux.obtenerTope()== lista.recuperar(i)) ){
                    pilaAux.desapilar();
                    i++;
                }
                if ((i>longitud) && pilaAux.esVacia()){
                    formato=true;
                }
                               
           }
                    
        }
        return formato; 
 }    
  
    public static Lista invertir (Lista lista1){
        Lista listaInvertida= new Lista ();
        int longitud=lista1.longitud(),i=1,pos;
        pos=longitud;
        while (i<=longitud){
            listaInvertida.insertar(lista1.recuperar(pos), i);
            i++;
            pos--;
        }
        return listaInvertida;
    }
    public static Lista intercalar (Lista lista1, Lista lista2){
        int m, e, i=1,p=1,d=1;
        m=lista1.longitud();
        e=lista2.longitud();
        Lista listaNueva= new Lista ();
        //una vez que terminamos de recorrer ambas listas recien ahi terminamos de iterar
        while (i<=m || p<=e){
            //mientras que i sea menor a la longitud de la primer lista posicionamos sus elementos en la lista nueva
            if (i<=m){
                listaNueva.insertar(lista1.recuperar(i), d);
                d++;
            }
            //mientras que p sea menor a la longitud
            if (p<=e){
                listaNueva.insertar(lista2.recuperar(p), d);
                d++;
               
            }
            i++;
            p++;
        }
        return listaNueva;
    }
    public static int contar (Object elem, Lista lista){
        //este metodo cuenta la cantidad de aparaciones de un elemento determinado en la lista, ambos son recibidos por parametro
       int longitud=lista.longitud(), i=1, cantRep=0;
       //mientras que no me pase de la longitud de la cola sigo contando la cantidad de elementos 
       while (i<=longitud){
           //si el elemento en la posicion i es igual al que fue pasado por parametro aumento la cantidad de repeticiones
           if (lista.recuperar(i).equals(elem)){
               cantRep++;
           }
           i++;
       }
       return cantRep;
    }
    public static int contarRecursivo (Object elem, Lista lista,  int i, int longitud){
        //este metodo cuenta la cantidad de apariciones de un elemento en una lista de forma recursiva 
        int cantRep=0;
        //caso base llegue al ultimo elemento
        if (i==longitud){
           //si es igual al objeto que yo quiero contar las repiticiones a cantRep le asigno 1
           if (lista.recuperar(i).equals(elem)){
               cantRep=1;
           }
           //sino 0
           else{
               cantRep=0;
           }
        }
        //caso recursivo
        else{
            // si el elemento que recupere de la lista es igual al qe yo quiero contar las rep entonces aumento cantRep y llamo recursivamente
            if (lista.recuperar(i).equals(elem)){
               cantRep=1+contarRecursivo(elem,lista,(i+1), longitud);
           }
            //sino solo llamo recursivamente
            else{
                 cantRep=contarRecursivo (elem, lista, (i+1), longitud);
            }
        }
        return cantRep;
    }
    public static boolean esCapicua (Lista lista){
        //este metodo verifica si los elementos de la lista son capicua
        boolean esCapicua=true;
        int ultimaPos=lista.longitud(), i=1, cantIte;
        cantIte=(ultimaPos/2);
        while (i<=cantIte){
            if (!lista.recuperar(i).equals(lista.recuperar(ultimaPos))){
                esCapicua=false;
            }
            i++;
            ultimaPos--;
        }
                
        return esCapicua;
    }
    public static Lista mixLineales2 (Lista lista){
        Lista listaNueva=new Lista ();
        Cola colaAux= new Cola ();
        Pila pilaAux= new Pila ();
        int longitud=lista.longitud(), i=1,m=1;
        char caracter;
        while(i<=longitud){
            caracter=(char)lista.recuperar(i);
            while (caracter != '*' && i<=longitud){
                colaAux.poner(caracter);
                pilaAux.apilar(caracter);
                listaNueva.insertar(caracter, m);
                i++;
                m++;
                if (i<=longitud){
                   caracter=(char)lista.recuperar(i); 
                }
                
            }
            //pongo en la lista los elementos en el orden inverso, esto se logra desapilando la pila
            while (!pilaAux.esVacia()){
                listaNueva.insertar(pilaAux.obtenerTope(), m);
                pilaAux.desapilar();
                m++;
            }
            //pongo en la lista los elementos en el orden original 
            while (!colaAux.esVacia()){
                listaNueva.insertar(colaAux.obtenerFrente(), m);
                colaAux.sacar();
                m++;
            }
            // si ultimo caracter almacenado fue un asterisco entonces lo posiciono en la lista nuevo
            if (caracter=='*'){
                listaNueva.insertar(caracter, m);
                m++;
            }
            i++;
        }
        return listaNueva;
    }     
     /* public static void testIntLista (){
        Lista lista1=new Lista  ();
        
        int n=1,elem, pos;
        while (n>0 && n<9){
            menu ();
            n=TecladoIn.readLineInt();
            switch (n){
            case 1:
                System.out.println ("Ingrese el nro que desea poner en la lista");
                elem=TecladoIn.readLineInt();
                System.out.println ("Ingrese la posicion en la que desea poner el nro en la lista");
                pos=TecladoIn.readLineInt();
                if(lista1.insertar(elem,pos)){
                    System.out.println ("El nuevo elemento se ha posicionado con exito");
                }
                else{
                    System.out.println ("La posicion ingresada no es valida");
                }
            break;
            case 2:
                System.out.println ("Ingrese la posicion del elemento que quiere eliminar");
                pos=TecladoIn.readLineInt();
                if (lista1.eliminar(pos)){
                    System.out.println ("Se ha eliminado con exito");
                }
                else{
                    System.out.println ("La posicion ingresada no es valida");
                }
            break;
            case 3:
                //Para pila de enteros
                System.out.println ("Ingrese la posicion de la cual desea recuperar un elemento");
                pos= TecladoIn.readLineInt();
                if(lista1.longitud()>=pos){  
                    System.out.println ("El elemento en la posicion " +pos+ "es: "+(int)lista1.recuperar(pos));
                }
                else{
                    System.out.println ("La posicion ingresada no es valida");
                }
            break;
            case 4:
                 if (lista1.esVacia()){
                     System.out.println ("La lista esta vacia");
                 }
                 else{
                     System.out.println ("La lisa no esta vacia");
                 }
            break;
            case 5:
               lista1.vaciar();
            break;
            case 6:
                Lista lista2= new Lista ();
                lista2 =lista1.clone();
                System.out.println (lista2.toString());
            break;
            case 7:
                 System.out.println (lista1.toString());
            break;
            case 8:
                 System.out.println ("Ingrese el elemento que desea localizar");
                 elem= TecladoIn.readLineInt();
                 if (lista1.localizar(elem)==-1){
                     System.out.println ("No se encontro el elemento en la lista");
                 }
                 else{
                      System.out.println ("El elemento se encuentra en la posicion:" +lista1.localizar(elem));
                 }
            break;
                 

            }
        }
    }
*/
      public static void menu (){
        System.out.println ("Seleccione con desea hacer con la cola");
        System.out.println ("1-Poner un elemento");
        System.out.println ("2-Sacar un elemento");
        System.out.println ("3-Recuperar un elemento");
        System.out.println ("4-Averiguar si es vacia");
        System.out.println ("5-Vaciarla");
        System.out.println ("6-Clonarla a otra cola");
        System.out.println ("7-Convertir a un string/cadena de texto sus elementos");
        System.out.println ("8-Localizar un elemento");
        
    }
      
    public static Cola mixLineales (Cola c){
        //creo una cola clone de la recibida por parametro asi no se destruye la cola original
        Cola c1= c.clone();
        Cola cola1= new Cola ();
        Pila pilaAux= new Pila ();
        while (!c1.esVacia()){
            while ((!c1.esVacia())&& ((char)c1.obtenerFrente() != '$')){
                //almaceno en la cola nueva los caracteres
                //almaceno en la pila los caracteres
                cola1.poner(c1.obtenerFrente());
                pilaAux.apilar(c1.obtenerFrente());
                //quito los caracteres que ya utilice 
                c1.sacar();
            }
            
            //ahora para lograr la cadena inversa desapilo los elementos de pila en la cola nueva
            //y a la vez desapilo la pila
            while (!pilaAux.esVacia()){
                //posiciono los elementos en la cola en orden inverso
                cola1.poner(pilaAux.obtenerTope());
                pilaAux.desapilar();
            }
            //pongo el signo $ cuando corresponda
            if (!c1.esVacia()) {
                cola1.poner(c1.obtenerFrente());
                c1.sacar();
            }
           
            
        }
      return cola1;
    }
    
   
    public static void menuInt (){
        System.out.println ("Seleccione con desea hacer con la cola");
        System.out.println ("1-Poner un elemento");
        System.out.println ("2-Sacar un elemento");
        System.out.println ("3-Obtener tope");
        System.out.println ("4-Averiguar si es vacia");
        System.out.println ("5-Vaciarla");
        System.out.println ("6-Clonarla a otra cola");
        System.out.println ("7-Convertir a un string/cadena de texto sus elementos");
    }

    public static void testInt (){
        Cola cola1=new Cola ();
        int n=1,elem;
        Object elemento;
        while (n>0 && n<9){
            menuInt ();
            n=TecladoIn.readLineInt();
            switch (n){
            case 1:
                System.out.println ("Ingrese el nro que desea poner en la pila");
                elem=TecladoIn.readLineInt();
                if (cola1.poner(elem)){
                    System.out.println ("El nuevo elemento se ha posicionado con exito");
                }
                else{
                    System.out.println ("La cola ya estaba completa. No fue posible posicionar el elemento");
                }
            break;
            case 2:
                if (cola1.sacar()){
                    System.out.println ("Se ha sacado con exito");
                }
                else{
                    System.out.println ("La cola esta vacia, no se pueden sacar mas elementos");
                }
            break;
            case 3:
                //Para pila de enteros
                elemento=(cola1.obtenerFrente());
                if (elemento==null){
                    System.out.println ("La cola esta vacia");
                }
                else{
                    System.out.println ("El frente de la cola es: "+(int)elemento);
                }
                
            break;
            case 4:
                 if (cola1.esVacia()){
                     System.out.println ("La cola esta vacia");
                 }
                 else{
                     System.out.println ("La cola no esta vacia");
                 }
            break;
            case 5:
                
               cola1.vaciar();
            break;
            case 6:
                Cola cola2= new Cola();
                cola2=cola1.clone();
                System.out.println (cola2.toString());
            break;
            case 7:
                 System.out.println (cola1.toString());
            break;
                
           
         }
        }
    }
   
//     public static void testFecha (){
//        Cola cola1=new Cola ();
//        Fecha fecha1;
//        int n=1, dia, mes,año;
//        while (n>0 && n<9){
//            menu ();
//            n=TecladoIn.readLineInt();
//            switch (n){
//            case 1:
//                System.out.println ("Ingrese el dia del año que desea poner en la pila");
//                dia=TecladoIn.readLineInt();
//                System.out.println ("Ingrese el mes del año que desea poner en la pila");
//                mes=TecladoIn.readLineInt();
//                System.out.println ("Ingrese el nro de año que desea poner en la pila");
//                año=TecladoIn.readLineInt();
//                fecha1=new Fecha(dia,mes,año);
//                if (cola1.poner(fecha1)){
//                    System.out.println ("El nuevo elemento se ha posicionado con exito");
//                }
//                else{
//                    System.out.println ("La cola ya estaba completa. No fue posible posicionar el elemento");
//                }
//            break;
//            case 2:
//                if (cola1.sacar()){
//                    System.out.println ("Se ha sacado con exito");
//                }
//                else{
//                    System.out.println ("La cola esta vacia, no se pueden sacar mas elementos");
//                }
//            break;
//            case 3:
//                //Para pila de enteros
//                System.out.println ("El frente de la cola es: "+(Fecha)cola1.obtenerFrente());
//            break;
//            case 4:
//                 if (cola1.esVacia()){
//                     System.out.println ("La cola esta vacia");
//                 }
//                 else{
//                     System.out.println ("La cola no esta vacia");
//                 }
//            break;
//            case 5:
//                
//               cola1.vaciar();
//            break;
//            case 6:
//                Cola cola2= new Cola  ();
//                cola2=cola1.clone();
//                System.out.println (cola2.toString());
//            break;
//            case 7:
//                 System.out.println (cola1.toString());
//            break;
//
//           
//         }
//        }
//    }
     
 
}