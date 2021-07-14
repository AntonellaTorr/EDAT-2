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
import java.util.Scanner;

/**
 *
 * @author Anto
 */
public class Tp {

    public static void escribirABM(String texto) {
        try {
            PrintWriter writer = new PrintWriter("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\ABM.txt", "UTF-8");
            writer.println(texto);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void leerArchivo(String texto, Grafo g, TablaDeBusqueda hab, TablaDeBusqueda desafios, TDB equipos,MapeoAMuchos desafiosR) {
        StringTokenizer st = new StringTokenizer(texto, "|");
        String cad = "", cod, abm = "", codD;
        boolean exito;
        while (st.hasMoreTokens()) {
            cad = st.nextToken();
            switch (cad) {
                case "H":
                    cod = st.nextToken();
                    exito=agregarHabitacion(cod, st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), hab, g);
                    if (exito){
                        abm += "Carga de Habitacion " + cod + "\n";
                    }
                    break;
                case "D":
                    cod = st.nextToken();
                    exito=agregarDesafio(cod, st.nextToken(), st.nextToken(), st.nextToken(), desafios);
                    if (exito){
                        abm += "Carga de Desafio  " + cod + "\n";
                    }
                    break;
                case "E":
                    cod = st.nextToken();
                    exito=agregarEquipo(cod, st.nextToken(), st.nextToken(), equipos);
                    if (exito){
                        abm += "Carga de Equipo  " + cod + "\n";
                    }
                    break;
                case "P":
                    cod = st.nextToken();
                    codD = st.nextToken();
                    exito=agregarPuerta(cod, codD, st.nextToken(), hab, g);
                    if (exito){
                        abm += "Puerta añadida, Origen " + cod + " Destino " + codD + "\n";
                    }
                break;
                case "DR":
                    String codE = st.nextToken();
                    codD = st.nextToken();
                    exito=agregarDesafioResuelto( codE, codD, desafiosR, equipos, desafios);
                    if (exito){
                        abm += "DesafioResuelto añadido, Equipo " + codE + " Desafio " + codD + "\n";
                    }
                    
            }
        }
        escribirABM(abm);
    }
       public static String leerTxt(String direccion) {
        String texto = "";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            while ((bfRead = bf.readLine()) != null) {
                temp = temp + bfRead;
            }
            texto = temp;
        } catch (Exception e) {
            System.out.println("No se encontro el archivo");
        }
        return texto;
    }

    public static boolean agregarPuerta(String codOrigen, String codDestino, String puntaje, TablaDeBusqueda h, Grafo g) {
        int codO = Integer.parseInt(codOrigen);
        int codD = Integer.parseInt(codDestino);
        int pun = Integer.parseInt(puntaje);
        Object hab = h.obtenerDato(codO);
        Object habD = h.obtenerDato(codD);
        return g.insertarArco(hab, habD, pun);
    }

    public static boolean agregarHabitacion(String codigo, String nombre, String planta, String mCuadrados, String tieneSalida,
        TablaDeBusqueda h, Grafo g) {
        int codigoH = Integer.parseInt(codigo);
        int plantaH = Integer.parseInt(planta);
        double cantM = Double.parseDouble(mCuadrados);
        boolean tieneS = Boolean.parseBoolean(tieneSalida);
        Habitacion nuevaH = new Habitacion(codigoH, nombre, plantaH, cantM, tieneS);
        boolean exito=h.insertar(codigoH, nuevaH);
        if (exito){
             g.insertarVertice(nuevaH);
        }
        return exito;
       
    }

    public static boolean agregarDesafio(String codigo, String puntajeAOtorgar, String nombre, String tipo, TablaDeBusqueda d) {
        int codigoD = Integer.parseInt(codigo);
        int puntajeO = Integer.parseInt(puntajeAOtorgar);
        Desafio nuevoD = new Desafio(codigoD, puntajeO, nombre, tipo);
        return d.insertar(codigo, nuevoD);

    }

    public static boolean agregarEquipo(String nombre, String puntajeExigido, String habitacionActual, TDB e) {
        //AÑADIR TRIM
        int puntE = Integer.parseInt(puntajeExigido);
        int habAct = Integer.parseInt(habitacionActual);
        Equipo nuevoE = new Equipo(nombre, puntE, habAct);
        return e.insertar(nombre, nuevoE);
    }
    public static boolean agregarDesafioResuelto(String codEquipo, String codDesafio, MapeoAMuchos desafiosR,TDB infoE, TablaDeBusqueda infoD){
        boolean exito;
        Desafio d=(Desafio)infoD.obtenerDato(codDesafio);
        Equipo e= (Equipo)infoE.obtenerDato(codEquipo);
        exito= d!=null && e!=null;
        if (exito){
            desafiosR.insertar(e, d);
        }
        return exito;
    }

 


