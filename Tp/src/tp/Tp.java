/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp;
import Estructuras.MapeoAMuchos;
import Estructuras.TDB;
import Estructuras.TablaDeBusqueda;
import Estructuras.Grafo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import Dominio.Desafio;
import Dominio.Equipo;
import Dominio.Habitacion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


/**
 *
 * @author Anto
 */
public class Tp {
  
    public static void escribirABM(String texto){
         try {
            PrintWriter writer = new PrintWriter("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\ABM.txt", "UTF-8");
            writer.println(texto);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public static void agregarPuerta (String codOrigen, String codDestino, String puntaje, TablaDeBusqueda h, Grafo g){
        int codO= Integer.parseInt(codOrigen);
        int codD=Integer.parseInt(codDestino);
        int pun=Integer.parseInt(puntaje);
        Object hab=h.obtenerDato(codO);
        Object habD=h.obtenerDato(codD);
        g.insertarArco(hab, habD, pun);
    }
    public static void agregarHabitacion (String codigo, String nombre, String planta, String mCuadrados, String tieneSalida,
        TablaDeBusqueda h, Grafo g){
       int codigoH= Integer.parseInt(codigo);
       int plantaH= Integer.parseInt(planta);
       double cantM=Double.parseDouble(mCuadrados);
       boolean tieneS=Boolean.parseBoolean(tieneSalida);
       Habitacion nuevaH= new Habitacion (codigoH, nombre, plantaH, cantM, tieneS);
       h.insertar(codigoH, nuevaH);//PREGUNTAR SI ESTA BIEN HACERLO ASI
       g.insertarVertice(nuevaH);
    }
    public static void agregarDesafio(String codigo, String puntajeAOtorgar, String nombre, String tipo, TablaDeBusqueda d){
       int codigoD= Integer.parseInt(codigo);
       int puntajeO= Integer.parseInt(puntajeAOtorgar);
       Desafio nuevoD= new Desafio (codigoD, puntajeO, nombre, tipo);
       d.insertar(codigo, nuevoD);
       
    }
    public static void agregarEquipo(String nombre, String puntajeExigido, String habitacionActual, TDB e){
        //AÑADIR TRIM
       int puntE= Integer.parseInt(puntajeExigido);
       int habAct= Integer.parseInt(habitacionActual);
       Equipo nuevoE= new Equipo(nombre, puntE, habAct);
       e.insertar(nombre, nuevoE);
    }
    public static String leerTxt (String direccion){
        String texto="";
        try{
            BufferedReader bf= new BufferedReader (new FileReader(direccion));
            String temp="";
            String bfRead;
            while ((bfRead=bf.readLine())!=null){
                temp=temp +bfRead;
            }
            texto=temp;
         } catch (Exception e){System.out.println ("No se encontro el archivo");}
        return texto;
    }

    public static void leerArchivo(String texto, Grafo g, TablaDeBusqueda hab, TablaDeBusqueda desafios,TDB equipos){
        StringTokenizer st = new StringTokenizer(texto, "|");   
        String cad="", cod, abm="", codD;
        while (st.hasMoreTokens()){
            cad=st.nextToken();
            switch (cad){
                case "H": 
                    cod=st.nextToken();
                    agregarHabitacion(cod,st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(), hab, g);
                    abm+="Carga de Habitacion "+cod +"\n";
                break;
                case "D": 
                    cod=st.nextToken();
                    agregarDesafio(cod,st.nextToken(),st.nextToken(),st.nextToken(), desafios);
                    abm+="Carga de Desafio  "+cod +"\n";
                break;
                case "E": 
                    cod=st.nextToken();
                    agregarEquipo(cod,st.nextToken(),st.nextToken(), equipos);
                    abm+="Carga de Equipo  "+cod +"\n";
                break;
                case "P": 
                    cod=st.nextToken();
                    codD=st.nextToken();
                    agregarPuerta(cod,codD,st.nextToken(),hab, g);
                    abm+="Puerta añadida, Origen "+cod +" Destino " +codD+"\n";
                break;
            }
        }
        escribirABM(abm);
       }
    public static void iniciarPrograma(String direccion){
        //creacion de las estructuras que almacenaran la informacion 
        Grafo plano= new Grafo ();
        TablaDeBusqueda infoH= new TablaDeBusqueda ();
        TablaDeBusqueda infoD= new TablaDeBusqueda ();
        TDB infoE= new TDB ();
        MapeoAMuchos desafiosR= new MapeoAMuchos ();//DEBE USARSE AL COMPLETAR DESAFIOS
        String texto= leerTxt(direccion);
        leerArchivo(texto,plano,infoH,infoD,infoE);
    }
    public static void prueba (){
        MapeoAMuchos n= new MapeoAMuchos ();
        n.insertar(1,"ENERO" );
        n.insertar(1,"LUNES" );
        n.insertar(2,"FEBRERO" );
        n.insertar(2,"MARTES" );
        n.insertar(3,"MARZO" );
        n.insertar(3,"MIERCOLES" );
        n.insertar(4,"ABRIL" );
        n.insertar(4,"JUEVES" );
        n.insertar(5,"MAYO" );
        n.insertar(5,"VIERNES" );
        n.insertar(6,"JUNIO" );
        n.insertar(6,"SABADO" );
        n.insertar(7,"JULIO" );
        n.insertar(7,"DOMINGO" );
        n.insertar(8,"AGOSTO" );
        n.insertar(9,"SEPTIMEBRE" );
        n.insertar(10,"OCTUBRE" );
        n.insertar(11,"NOVIEMBRE" );
        n.insertar(12,"DICIEMBRE" );
        TablaDeBusqueda a= new TablaDeBusqueda();
        a.insertar(4, "Antonella");
        a.insertar(7, "Bruno");
        a.insertar(9, "Luis");
        a.insertar(15, "Camila");
        a.insertar(3, "Lucia");
        a.insertar(2, "Claudia");
        a.insertar(1, "Gustavo");
        TDB p= new TDB();
        p.insertar("ABB", "brunoktr");
        p.insertar("ACC", "bantonella");
        p.insertar("CCA", "crozas");
        p.insertar("MMM", "karinarozas");
        p.insertar("NNJ", "takki");
        p.insertar("XXX", "jose123");
        
        System.out.println ("INICIO TESTING MAPEO");
        System.out.println (n.obtenerValores(1).toString());
        System.out.println ("Espera LUNES y Enero");
        System.out.println ("Quita MARTES para dom 2 febrero");
        n.desasociar(2, "MARTES");
        System.out.println(n.obtenerValores(2).toString());
        System.out.println ("Espera Febrero");
        n.insertar(14, "cumple anto");
        n.insertar(14, "examen calculo");
        System.out.println ("Asocia cumple anto y examen calculo al nro 14");
        System.out.println (n.obtenerValores(14).toString());
        n.desasociar(3, "MIERCOLES");
        n.desasociar(3, "MARZO");
        System.out.println ("Desasocia miercoles y marzo del nro 3");
        System.out.println (n.obtenerValores(3).toString());
        
        System.out.println ("INICIO TESTING TDB");
        System.out.println ("Lista las claves espera: [ABB,ACC, CCA,MMM,NNJ,XXX]");
        System.out.println (p.listarClaves().toString());
        System.out.println ("Lista las datos espera: [brunoktr,bantonella, crozas, karinarozas,takki,jose123]");
        System.out.println (p.listarDatos().toString());
        System.out.println ("Busca dato de clave XXX, espera jose123");
        System.out.println (p.obtenerDato("XXX"));
        p.eliminar("ABB");
        p.eliminar("ACC");
        p.eliminar("CCA");
        p.eliminar("MMM");
        p.eliminar("NNJ");
        p.eliminar("XXX");
        System.out.println ("Elimina todas las claves");
        System.out.println (p.listarClaves());
        System.out.println (p.esVacia()+"es vacia? Espera true"); 
        
        
        
    }
    public static void main(String[] args) {
        // TODO code application logic here
       iniciarPrograma("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        
        
        
        
    }
    
}
