/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

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
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Anto
 */
public class Juego {
    
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
            e.printStackTrace();
        }
        return texto;
    }
    public static void leerArchivo(String texto, Grafo g, TablaDeBusqueda hab, TablaDeBusqueda desafios, TDB equipos, MapeoAMuchos desafiosR) {
        StringTokenizer st = new StringTokenizer(texto, "|");
        String cad = "", cod, codD;
        boolean exito;
        while (st.hasMoreTokens()) {
            cad = st.nextToken();
            switch (cad) {
                case "H":
                    cod = st.nextToken();
                    exito = agregarHabitacion(cod, st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), hab, g);
                    if (exito) {
                        escribirLog("Carga de Habitacion " + cod + "\n");
                    }
                    break;
                case "D":
                    cod = st.nextToken();
                    exito = agregarDesafio(cod, st.nextToken(), st.nextToken(), st.nextToken(), desafios);
                    if (exito) {
                        escribirLog( "Carga de Desafio  " + cod + "\n");
                    }
                    break;
                case "E":
                    cod = st.nextToken();
                    exito = agregarEquipo(cod, st.nextToken(), st.nextToken(), equipos);
                    if (exito) {
                       escribirLog( "Carga de Equipo  " + cod + "\n");
                    }
                    break;
                case "P":
                    cod = st.nextToken();
                    codD = st.nextToken();
                    exito = agregarPuerta(cod, codD, st.nextToken(), hab, g);
                    if (exito) {
                        escribirLog( "Puerta añadida, Origen " + cod + " Destino " + codD + "\n");
                    }
                    break;
                case "DR":
                    String codE = st.nextToken();
                    codD = st.nextToken();
                    exito = agregarDesafioResuelto(codE, codD, desafiosR, equipos, desafios);
                    if (exito) {
                        escribirLog("DesafioResuelto añadido, Equipo " + codE + " Desafio " + codD + "\n");
                    }
                break;
                default :
                break;

            }
        }
    }

    public static void escribirLog(String texto) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\ABM.txt", true));
            writer.println(texto);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        boolean exito = h.insertar(codigoH, nuevaH);
        if (exito) {
            g.insertarVertice(nuevaH);
        }
        return exito;

    }

    public static boolean agregarDesafio(String codigo, String puntajeAOtorgar, String nombre, String tipo, TablaDeBusqueda d) {
        int codigoD = Integer.parseInt(codigo);
        int puntajeO = Integer.parseInt(puntajeAOtorgar);
        Desafio nuevoD = new Desafio(codigoD, puntajeO, nombre, tipo);
        return d.insertar(codigoD, nuevoD);

    }

    public static boolean agregarEquipo(String nombre, String puntajeExigido, String habitacionActual, TDB e) {
        int puntE = Integer.parseInt(puntajeExigido);
        int habAct = Integer.parseInt(habitacionActual);
        Equipo nuevoE = new Equipo(nombre, puntE, habAct);
        return e.insertar(nombre, nuevoE);
    }

    public static boolean agregarDesafioResuelto(String nombreEquipo, String codDesafio, MapeoAMuchos desafiosR, TDB infoE, TablaDeBusqueda infoD) {
        boolean exito;
        int cod= Integer.parseInt(codDesafio);
        Desafio d = (Desafio) infoD.obtenerDato(cod);
        Equipo e = (Equipo) infoE.obtenerDato(nombreEquipo);
        exito = d != null && e != null;
        if (exito) {
            desafiosR.insertar(e, d);
            e.setPuntajeTotal(e.getPuntajeActual()+d.getPuntajeAOtorgar());
            e.setPuntajeActual(d.getPuntajeAOtorgar());//CHEQUEAR SI ESTO ESTA BIEN 
        }
        return exito;
    }

    public static void administrarSistema() {
        Grafo plano = new Grafo();
        TablaDeBusqueda infoH = new TablaDeBusqueda();
        TablaDeBusqueda infoD = new TablaDeBusqueda();
        TDB infoE = new TDB();
        MapeoAMuchos desafiosR = new MapeoAMuchos();
        String direccion = ("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        int n = mostrarMenu();
        while (n>=1 && n<=6){
            switch (n) {
                case 1:
                    iniciarPrograma(direccion, plano, infoH, infoD, infoE, desafiosR);
                    break;
                case 2:
                    ABM(plano, infoH, infoD, infoE, desafiosR);
                    break;
                case 3:
                    consultasSobreHabitaciones(infoH,plano);
                break;
                case 4:
                    consultasSobreDesafios(infoD,desafiosR,infoE);
                break;
                case 5:
                    consultasSobreEquipos(plano,infoE,desafiosR, infoD, infoH);
                break;
                case 6:
                    mostrarSistema(plano,infoH,infoD,infoE,desafiosR);
                break;
            }
             n = mostrarMenu();
        }
    }
    
    public static int mostrarMenu() {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Carga Inicial del Sistema");
        System.out.println("2-Modificaciones de Habitaciones,desafios y equipos");
        System.out.println("3-Consultas sobre habitaciones");
        System.out.println("4-Consultas sobre desafios");
        System.out.println("5-Consultas sobre equipos");
        System.out.println("6-Consultas generales");
        System.out.println("7-Finalizar");
        int n= TecladoIn.readLineInt();
        return n;
    }

    public static void mostrarSistema(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR){
        System.out.println ("Plano de la casa");
        System.out.println (plano.toString());
        System.out.println ("Informacion de las habitaciones");
        System.out.println (infoH.toString());
        System.out.println ("Informacion de los desafios");
        System.out.println (infoD.toString());
        System.out.println ("Informacion de los equipos");
        System.out.println (infoE.toString());
        System.out.println ("Informacion de los desafios realizados por equipo");
        System.out.println (desafiosR.toString());
           
    }

    public static void iniciarPrograma(String direccion, Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR) {
        //creacion de las estructuras que almacenaran la informacion 
        String texto = leerTxt("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        leerArchivo(texto, plano, infoH, infoD, infoE, desafiosR);

    }

    public static void ABM(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR) {
        System.out.println("Ingrese H- si desea operar con las habitaciones");
        System.out.println("Ingrese D- si desea operar con los desafios");
        System.out.println("Ingrese E- si desea operar con los equipos");
        System.out.println("Ingrese P- si desea operar con las puertas");
        char n=TecladoIn.readLineNonwhiteChar();
        String res;
       
        switch (n) {
            case 'H':
                System.out.println("Desea crear una nueva habitacion?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!crearHabitacion(plano, infoH)){
                        System.out.println("ERROR.La habitacion ya existe, por favor intentelo de nuevo ");
                    }
                }
                System.out.println("Desea modificar una habitacion ya existente?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!modifacionesHabitaciones(plano, infoH)){
                         System.out.println("ERROR.La habitacion no existe, por favor intentelo de nuevo ");
                    }
                }

                break;
            case 'D':
                System.out.println("Desea crear un nuevo desafio?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!crearDesafio(infoD)){
                        System.out.println ("ERROR.El desafio ya existe, por favor intentelo de nuevo ");
                    }
                }
                System.out.println("Desea modificar un desafio ya existente?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!modifacionesDesafios(infoD)){
                         System.out.println ("ERROR.El desafio no existe, por favor intentelo de nuevo ");
                    }
                }

                break;
            case 'E':
                System.out.println("Desea crear un nuevo equipo?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!crearEquipo(infoE)){
                         System.out.println ("ERROR.El equipo ya existe, por favor intentelo de nuevo ");
                    }
                }
                System.out.println("Desea modificar un equipo ya existente?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!modifacionesEquipos(infoE, infoD, desafiosR)){
                         System.out.println ("ERROR.El equipo no existe, por favor intentelo de nuevo ");
                    }
                }
                ;
                break;
            case 'P':
                System.out.println("Desea crear un nueva puerta?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                   if (!crearPuerta(plano, infoH)){
                        System.out.println("Ha ocurrido un error con los datos ingresados por favor intentelo de nuevo");
                   }
                }
                System.out.println("Desea modificar una puerta ya existente?");
                res = TecladoIn.readLine();
                if (res.equalsIgnoreCase("Si")) {
                    if (!modifacionesPuertas(plano, infoH)){
                         System.out.println("Ha ocurrido un error con los datos ingresados por favor intentelo de nuevo");
                    }
                }

                break;

        }
    }

    public static boolean crearHabitacion(Grafo plano, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de la habitacion");
        int n = TecladoIn.readLineInt();
        boolean encontrado = infoH.existeClave(n);
        if (!encontrado) {
            String nombre;
            int nroP;
            double cantM;
            boolean tieneSalida;
            System.out.println("Ingrese el nombre de la habitacion");
            nombre = TecladoIn.readLine();
            System.out.println("Ingrese el nro de planta");
            nroP = TecladoIn.readLineInt();
            System.out.println("Ingrese la cantidad de metros cuadrados");
            cantM = TecladoIn.readLineDouble();
            System.out.println("Tiene salida? true/false ");
            tieneSalida = TecladoIn.readLineBoolean();
            Habitacion nuevaHabitacion = new Habitacion(n, nombre, nroP, cantM, tieneSalida);
            infoH.insertar(n, nuevaHabitacion);
            plano.insertarVertice(nuevaHabitacion);
            escribirLog("Se agrego habitacion codigo "+n+" nombre "+nombre +"\n");
            
        }
        return !encontrado;

    }

    public static boolean crearDesafio(TablaDeBusqueda infoD) {
        System.out.println("Ingrese el codigo de el desafio");
        int n = TecladoIn.readLineInt();
        boolean encontrado = infoD.existeClave(n);
        if (!encontrado) {
            String nombre;
            int puntaje;
            String tipo;
            System.out.println("Ingrese el nombre de el desafio");
            nombre = TecladoIn.readLine();
            System.out.println("Ingrese el puntaje que otorga");
            puntaje = TecladoIn.readLineInt();
            System.out.println("Ingrese el tipo de desafio");
            tipo =TecladoIn.readLine();

            Desafio nuevoDesafio = new Desafio(n, puntaje, nombre, tipo);
            infoD.insertar(n, nuevoDesafio);
            escribirLog("Se agrego desafio codigo "+n+" nombre "+nombre+"\n");
        }
        return !encontrado;
    }

    public static boolean crearEquipo(TDB infoE) {
        System.out.println("Ingrese el nombre de el equipo");
        String nombre = TecladoIn.readLine();
        boolean encontrado = infoE.existeClave(nombre);
        if (!encontrado) {
            int habitacionAct;
            int puntaje;
            System.out.println("Ingrese el puntaje exigido para salir de la casa");
            puntaje = TecladoIn.readLineInt();
            System.out.println("Ingrese el nro de habitacion en la que se encuentra actualmente");
            habitacionAct = TecladoIn.readLineInt();

            Equipo nuevoEquipo = new Equipo(nombre, puntaje, habitacionAct);
            infoE.insertar(nombre, nuevoEquipo);
            escribirLog("Se agrego equipo con nombre "+nombre +"\n");
        }
        return !encontrado;
    }

    public static boolean crearPuerta(Grafo plano, TablaDeBusqueda infoH) {       
        System.out.println("Ingrese el codigo de la habitacion origen");
        int n = TecladoIn.readLineInt();
        Habitacion habO = (Habitacion) infoH.obtenerDato(n);

        System.out.println("Ingrese el codigo de la habitacion destino");
        int nD = TecladoIn.readLineInt();
        Habitacion habD = (Habitacion) infoH.obtenerDato(nD);

        //si existen las dos habitaciones y no existe una puerta entre ellas
        if (habO != null && habD != null && !plano.existeArco(habO, habD)) {
            int puntaje;
            System.out.println("Ingrese el puntaje exigido para pasar de la habitacion origen a la de destino");
            puntaje = TecladoIn.readLineInt();
            plano.insertarArco(habO, habD, puntaje);
            escribirLog("Se agrego nueva puerta entre la habitacion con codigo "+habO.getCodigo()+" y la habitacion con codigo "+habD.getCodigo()+"puntaje necesario "+ puntaje+"\n");
        }
        return !plano.existeArco(habO, habD);

    }

    //MODIFICACIONES DE LAS HABITACIONES,EQUIPOS Y PUERTAS
    public static boolean modifacionesHabitaciones(Grafo plano, TablaDeBusqueda infoH) {       
        Habitacion h = obtenerHabitacion(infoH);
        //si la habitacion existe se piden los demas datos
        if (h != null) {
            System.out.println("Ingrese E- si desea eliminar la habitacion");
            System.out.println("Ingrese N- si desea modificar el nombre");
            System.out.println("Ingrese P- si desea modificar el nro de planta");
            System.out.println("Ingrese M-si desea modificar la cantidad de m cuadrados");
            System.out.println("Ingrese S-si desea cambiar el atributo de salida");
           
            char m =TecladoIn.readLineNonwhiteChar();
            int cod=h.getCodigo();
            switch (m) {
                case 'E':
                    boolean exito=plano.eliminarVertice(h);
                    infoH.eliminar(cod);
                    if (exito){
                        escribirLog("Se elimino la habitacion con codigo "+cod +"\n");
                    }
                    
                    break;
                case 'N':
                    System.out.println("Ingrese el nuevo nombre");
                    String res = TecladoIn.readLine();
                    h.setNombre(res);
                    escribirLog("Se le modifico el nombre a la habitacion con codigo "+cod +"\n");
                    break;
                case 'P':
                    System.out.println("Ingrese el nuevo nro de planta");
                    int nro = TecladoIn.readLineInt();
                    h.setPlanta(nro);
                    escribirLog("Se modifico el nro de planta de la habitacion con codigo "+cod +"\n");
                    break;
                case 'M':
                    System.out.println("Ingrese la cantidad de m cuadrados");
                    Double mC = TecladoIn.readLineDouble();
                    h.setmCuadrados(mC);
                    escribirLog("Se modificaron los mCuadradros de la habitacion con codigo "+cod +"\n");
                    break;
                case 'S':
                    System.out.println("Ingrese el nuevo valor para el atributo tieneSalida");
                    boolean tieneS = TecladoIn.readLineBoolean();
                    h.setTieneSalida(tieneS);
                    escribirLog("Se le modifico el atributo tieneSalida a la habitacion con codigo "+cod +"\n");
                    break;
                default:
                    System.out.println("La letra ingresada es incorrecta");
                    break;
            }
        }
        return h!=null;

    }

    public static boolean modifacionesDesafios(TablaDeBusqueda infoE) {
        System.out.println("Ingrese el codigo de el desafio que desea modificar");
        int n=TecladoIn.readLineInt();
        Desafio d = (Desafio) infoE.obtenerDato(n);
        //SI EL DESAFIO EXISTE SE SOLICITAN LOS DEMAS DATOS
        if (d != null) {
            System.out.println("Ingrese E- si desea eliminar el desafio");
            System.out.println("Ingrese N- si desea modificar el nombre");
            System.out.println("Ingrese P- si desea modificar el puntaje que otorga");
            System.out.println("Ingrese T-si desea modificar el tipo");
            char m=TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    infoE.eliminar(n);
                    escribirLog("Se elimino el desafio con codigo "+n +"\n");
                    break;
                case 'N':
                    System.out.println("Ingrese el nuevo nombre");
                    String res = TecladoIn.readLine();
                    d.setNombre(res);
                     escribirLog("Se cambio el nombre del desafio con codigo "+n +"\n");
                    break;
                case 'P':
                    System.out.println("Ingrese el nuevo puntaje que otorga");
                    int nro =  TecladoIn.readLineInt();
                    d.setPuntajeAOtorgar(nro);
                    escribirLog("Se cambio el puntaje que otorga el desafio con codigo "+n +"\n");
                    break;
                case 'T':
                    System.out.println("Ingrese el tipo");
                    String tipo =  TecladoIn.readLine();
                    d.setTipo(tipo);
                    escribirLog("Se cambio el tipo del desafio con codigo "+n +"\n");
                    break;
                default:
                    System.out.println("La letra ingresada es incorrecta");
                    break;
            }
        }
        return d!=null;
    }

    public static boolean modifacionesEquipos(TDB infoE, TablaDeBusqueda infoD, MapeoAMuchos desafiosR) {
        System.out.println("Ingrese el nombre de el equipo  que desea modificar");
        String nombre =  TecladoIn.readLine();
        Equipo e = (Equipo) infoE.obtenerDato(nombre);
        if (e != null) {
            System.out.println("Ingrese E- si desea eliminar el equipo");
            System.out.println("Ingrese P- si desea modificar el puntaje exigido para salir de la casa");
            System.out.println("Ingrese T-si desea modificar el puntaje total");
            System.out.println("Ingrese H-si desea cambiar la habitacion actual donde se encuentra el equipo");
            System.out.println("Ingrese A-si desea modificar el puntaje actual en la habitacion donde esta posicionado");
            System.out.println("Ingrese D-si desea añadir un desafio resuelto");
            char m= TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    infoE.eliminar(nombre);
                    escribirLog("Se elimino el equipo con nombre "+nombre +"\n");
                    break;
                case 'P':
                    System.out.println("Ingrese el nuevo puntaje");
                    int cant =  TecladoIn.readLineInt();
                    e.setPuntajeExigido(cant);
                    escribirLog("Al equipo con nombre "+nombre +" se le modifico el puntaje exigido para salir de la casa" +"\n");
                    break;
                case 'T':
                    System.out.println("Ingrese el nuevo puntaje total");
                    int puntT =TecladoIn.readLineInt();
                    e.setPuntajeTotal(puntT);
                    escribirLog("Al equipo con nombre "+nombre +" se le modifico el puntaje total"+"\n");
                    break;
                case 'H':
                    System.out.println("Ingrese el nro de habitacion");
                    int nroH = TecladoIn.readLineInt();
                    e.setHabitacionActual(nroH);
                    escribirLog("Al equipo con nombre "+nombre +" se le modifico la habitacion en la que se encuentra actualmente" +"\n");
                    break;
                case 'A':
                    System.out.println("Ingrese el nuevo puntaje actual en la habitacion");
                    int puntA = TecladoIn.readLineInt();
                    e.setPuntajeActual(puntA);
                    escribirLog("Al equipo con nombre "+nombre +" se le modifico el puntaje actual dentro de la habitacion" +"\n");
                    break;
                case 'D':
                    System.out.println("Ingrese el codigo de el desafio");
                    int codD = TecladoIn.readLineInt();
                    Desafio d = (Desafio) infoD.obtenerDato(codD);
                    if (d != null) {
                        desafiosR.insertar(e, d);
                        e.setPuntajeTotal(e.getPuntajeActual()+d.getPuntajeAOtorgar());
                        e.setPuntajeActual(d.getPuntajeAOtorgar());
                        escribirLog("El equipo con nombre "+nombre +"resolvio el desafio con codigo "+codD +"\n");
                    }
                    break;
                default:
                    System.out.println("Respuesta incorrecta");
                    break;
            }
        }
        return e!=null;
    }

    public static boolean modifacionesPuertas(Grafo plano, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de la habitacion origen");
        int n = TecladoIn.readLineInt();
        Habitacion habO = (Habitacion) infoH.obtenerDato(n);
        System.out.println("Ingrese el codigo de la habitacion destino");
        int nD = TecladoIn.readLineInt();
        Habitacion habD = (Habitacion) infoH.obtenerDato(nD);

        if (habO != null && habD != null && plano.existeArco(n, nD)) {
            System.out.println("Ingrese E- si desea eliminar la puerta");
            System.out.println("Ingrese P- si desea modificar el puntaje para pasar de una habitacion a la otra");
            char m=TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    plano.eliminarArco(n, nD);
                    escribirLog("Se elimino la puerta entre la habitacion con codigo "+n +"y la habitacion con codigo "+nD +"\n");
                    break;

                case 'P':
                    System.out.println("Ingrese el nuevo puntaje");
                    int nro = TecladoIn.readLineInt();
                    plano.cambiarEtiqueta(n, nD, nro);
                    escribirLog("Se modifo el puntaje en la puerta entre la habitacion con codigo "+n +" y la habitacion con codigo "+nD +"\n");
                    break;
                default:
                    System.out.println("La letra ingresada es incorrecta");
                    break;
            }
        }
        return plano.existeArco(n, nD);
    }

    //Consultas sobre habitaciones
    public static void consultasSobreHabitaciones(TablaDeBusqueda infoH, Grafo plano) {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Mostrar habitacion");
        System.out.println("2-Dado una habitacion mostrar las habitaciones contiguas y el puntaje necesario para pasar a ellas");
        System.out.println("3-Dadas dos habitaciones y una cantidad de puntaje averiguar si es posible llegar de la una a la otra acumulando esa cant de puntaje");
        System.out.println("4-Dadas 3 habitaciones y una cantidad de puntaje, mostrar las formas de pasar de una a otra sin pasar por la 3 habitacion y sin superar el puntaje");
        int n = TecladoIn.readLineInt();
        switch (n) {
            case 1:
                Habitacion h = obtenerHabitacion(infoH);
                if (h != null) {
                    System.out.println(h.toString());
                }
                else{
                     System.out.println("Lo sentimos la habitacion ingresada no existe");
                }
                break;
            case 2:
                h = obtenerHabitacion(infoH);
                if (h != null) {
                    System.out.println (plano.mostrarContiguas(h));
                }
                else{
                     System.out.println("Lo sentimos la habitacion ingresada no existe");
                }
                break;
            case 3:
                h = obtenerHabitacion(infoH);
                Habitacion h2 = obtenerHabitacion(infoH);
                if (h != null && h2 != null) {
                    System.out.println("Ingrese el puntaje");
                    int pun = TecladoIn.readLineInt();
                    if (plano.esPosibleLlegar(h, h2, pun)){
                        System.out.println ("Si es posible llegar");
                    }
                    else{
                        System.out.println ("No es posible llegar");
                    }
                }
                else{
                    System.out.println ("Los datos ingresados fueron incorrectos. Por favor intentelo de nuevo");
                }
                break;
            case 4:
                h = obtenerHabitacion(infoH);
                h2 = obtenerHabitacion(infoH);
                Habitacion h3=obtenerHabitacion(infoH);
                if (h != null && h2 != null && h3!=null) {
                    System.out.println("Ingrese el puntaje");
                    int pun = TecladoIn.readLineInt();
                    plano.sinPasarPor(h, h2, h3, pun);
                }
                else{
                    System.out.println ("Los datos ingresados fueron incorrectos. Por favor intentelo de nuevo");
                }
            break;
            default:
                System.out.println ("Respuesta ingresa incorrecta");
                

        }
    }

    private static Habitacion obtenerHabitacion(TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de habitacion");
        int cod = TecladoIn.readLineInt();
        Habitacion h = (Habitacion) infoH.obtenerDato(cod);
        return h;
    }
    
    public static void consultasSobreDesafios (TablaDeBusqueda infoD, MapeoAMuchos desafiosR,TDB infoE){
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Mostrar un desafio");
        System.out.println("2-Dado un equipo mostrar los desafios que resolvio");
        System.out.println("3-Dado dos puntajes y un tipo, mostrar los desafios de ese tipo y en el rango de los puntajes");

        int n = TecladoIn.readLineInt();
        switch (n) {
            case 1:
                Desafio d = obtenerDesafio(infoD);
                if (d != null) {
                    System.out.println(d.toString());
                }
                else{
                    System.out.println ("Lo sentimos el desafio ingresado no existe");
                }
                break;
            case 2:
                Equipo e= obtenerEquipo(infoE);
                if (e!=null){
                    System.out.println (desafiosR.obtenerValores(e).toString());
                }
                else{
                    System.out.println ("Lo sentimos el equipo ingresado no existe");
                }
                break;
            case 3:
                System.out.println("Ingrese el tipo");
                String tipo= TecladoIn.readLine();
                System.out.println("Ingrese el puntajeMinimo");
                int puntMin= TecladoIn.readLineInt();
                System.out.println("Ingrese el puntajeMaximo");
                int puntMax= TecladoIn.readLineInt();
                String cad=infoD.buscarDesafiosTipo(tipo,puntMin,puntMax);
                if (cad.equals("")){
                    System.out.println ("No existe ningun desafio con los datos ingresados, por favor intentelo de nuevo");
                }
                else{
                    System.out.println (cad);
                }
                break;
           
            default:
                System.out.println ("Respuesta ingresa incorrecta");
                

        }
    }
     private static Desafio obtenerDesafio(TablaDeBusqueda infoD) {
        System.out.println("Ingrese el codigo de el desafio");
        int cod = TecladoIn.readLineInt();
        Desafio d = (Desafio) infoD.obtenerDato(cod);
        return d;
    }
    public static void consultasSobreEquipos(Grafo plano, TDB infoE, MapeoAMuchos desafiosR, TablaDeBusqueda infoD,TablaDeBusqueda infoH){
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Mostrar la informacion de un equipo");
        System.out.println("2-Jugar un desafio");
        System.out.println("3-Dado un equipo y una habitacion, averiguar si puede pasar a la habitacion, si es posible avanza");
        System.out.println("4-Dado un equipo averiguar si puede salir");
        

        int n = TecladoIn.readLineInt();
        Equipo e = obtenerEquipo(infoE);
        switch (n) {
            case 1:
                if (e!= null) {
                    System.out.println(e.toString());
                }
                else{
                    System.out.println ("Lo sentimos el equipo ingresado no existe");
                }
                break;
            case 2:
                Desafio d=obtenerDesafio(infoD);
                if (e!=null && d!=null){
                    desafiosR.insertar(e, d);
                    e.setPuntajeTotal(e.getPuntajeTotal()+d.getPuntajeAOtorgar());
                    e.setPuntajeActual(d.getPuntajeAOtorgar());
                }else{
                    System.out.println ("Lo sentimos los datos ingresados no fueron correctos, intentelo de nuevo");
                }
                
                break;
            case 3:
                Habitacion h2= obtenerHabitacion(infoH);
                if (e!=null && h2!=null){
                    Habitacion h=(Habitacion) infoH.obtenerDato(e.getHabitacionActual());
                    //HAY QUE PASAR EL ACTUAL O EL TOTAL
                    boolean exito=plano.puedePasar(h, h2, e.getPuntajeActual());
                    if (exito){
                        //FALTA ALGO MAS?
                        e.setHabitacionActual(h2.getCodigo());                       
                    }
                }
                else{
                    System.out.println ("Lo sentimos los datos ingresados no fueron correctos, intentelo de nuevo");
                }
                
                break;
            case 4:
                 boolean puedeSalir=false;
                 Habitacion hac=(Habitacion) infoH.obtenerDato(e.getHabitacionActual());
                 if (e.getPuntajeTotal()>= e.getPuntajeExigido()){
                     System.out.println ("Puede salir");
                 }
                 else{
                     //ESTA BIEN CON EL ELSE O HAY QUE MOSTRARLO SIEMPRE
                     int puntajeAObtener=e.getPuntajeExigido()-e.getPuntajeTotal();
                     System.out.println ("El puntaje que debe obtener para ganar el juego es de: "+puntajeAObtener);
                 }
                 if (hac.tieneSalida()){
                     System.out.println ("La habitacion en la que se encuentra el equipo actualmente tiene salida al exterior");
                 }
                 else{
                     System.out.println ("La habitacion en la que se encuentra el equipo actualmente NO tiene salida al exterior");
                 }
                 
            break;
            default:
                System.out.println ("Respuesta ingresada incorrecta");
                

        }
    }
     private static Equipo obtenerEquipo(TDB infoE) {
        System.out.println ("Ingrese el nombre de el equipo");
        String codE= TecladoIn.readLine();
        Equipo e= (Equipo)infoE.obtenerDato(codE);
        return e;
    }
    

    public static void main(String[] args) {
        // TODO code application logic here
        administrarSistema();
       /* Grafo plano= new Grafo ();
        plano.insertarVertice("Living");
        plano.insertarVertice("Habitacion");
        plano.insertarVertice("Cocina");
        plano.insertarVertice("Dormitorio");
        
        plano.insertarArco("Living","Cocina", 100);
        plano.insertarArco("Cocina","Habitacion", 100);
        plano.insertarArco("Living","Dormitorio", 20);
        plano.insertarArco("Cocina","Dormitorio", 50);
        System.out.println(plano.toString());
        System.out.println( plano.sinPasarPor("Living", "Cocina","Habitacion",80 ).toString());
         
        TablaDeBusqueda a= new TablaDeBusqueda ();
        a.insertar(14, "pepe");
        a.insertar(7, "juan");
        a.insertar(6, "vec");  
        a.insertar(10, "j"); 
        a.insertar(12, "j"); 
        a.insertar(2, "j"); 
        a.insertar(4, "j");
        a.insertar(1, "j"); 
        a.insertar(3, "j"); 
        a.insertar(5, "j"); 
        a.insertar(9, "j"); 
        a.insertar(8, "j"); 
        */


        
    }
    
    

}