    public static void iniciarPrograma(String direccion, Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE,MapeoAMuchos desafiosR) {
        //creacion de las estructuras que almacenaran la informacion 
        String texto = leerTxt("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        leerArchivo(texto, plano, infoH, infoD, infoE,desafiosR);

    }

    public static int mostrarMenu() {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Carga Inicial del Sistema");
        System.out.println("2-Modificaciones de Habitaciones,desafios y equipos");
        System.out.println("3-Consultas sobre habitaciones");
        System.out.println("4-Consultas sobre desafios");
        System.out.println("5-Consultas sobre equipos");
        System.out.println("6-Consultas generales");
        int n;
        Scanner leer = new Scanner(System.in);
        n = leer.nextInt();
        return n;
    }

    public static void administrarSistema() {
        Grafo plano = new Grafo();
        TablaDeBusqueda infoH = new TablaDeBusqueda();
        TablaDeBusqueda infoD = new TablaDeBusqueda();
        TDB infoE = new TDB();
        MapeoAMuchos desafiosR = new MapeoAMuchos();//DEBE USARSE AL COMPLETAR DESAFIOS
        String direccion = ("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
      
        int n = mostrarMenu();
        switch (n) {
            case 1:
                iniciarPrograma(direccion, plano, infoH, infoD, infoE,desafiosR);
                break;
            case 2:
                ABM(plano, infoH, infoD, infoE, desafiosR);
            break;
            case 3: 
        }
    
    }

    public static void ABM(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE,MapeoAMuchos desafiosR) {
        System.out.println("Ingrese H- si desea operar con las habitaciones");
        System.out.println("Ingrese D- si desea operar con los desafios");
        System.out.println("Ingrese E- si desea operar con losequipos");
        System.out.println("Ingrese P- si desea operar con las puertas");
        char n;
        String res;
        Scanner leer = new Scanner(System.in);
        n = leer.nextLine().charAt(0);
        switch (n) {
            case 'H':
                // es necesario agregar un while aca hasta que desee salir?
                System.out.println("Desea crear una nueva habitacion?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    crearHabitacion(plano, infoH);
                }
                System.out.println("Desea modificar una habitacion ya existente?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    modifacionesHabitaciones(plano, infoH);
                }

                break;
            case 'D':
                System.out.println("Desea crear un nuevo desafio?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    crearDesafio(infoD);
                }
                System.out.println("Desea modificar un desafio ya existente?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    modifacionesDesafios(infoD);
                }

                break;
            case 'E':
                System.out.println("Desea crear un nuevo equipo?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    crearEquipo(infoE);
                }
                System.out.println("Desea modificar un equipo ya existente?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    modifacionesEquipos(infoE,infoD,desafiosR);
                };
                break;
            case 'P':
                System.out.println("Desea crear un nueva puerta?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    crearPuerta(plano,infoH);
                }
                System.out.println("Desea modificar una puerta ya existente?");
                res = leer.nextLine();
                if (res.equalsIgnoreCase("Si")) {
                    modifacionesPuertas(plano, infoH);
                }
                
                break;

        }
    }

    public static boolean crearHabitacion(Grafo plano, TablaDeBusqueda infoH) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el codigo de la habitacion");
        int n = leer.nextInt();
        boolean encontrado = infoH.existeClave(n);
        if (!encontrado){
            String nombre;
            int nroP;
            double cantM;
            boolean tieneSalida;
            System.out.println("Ingrese el nombre de la habitacion");
            nombre = leer.nextLine();
            System.out.println("Ingrese el nro de planta");
            nroP = leer.nextInt();
            System.out.println("Ingrese la cantidad de metros cuadrados");
            cantM = leer.nextDouble();
            System.out.println("Tiene salida? true/false ");
            tieneSalida = leer.nextBoolean();
            Habitacion nuevaHabitacion = new Habitacion(n, nombre, nroP, cantM, tieneSalida);
            infoH.insertar(n, nuevaHabitacion);
            plano.insertarVertice(nuevaHabitacion);
            //agregar se creo una habitacion
        }
        return !encontrado;
        
    }

    public static boolean crearDesafio(TablaDeBusqueda infoD) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el codigo de el desafio");
        int n = leer.nextInt();
        boolean encontrado = infoD.existeClave(n);
        if (!encontrado){
            String nombre;
            int puntaje;
            String tipo;
            System.out.println("Ingrese el nombre de el desafio");
            nombre = leer.nextLine();
            System.out.println("Ingrese el puntaje que otorga");
            puntaje = leer.nextInt();
            System.out.println("Ingrese el tipo de desafio");
            tipo = leer.nextLine();

            Desafio nuevoDesafio = new Desafio(n, puntaje, nombre, tipo);
            infoD.insertar(n, nuevoDesafio);
        }
        
        //agregar se croe un desafio
        return !encontrado;
    }
    
    public static boolean crearEquipo(TDB infoE) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre de el equipo");
        String nombre = leer.nextLine();
        boolean encontrado = infoE.existeClave(nombre);
        if (encontrado){
            int habitacionAct;
            int puntaje;
            System.out.println("Ingrese el puntaje exigido para salir de la casa");
            puntaje = leer.nextInt();
            System.out.println("Ingrese el nro de habitacion en la que se encuentra actualmente");
            habitacionAct = leer.nextInt();

            Equipo nuevoEquipo = new Equipo(nombre, puntaje, habitacionAct);
            infoE.insertar(nombre, nuevoEquipo);
        }
        //agregar se creo un equipo
        return !encontrado;
    }
    public static boolean crearPuerta(Grafo plano, TablaDeBusqueda infoH) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el codigo de la habitacion origen");
        int n= leer.nextInt();
        Habitacion habO = (Habitacion)infoH.obtenerDato(n);
       
        System.out.println("Ingrese el codigo de la habitacion destino");
        int nD= leer.nextInt();
        Habitacion habD = (Habitacion)infoH.obtenerDato(nD);
        
        boolean exito=false;
        
       
        if (habO!=null && habD!=null && !plano.existeArco(habO, habD)){
            exito=true;
            int puntaje;
            System.out.println("Ingrese el puntaje exigido para pasar de la habitacion origen a la de destino");
            puntaje = leer.nextInt();
            plano.insertarArco(habO, habD, puntaje);
        }
        
        //agregar se creo una puerta en caso de exito
        return  exito;
       

    }
    //MODIFICACIONES DE LAS HABITACIONES,EQUIPOS Y PUERTAS
    public static boolean modifacionesHabitaciones(Grafo plano, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de la habitacion que desea modificar");
        int n;
        Scanner leer = new Scanner(System.in);
        n = leer.nextInt();
        Habitacion h = (Habitacion) infoH.obtenerDato(n);
        //si la habitacion existe se piden los demas datos
        if (h!=null){
            System.out.println("Ingrese E- si desea eliminar la habitacion");
            System.out.println("Ingrese N- si desea modificar el nombre");
            System.out.println("Ingrese P- si desea modificar el nro de planta");
            System.out.println("Ingrese M-si desea modificar la cantidad de m cuadrados");
            System.out.println("Ingrese S-si desea cambiar el atributo de salida");
            char m;
            m = leer.nextLine().charAt(0);
            switch(m){
                case 'E': 
                    plano.eliminarVertice(n);
                    infoH.eliminar(n);
                break;
                case 'N': 
                    System.out.println ("Ingrese el nuevo nombre");
                    String res= leer.nextLine();
                    h.setNombre(res);
                break;
                case 'P':
                    System.out.println ("Ingrese el nuevo nro de planta");
                    int nro= leer.nextInt();
                    h.setPlanta(nro);
                break;
                case 'M':
                    System.out.println ("Ingrese la cantidad de m cuadrados");
                    Double mC= leer.nextDouble();
                    h.setmCuadrados(mC);
                break;
                case 'S':
                    System.out.println ("Ingrese el nuevo valor para el atributo tieneSalida");
                    boolean tieneS= leer.nextBoolean();
                    h.setTieneSalida(tieneS);
                break;
                default:
                    System.out.println ("Letra incorrecta");
                break; 
            }
        }
        return (h!=null);

    }

    public static boolean modifacionesDesafios(TablaDeBusqueda infoE) {
        System.out.println("Ingrese el codigo de el desafio que desea modificar");
        int n;
        Scanner leer = new Scanner(System.in);
        n = leer.nextInt();
        Desafio d = (Desafio) infoE.obtenerDato(n);
        //SI EL DESAFIO EXISTE SE SOLICITAN LOS DEMAS DATOS
        if (d!=null){
            System.out.println("Ingrese E- si desea eliminar el desafio");
            System.out.println("Ingrese N- si desea modificar el nombre");
            System.out.println("Ingrese P- si desea modificar el puntaje que otorga");
            System.out.println("Ingrese T-si desea modificar el tipo");
            char m;
            m = leer.nextLine().charAt(0);
            switch(m){
                case 'E': 
                    infoE.eliminar(n);
                break;
                case 'N': 
                    System.out.println ("Ingrese el nuevo nombre");
                    String res= leer.nextLine();
                    d.setNombre(res);
                break;
                case 'P':
                    System.out.println ("Ingrese el nuevo puntaje que otorga");
                    int nro= leer.nextInt();
                    d.setPuntajeAOtorgar(nro);
                break;
                case 'T':
                    System.out.println ("Ingrese el tipo");
                    String tipo= leer.nextLine();
                    d.setTipo(tipo);
                break;
                default:
                    System.out.println ("Respuesta incorrecta");
                break; 
            }
        }
        return (d!=null);
    }

    public static boolean modifacionesEquipos(TDB infoE,TablaDeBusqueda infoD, MapeoAMuchos desafiosR) {
        System.out.println("Ingrese el nombre de el equipo  que desea modificar");
        Scanner leer = new Scanner(System.in);
        String nombre= leer.nextLine();
        Equipo e = (Equipo) infoE.obtenerDato(nombre);
        if (e!=null){
            System.out.println("Ingrese E- si desea eliminar el equipo");
            System.out.println("Ingrese P- si desea modificar el puntaje exigido para salir de la casa");
            System.out.println("Ingrese T-si desea modificar el puntaje total");
            System.out.println("Ingrese H-si desea cambiar la habitacion actual donde se encuentra el equipo");
            System.out.println("Ingrese A-si desea modificar el puntaje actual en la habitacion donde esta posicionado");
            System.out.println("Ingrese D-si desea añadir un desafio resuelto");
            char m;
            m = leer.nextLine().charAt(0);
            switch(m){
                case 'E': 
                    infoE.eliminar(nombre);
                break;
                case 'P': 
                    System.out.println ("Ingrese el nuevo puntaje");
                    int cant= leer.nextInt();
                    e.setPuntajeExigido(cant);
                break;
                case 'T':
                    System.out.println ("Ingrese el nuevo puntaje total");
                    int puntT= leer.nextInt();
                    e.setPuntajeTotal(puntT);
                break;
                case 'H':
                    System.out.println ("Ingrese el nro de habitacion");
                    int nroH= leer.nextInt();
                    e.setHabitacionActual(nroH);
                break;
                case 'A':
                    System.out.println ("Ingrese el nuevo puntaje actual en la habitacion");
                    int puntA= leer.nextInt();
                    e.setPuntajeActual(puntA);
                break;
                case 'D':
                    System.out.println ("Ingrese el codigo de el desafio");
                    int codD= leer.nextInt();
                    Desafio d= (Desafio)infoD.obtenerDato(codD);
                    if (d!=null){
                        desafiosR.insertar(e,d);
                    }
                break;                  
                default:
                    System.out.println ("Respuesta incorrecta");
                break; 
            }
        }
        return (e!=null);
    }
  
    public static void modifacionesPuertas(Grafo plano,TablaDeBusqueda infoH) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el codigo de la habitacion origen");
        int n= leer.nextInt();
        Habitacion habO = (Habitacion)infoH.obtenerDato(n);
        
        System.out.println("Ingrese el codigo de la habitacion destino");
        int nD= leer.nextInt();
        Habitacion habD = (Habitacion)infoH.obtenerDato(nD);
        
        if (habO!=null && habD!=null){
            System.out.println("Ingrese E- si desea eliminar la puerta");
            System.out.println("Ingrese P- si desea modificar el puntaje para pasar de una habitacion a la otra");
            char m;
            m = leer.nextLine().charAt(0);
            switch(m){
                case 'E': 
                    plano.eliminarArco(n, nD);
                break;

                case 'P':
                    System.out.println ("Ingrese el nuevo puntaje");
                    int nro= leer.nextInt();
                    plano.cambiarEtiqueta(n, nD, nro);
                break;
                default:
                    System.out.println ("Respuesta incorrecta");
                break; 
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        administrarSistema();
    }

}
