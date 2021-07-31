/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import Estructuras.*;
import java.util.StringTokenizer;
import Dominio.*;
import java.io.*;

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

    public static void leerArchivo(String texto, Grafo g, TablaDeBusqueda hab, TablaDeBusqueda desafios, TDB equipos) {
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
                    exito = agregarDesafio(cod, st.nextToken(), st.nextToken(), desafios);
                    if (exito) {
                        escribirLog("Carga de Desafio con puntaje  " + cod + "\n");
                    }
                    break;
                case "E":
                    cod = st.nextToken();
                    exito = agregarEquipo(cod, st.nextToken(), st.nextToken(), equipos);
                    if (exito) {
                        escribirLog("Carga de Equipo  " + cod + "\n");
                    }
                    break;
                case "P":
                    cod = st.nextToken();
                    codD = st.nextToken();
                    exito = agregarPuerta(cod, codD, st.nextToken(), hab, g);
                    if (exito) {
                        escribirLog("Puerta añadida, Origen " + cod + " Destino " + codD + "\n");
                    }
                    break;
                default:

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

    public static boolean agregarDesafio(String puntajeAOtorgar, String nombre, String tipo, TablaDeBusqueda d) {
        int puntajeO = Integer.parseInt(puntajeAOtorgar);
        Desafio nuevoD = new Desafio(puntajeO, nombre, tipo);
        return d.insertar(puntajeO, nuevoD);

    }

    public static boolean agregarEquipo(String nombre, String puntajeExigido, String habitacionActual, TDB e) {
        int puntE = Integer.parseInt(puntajeExigido);
        int habAct = Integer.parseInt(habitacionActual);
        Equipo nuevoE = new Equipo(nombre, puntE, habAct);
        return e.insertar(nombre, nuevoE);
    }

    /* public static boolean agregarDesafioResuelto(String nombreEquipo, String puntajeDes, MapeoAMuchos desafiosR, TDB infoE, TablaDeBusqueda infoD) {
        //agregar verificacion de si no resolvio desafio el equipo
        boolean exito;
        int puntajeD= Integer.parseInt(puntajeDes);
        Desafio d = (Desafio) infoD.obtenerDato(puntajeD);
        Equipo e = (Equipo) infoE.obtenerDato(nombreEquipo);
        exito = d != null && e != null;
        if (exito) {
            //si el equipo todavia no habia resuelto el desafio le agregamos el puntaje
            if (desafiosR.asociar(e, d)){
                 e.setPuntajeActual(e.getPuntajeActual()+puntajeD);
            }
          
        }
        return exito;
    }
     */
    public static void administrarSistema() {
        Grafo plano = new Grafo();
        TablaDeBusqueda infoH = new TablaDeBusqueda();
        TablaDeBusqueda infoD = new TablaDeBusqueda();
        TDB infoE = new TDB();
        MapeoAMuchos desafiosR = new MapeoAMuchos();
        MapeoAMuchos puertasCompletadas = new MapeoAMuchos();
        String direccion = ("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        int n = mostrarMenu();
        while (n >= 1 && n <= 6) {
            switch (n) {
                case 1:
                    iniciarPrograma(direccion, plano, infoH, infoD, infoE);
                    break;
                case 2:
                    ABM(plano, infoH, infoD, infoE, desafiosR);
                    break;
                case 3:
                    consultasSobreHabitaciones(infoH, plano);
                    break;
                case 4:
                    consultasSobreDesafios(infoD, desafiosR, infoE);
                    break;
                case 5:
                    consultasSobreEquipos(plano, infoE, desafiosR, infoD, infoH, puertasCompletadas);
                    break;
                case 6:
                    mostrarSistema(plano, infoH, infoD, infoE, desafiosR);
                    break;
                default:
                    System.out.println("El numero ingresado es incorrecto por favor intentelo de nuevo");
            }
            n = mostrarMenu();
        }
    }

    public static int mostrarMenu() {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Carga Inicial del Sistema");
        System.out.println("2-Modificaciones de Habitaciones,desafios,equipos y puertas");
        System.out.println("3-Consultas sobre habitaciones");
        System.out.println("4-Consultas sobre desafios");
        System.out.println("5-Consultas sobre equipos");
        System.out.println("6-Consultas generales");
        System.out.println("7-Finalizar");
        int n = TecladoIn.readLineInt();
        return n;
    }

    public static void mostrarSistema(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR) {
        System.out.println("Ingrese que desea ver");
        System.out.println("P-Plano de la casa");
        System.out.println("H-Informacion de las habitaciones");
        System.out.println("D-Informacion de los desafios");
        System.out.println("E-Informacion de los equipos");
        System.out.println("R-Los desafios resueltos por cada equipo");
        char res= TecladoIn.readLineNonwhiteChar();
        switch (res){
            case 'P':
                 System.out.println(plano.toString());
            break;
            case 'H':
                System.out.println(infoH.toString());
                break;
            case 'D':
                System.out.println(infoD.toString());
                break;
            case 'E':
                 System.out.println(infoE.toString());
                break;
            case 'R':
                System.out.println(desafiosR.toString());
                break;
            default:
                 System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");
        }
        

    }

    public static void iniciarPrograma(String direccion, Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE) {
        String texto = leerTxt("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        leerArchivo(texto, plano, infoH, infoD, infoE);

    }

    public static void ABM(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR) {
        System.out.println("Ingrese H- si desea operar con las habitaciones");
        System.out.println("Ingrese D- si desea operar con los desafios");
        System.out.println("Ingrese E- si desea operar con los equipos");
        System.out.println("Ingrese P- si desea operar con las puertas");
        char n = TecladoIn.readLineNonwhiteChar();
        switch (n) {
            case 'H':
                ABMHabitaciones(plano, infoH);
                break;
            case 'D':
                ABMDesafios(infoD);
                break;
            case 'E':
                ABMEquipos(infoE, infoD, desafiosR, infoH);
                break;
            case 'P':
                ABMPuertas(plano, infoH);
                break;
            default:
                System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");

        }
    }

    public static void ABMHabitaciones(Grafo plano, TablaDeBusqueda infoH) {
        String res;
        System.out.println("Desea crear una nueva habitacion?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!crearHabitacion(plano, infoH)) {
                System.out.println("ERROR.La habitacion ya existe, por favor intentelo de nuevo ");
            }
        }
        System.out.println("Desea modificar una habitacion ya existente?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!modifacionesHabitaciones(plano, infoH)) {
                System.out.println("ERROR.La habitacion no existe, por favor intentelo de nuevo ");
            }
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
            escribirLog("Se agrego habitacion codigo " + n + " nombre " + nombre + "\n");

        }
        return !encontrado;

    }

    public static boolean modifacionesHabitaciones(Grafo plano, TablaDeBusqueda infoH) {
        Habitacion h = obtenerHabitacion(infoH);
        //si la habitacion existe se piden los demas datos
        if (h != null) {
            System.out.println("Ingrese E- si desea eliminar la habitacion");
            System.out.println("Ingrese N- si desea modificar el nombre");
            System.out.println("Ingrese P- si desea modificar el nro de planta");
            System.out.println("Ingrese M-si desea modificar la cantidad de m cuadrados");
            System.out.println("Ingrese S-si desea cambiar el atributo de salida");
            char m = TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    if (h.getCantEquiposOcupandola() == 0) {
                        eliminarHabitacion(plano, infoH, h);
                    } else {
                        System.out.println("Lo sentimos, dicha habitacion tiene equipos ocupandola, por lo tanto no se puede eliminar");
                    }
                    break;
                case 'N':
                    cambiarNombre(h);
                    break;
                case 'P':
                    cambiarNroPlanta(h);
                    break;
                case 'M':
                    cambiarCantMCuadrados(h);
                    break;
                case 'S':
                    cambiarTieneSalida(h);
                    break;
                default:
                    System.out.println("La letra ingresada es incorrecta");
            }
        }
        return h != null;

    }

    public static void eliminarHabitacion(Grafo plano, TablaDeBusqueda infoH, Habitacion h) {
        boolean exito = infoH.eliminar(h.getCodigo());
        if (exito) {
            plano.eliminarVertice(h);
            escribirLog("Se elimino la habitacion con codigo " + h.getCodigo() + "\n");
        }
    }

    public static void cambiarNombre(Habitacion h) {
        System.out.println("Ingrese el nuevo nombre");
        String res = TecladoIn.readLine();
        h.setNombre(res);
        escribirLog("Se le modifico el nombre a la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void cambiarNroPlanta(Habitacion h) {
        System.out.println("Ingrese el nuevo nro de planta");
        int nro = TecladoIn.readLineInt();
        h.setPlanta(nro);
        escribirLog("Se modifico el nro de planta de la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void cambiarCantMCuadrados(Habitacion h) {
        System.out.println("Ingrese la cantidad de m cuadrados");
        Double mC = TecladoIn.readLineDouble();
        h.setmCuadrados(mC);
        escribirLog("Se modificaron los mCuadradros de la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void cambiarTieneSalida(Habitacion h) {
        System.out.println("Ingrese el nuevo valor para el atributo tieneSalida");
        boolean tieneS = TecladoIn.readLineBoolean();
        h.setTieneSalida(tieneS);
        escribirLog("Se le modifico el atributo tieneSalida a la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void ABMDesafios(TablaDeBusqueda infoD) {
        String res;
        System.out.println("Desea crear un nuevo desafio?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!crearDesafio(infoD)) {
                System.out.println("ERROR.El desafio ya existe, por favor intentelo de nuevo ");
            }
        }
        System.out.println("Desea modificar un desafio ya existente?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!modifacionesDesafios(infoD)) {
                System.out.println("ERROR.El desafio no existe, por favor intentelo de nuevo ");
            }
        }
    }

    public static boolean modifacionesDesafios(TablaDeBusqueda infoE) {
        System.out.println("Ingrese el puntaje del desafio que desea modificar");
        int n = TecladoIn.readLineInt();
        Desafio d = (Desafio) infoE.obtenerDato(n);
        //SI EL DESAFIO EXISTE SE SOLICITAN LOS DEMAS DATOS
        if (d != null) {
            System.out.println("Ingrese E- si desea eliminar el desafio");
            System.out.println("Ingrese N- si desea modificar el nombre");
            System.out.println("Ingrese T-si desea modificar el tipo");
            char m = TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    infoE.eliminar(n);
                    escribirLog("Se elimino el desafio con codigo " + n + "\n");
                    break;
                case 'N':
                    System.out.println("Ingrese el nuevo nombre");
                    String res = TecladoIn.readLine();
                    d.setNombre(res);
                    escribirLog("Se cambio el nombre del desafio con codigo " + n + "\n");
                    break;
                case 'T':
                    System.out.println("Ingrese el tipo");
                    String tipo = TecladoIn.readLine();
                    d.setTipo(tipo);
                    escribirLog("Se cambio el tipo del desafio con codigo " + n + "\n");
                    break;
                default:
                    System.out.println("La letra ingresada es incorrecta");
            }
        }
        return d != null;
    }

    public static boolean crearDesafio(TablaDeBusqueda infoD) {
        System.out.println("Ingrese el puntaje que otorga");
        int puntaje = TecladoIn.readLineInt();
        boolean encontrado = infoD.existeClave(puntaje);
        if (!encontrado) {
            String nombre;
            String tipo;
            System.out.println("Ingrese el nombre de el desafio");
            nombre = TecladoIn.readLine();
            System.out.println("Ingrese el tipo de desafio");
            tipo = TecladoIn.readLine();
            Desafio nuevoDesafio = new Desafio(puntaje, nombre, tipo);
            infoD.insertar(puntaje, nuevoDesafio);
            escribirLog("Se agrego desafio con puntaje " + puntaje + " nombre " + nombre + "\n");
        }
        return !encontrado;
    }

    public static void ABMEquipos(TDB infoE, TablaDeBusqueda infoD, MapeoAMuchos desafiosR, TablaDeBusqueda infoH) {
        String res;
        System.out.println("Desea crear un nuevo equipo?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!crearEquipo(infoE, infoD,infoH)) {
                System.out.println("Lo sentimos los datos ingresados fueron incorrectos, por favor intentelo de nuevo ");
            }
        }
        System.out.println("Desea modificar un equipo ya existente?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!modifacionesEquipos(infoE, infoD, desafiosR, infoH)) {
                System.out.println("ERROR.El equipo no existe, por favor intentelo de nuevo ");
            }
        }
    }

    public static boolean crearEquipo(TDB infoE, TablaDeBusqueda infoD, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el nombre de el equipo");
        String nombre = TecladoIn.readLine();
        Equipo nuevoEquipo = null;
        boolean encontrado = infoE.existeClave(nombre), existeH = false, exito = false;
        if (!encontrado) {
            int habitacionAct;
            int puntaje;
            System.out.println("Ingrese el puntaje exigido para salir de la casa");
            puntaje = TecladoIn.readLineInt();
            System.out.println("Desea ingresar el nro de habitacion en el que se encuentra actualmente?SI/NO");
            String res = TecladoIn.readLine();
            if (res.equalsIgnoreCase("SI")) {
                System.out.println("Ingrese el nro de habitacion en la que se encuentra actualmente");
                habitacionAct = TecladoIn.readLineInt();
                existeH = infoH.existeClave(habitacionAct);
                if (existeH) {
                    nuevoEquipo = new Equipo(nombre, puntaje, habitacionAct);
                    exito = true;
                }
            } else {
                nuevoEquipo = new Equipo(nombre, puntaje, 0);
                exito = true;
            }
            infoE.insertar(nombre, nuevoEquipo);
            escribirLog("Se agrego equipo con nombre " + nombre + "\n");
        }
        return exito;
    }

    public static boolean modifacionesEquipos(TDB infoE, TablaDeBusqueda infoD, MapeoAMuchos desafiosR, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el nombre de el equipo  que desea modificar");
        String nombre = TecladoIn.readLine();
        Equipo e = (Equipo) infoE.obtenerDato(nombre);
        if (e != null) {
            System.out.println("Ingrese E- si desea eliminar el equipo");
            System.out.println("Ingrese P- si desea modificar el puntaje exigido para salir de la casa");
            System.out.println("Ingrese T-si desea modificar el puntaje total");
            System.out.println("Ingrese H-si desea cambiar la habitacion actual donde se encuentra el equipo");
            System.out.println("Ingrese A-si desea modificar el puntaje actual en la habitacion donde esta posicionado");
            System.out.println("Ingrese D-si desea añadir un desafio resuelto");
            Habitacion h = (Habitacion) infoD.obtenerDato(e.getHabitacionActual());
            char m = TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    eliminarEquipo(e, infoE, h);
                    break;
                case 'P':
                    modificarPuntajeExigido(e);
                    break;
                case 'T':
                    modificarPuntajeTotal(e);
                    break;
                case 'H':
                    if (!modificarHabitacionActual(e, infoH)){
                        System.out.println ("Lo sentimos la habitacion ingresada es invalida por favor intente de nuevo");
                    }
                    break;
                case 'A':
                    modificarPuntajeHabitacionActual(e);
                    break;
                case 'D':
                    if (e.getHabitacionActual() != 0) {
                        if (!añadirDesafioResuelto(e, infoD, desafiosR)) {
                            System.out.println("Lo sentimos los datos ingresados fueron incorrectos por favor intentelo de nuevo");
                        }
                    } else {
                        System.out.println("El equipo no se encuentra en ninguna habitacion de la casa por lo tanto no puede jugar, por favor intentelo de nuevo");
                    }
                    break;
                default:
                    System.out.println("Respuesta incorrecta");
            }
        }
        return e != null;
    }

    public static void eliminarEquipo(Equipo e, TDB infoE, Habitacion hActual) {
        String nombre = e.getNombre();
        infoE.eliminar(nombre);
        if (e.getHabitacionActual() != 0) {
            hActual.setCantEquiposOcupandola(hActual.getCantEquiposOcupandola() - 1);
        }
        escribirLog("Se elimino el equipo con nombre " + nombre + "\n");
    }

    public static void modificarPuntajeExigido(Equipo e) {
        System.out.println("Ingrese el nuevo puntaje");
        int cant = TecladoIn.readLineInt();
        e.setPuntajeExigido(cant);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico el puntaje exigido para salir de la casa" + "\n");
    }

    public static void modificarPuntajeTotal(Equipo e) {
        System.out.println("Ingrese el nuevo puntaje total");
        int puntT = TecladoIn.readLineInt();
        e.setPuntajeTotal(puntT);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico el puntaje total" + "\n");
    }

    public static boolean modificarHabitacionActual(Equipo e, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el nro de habitacion");
        int nroH = TecladoIn.readLineInt();
        Habitacion habActual = (Habitacion) infoH.obtenerDato(e.getHabitacionActual());
        Habitacion nuevaHabActual = (Habitacion) infoH.obtenerDato(nroH);
        //si existe la habitacion ingresada
        
        if (nuevaHabActual != null){
            if (e.getHabitacionActual()!=0){
                //si el equipo se encuentra en una habitacion valida entonces le seteamos la cantidad de equipos que la estan ocupadno
                habActual.setCantEquiposOcupandola(habActual.getCantEquiposOcupandola() - 1);
            }
            //cambiamos al equipo de habitacion
            e.setHabitacionActual(nroH);
            nuevaHabActual.setCantEquiposOcupandola(nuevaHabActual.getCantEquiposOcupandola() + 1);
            escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico la habitacion en la que se encuentra actualmente" + "\n");
        }
        return nuevaHabActual!=null;

    }

    public static void modificarPuntajeHabitacionActual(Equipo e) {
        System.out.println("Ingrese el nuevo puntaje actual en la habitacion");
        int puntA = TecladoIn.readLineInt();
        e.setPuntajeActual(puntA);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico el puntaje actual dentro de la habitacion" + "\n");
    }

    public static boolean añadirDesafioResuelto(Equipo e, TablaDeBusqueda infoD, MapeoAMuchos desafiosR) {
        Desafio d = obtenerDesafio(infoD);
        boolean exito = false;
        if (d != null) {
            if (desafiosR.asociar(e, d)) {
                e.setPuntajeTotal(e.getPuntajeActual() + d.getPuntajeAOtorgar());
                e.setPuntajeActual(d.getPuntajeAOtorgar());
                exito = true;
                escribirLog("El equipo con nombre " + e.getNombre() + "resolvio el desafio con puntaje " + d.getPuntajeAOtorgar() + "\n");
            }
        }
        return exito;
    }

    public static void ABMPuertas(Grafo plano, TablaDeBusqueda infoH) {
        String res;
        System.out.println("Desea crear un nueva puerta?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!crearPuerta(plano, infoH)) {
                System.out.println("Ha ocurrido un error con los datos ingresados por favor intentelo de nuevo");
            }
        }
        System.out.println("Desea modificar una puerta ya existente?");
        res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("Si")) {
            if (!modifacionesPuertas(plano, infoH)) {
                System.out.println("Ha ocurrido un error con los datos ingresados por favor intentelo de nuevo");
            }
        }

    }

    public static boolean crearPuerta(Grafo plano, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de la habitacion origen");
        int n = TecladoIn.readLineInt();
        Habitacion habO = (Habitacion) infoH.obtenerDato(n);
        boolean encontrado=false;

        System.out.println("Ingrese el codigo de la habitacion destino");
        int nD = TecladoIn.readLineInt();
        Habitacion habD = (Habitacion) infoH.obtenerDato(nD);

        //si existen las dos habitaciones y no existe una puerta entre ellas
        if (habO != null && habD != null && !plano.existeArco(habO, habD)) {
            int puntaje;
            System.out.println("Ingrese el puntaje exigido para pasar de la habitacion origen a la de destino");
            puntaje = TecladoIn.readLineInt();
            encontrado=plano.insertarArco(habO, habD, puntaje);
            escribirLog("Se agrego nueva puerta entre la habitacion con codigo " + habO.getCodigo() + " y la habitacion con codigo " + habD.getCodigo() + "puntaje necesario " + puntaje + "\n");
        }
        return encontrado;

    }

    public static boolean modifacionesPuertas(Grafo plano, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de la habitacion origen");
        int n = TecladoIn.readLineInt();
        Habitacion habO = (Habitacion) infoH.obtenerDato(n);
        System.out.println("Ingrese el codigo de la habitacion destino");
        int nD = TecladoIn.readLineInt();
        Habitacion habD = (Habitacion) infoH.obtenerDato(nD);
        boolean exito=false;
        if (habO != null && habD != null && plano.existeArco(habO, habD)) {
            System.out.println("Ingrese E- si desea eliminar la puerta");
            System.out.println("Ingrese P- si desea modificar el puntaje para pasar de una habitacion a la otra");
            char m = TecladoIn.readLineNonwhiteChar();
            switch (m) {
                case 'E':
                    exito=plano.eliminarArco(habO, habD);
                    escribirLog("Se elimino la puerta entre la habitacion con codigo " + n + "y la habitacion con codigo " + nD + "\n");
                    break;
                case 'P':
                    System.out.println("Ingrese el nuevo puntaje");
                    int nro = TecladoIn.readLineInt();
                    exito=plano.cambiarPeso(habO, habD, nro);
                    plano.cambiarPeso(habD, habO, nro);
                    escribirLog("Se modifo el puntaje en la puerta entre la habitacion con codigo " + n + " y la habitacion con codigo " + nD + "\n");
                    break;
                default:
                    System.out.println("La letra ingresada es incorrecta");
            }
        }
        return exito;
    }

    //Consultas sobre habitaciones
    public static void consultasSobreHabitaciones(TablaDeBusqueda infoH, Grafo plano) {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Mostrar habitacion");
        System.out.println("2-Dado una habitacion mostrar las habitaciones contiguas y el puntaje necesario para pasar a ellas");
        System.out.println("3-Dadas dos habitaciones y una cantidad de puntaje averiguar si es posible llegar de la una a la otra acumulando esa cant de puntaje");
        System.out.println("4-Dadas 3 habitaciones y una cantidad de puntaje, mostrar las formas de pasar de una a otra sin pasar por la 3 habitacion y sin superar el puntaje");
        int n = TecladoIn.readLineInt();
        Habitacion h = obtenerHabitacion(infoH);
        if (h != null) {
            switch (n) {
                case 1:
                    System.out.println(h.toString());
                    break;
                case 2:
                    mostrarHabitacionesContiguas(plano.buscarAdyacentes(h));
                    break;
                case 3:
                    Habitacion h2 = obtenerHabitacion(infoH);
                    if (h2 != null) {
                        esPosibleLlegar(plano, h, h2);
                    } else {
                        System.out.println("La habitacion ingresada no existe por favor intentelo de nuevo");
                    }
                    break;
                case 4:
                    h2 = obtenerHabitacion(infoH);
                    Habitacion h3 = obtenerHabitacion(infoH);
                    if (h2 != null && h3 != null) {
                        formasDePasar(plano, h, h2, h3);
                    } else {
                        System.out.println("Los datos ingresados fueron incorrectos. Por favor intentelo de nuevo");
                    }
                    break;
                default:
                    System.out.println("La respuesta ingresada es incorrecta");
            }
        } else {
            System.out.println("Lo sentimos la habitacion ingresada no existe");
        }

    }

    public static void esPosibleLlegar(Grafo plano, Habitacion h, Habitacion h2) {
        System.out.println("Ingrese el puntaje");
        int pun = TecladoIn.readLineInt();
        if (plano.esPosibleLlegar(h, h2, pun)) {
            System.out.println("Si es posible llegar");
        } else {
            System.out.println("No es posible llegar");
        }
    }

    public static void formasDePasar(Grafo plano, Habitacion h, Habitacion h2, Habitacion h3) {
        System.out.println("Ingrese el puntaje");
        int pun = TecladoIn.readLineInt();
        mostrarCaminos(plano.sinPasarPor(h, h2, h3, pun));
    }

    public static void mostrarCaminos(Cola caminos) {
        if(caminos.esVacia()){
             System.out.println("No existen caminos bajo las condiciones indicadas");
        }
        else{
            while (!caminos.esVacia()) {
            Lista caminoActual = (Lista) caminos.obtenerFrente();
            int i = 1, maxLongitud = caminoActual.longitud();
            while (i <= maxLongitud) {
                Habitacion h = (Habitacion) caminoActual.recuperar(i);
                System.out.print(" H" + "codigo=" + h.getCodigo());
                i++;
            }
            System.out.println("\n");

            caminos.sacar();
            }
        }
    }

    public static Habitacion obtenerHabitacion(TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de habitacion");
        int cod = TecladoIn.readLineInt();
        Habitacion h = (Habitacion) infoH.obtenerDato(cod);
        return h;
    }

    public static void mostrarHabitacionesContiguas(Lista habitacionesContiguas) {
        int cantH = habitacionesContiguas.longitud(), i = 1;
        if (cantH == 0) {
            System.out.println("La habitacion ingresada no tiene habitaciones contiguas");
        } else {
            while (i <= cantH) {
                Habitacion a = (Habitacion) habitacionesContiguas.recuperar(i);
                System.out.print(a.toString());
                i++;
                System.out.println(" Puntaje Necesario " + habitacionesContiguas.recuperar(i));
                i++;
            }
        }

    }

    public static void consultasSobreDesafios(TablaDeBusqueda infoD, MapeoAMuchos desafiosR, TDB infoE) {
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
                } else {
                    System.out.println("Lo sentimos el desafio ingresado no existe");
                }
                break;
            case 2:
                Equipo e = obtenerEquipo(infoE);
                if (e != null) {
                    mostrarDesafiosResueltosPorEquipo(e, desafiosR);
                } else {
                    System.out.println("Lo sentimos el equipo ingresado no existe");
                }
                break;
            case 3:
                mostrarDesafiosTipo(infoD);
                break;
            default:
                System.out.println("Respuesta ingresa incorrecta");
        }
    }

    public static Desafio obtenerDesafio(TablaDeBusqueda infoD) {
        System.out.println("Ingrese el puntaje del desafio");
        int puntaje = TecladoIn.readLineInt();
        Desafio d = (Desafio) infoD.obtenerDato(puntaje);
        return d;
    }

    public static void mostrarDesafiosResueltosPorEquipo(Equipo e, MapeoAMuchos desafiosR) {
        Lista desafiosRes = desafiosR.obtenerValores(e);
        if (desafiosRes.esVacia()) {
            System.out.println("El equipo ingresado todavia no ha completado ningun desafio");
        } else {
            System.out.println(desafiosRes.toString());
        }
    }

    public static void mostrarDesafiosTipo(TablaDeBusqueda infoD) {
        System.out.println("Ingrese el tipo");
        String tipo = TecladoIn.readLine();
        System.out.println("Ingrese el puntajeMinimo");
        int puntMin = TecladoIn.readLineInt();
        System.out.println("Ingrese el puntajeMaximo");
        int puntMax = TecladoIn.readLineInt();
        String cad = buscarDesafiosTipo(tipo, puntMin, puntMax, infoD);
        if (cad.equals("")) {
            System.out.println("No existe ningun desafio con los datos ingresados, por favor intentelo de nuevo");
        } else {
            System.out.println(cad);
        }
    }

    public static String buscarDesafiosTipo(String tipo, int puntMin, int puntMax, TablaDeBusqueda infoD) {
        Lista desafioPuntRango = infoD.listarRango(puntMin, puntMax);
        int i = 1, cantD = desafioPuntRango.longitud();
        String cad = "";
        while (i <= cantD) {
            int codigo = (int) desafioPuntRango.recuperar(i);
            Desafio d = (Desafio) infoD.obtenerDato(codigo);
            if (d.getTipo().equalsIgnoreCase(tipo)) {
                cad += d.toString() + "\n";
            }
            i++;
        }
        return cad;
    }

    public static void consultasSobreEquipos(Grafo plano, TDB infoE, MapeoAMuchos desafiosR, TablaDeBusqueda infoD, TablaDeBusqueda infoH, MapeoAMuchos puertasCompletadas) {
        Equipo e = obtenerEquipo(infoE);
        if (e != null) {
            if (e.getHabitacionActual() == 0) {
                consultasReducidasEquipo(e);
            } else {
                System.out.println("Seleccione que desea hacer");
                System.out.println("1-Mostrar la informacion de un equipo");
                System.out.println("2-Jugar un desafio");
                System.out.println("3-Dado un equipo y una habitacion, averiguar si puede pasar a la habitacion, si es posible avanza");
                System.out.println("4-Dado un equipo averiguar si puede salir");

                int n = TecladoIn.readLineInt();
                switch (n) {
                    case 1:
                        System.out.println(e.toString());
                        break;
                    case 2:
                        Desafio d = obtenerDesafio(infoD);
                        if (d != null) {
                            if (!jugarDesafio(e, d, desafiosR)) {
                                System.out.println("El equipo ya resolvio el desafio ingresado por favor intentelo de nuevo");
                            }
                        } else {
                            System.out.println("El desafio ingresado no existe por favor intentelo de nuevo");
                        }

                        break;
                    case 3:
                        Habitacion h2 = obtenerHabitacion(infoH);
                        if (h2 != null) {
                            if (sePuedePasar(puertasCompletadas, e, h2, plano, infoH)) {
                                System.out.println("El equipo puede pasar a la habitacion ingresada");
                            } else {
                                System.out.println("El equipo NO puede psar a la habitacion ingresada");
                            }
                        } else {
                            System.out.println("Lo sentimos la habitacion ingresada no existe,por favor intentelo de nuevo");
                        }

                        break;
                    case 4:
                        Habitacion hac = (Habitacion) infoH.obtenerDato(e.getHabitacionActual());
                        if (e.getPuntajeTotal() >= e.getPuntajeExigido() && hac.tieneSalida()) {
                            System.out.println("Puede salir");
                        } else {
                            System.out.println("No se puede salir de la casa");
                        }
                        break;
                    default:
                        System.out.println("Respuesta ingresada incorrecta");

                }
            }

        }
    }

    public static Equipo obtenerEquipo(TDB infoE) {
        System.out.println("Ingrese el nombre de el equipo");
        String codE = TecladoIn.readLine();
        Equipo e = (Equipo) infoE.obtenerDato(codE);
        return e;
    }

    public static void consultasReducidasEquipo(Equipo e) {
        System.out.println("Dado que el equipo no se encuentra en una habitacion de la casa, podra solo pedir que se muestre la info del mismo. \n"
                + "Si desea realiziar otro tipo de consultas por favor asegurese que el equipo se encuentre en una habitacion valida. ");
        System.out.println("Ingrese SI-Si desea mostrar la informacion,NO-Si desea volver al menu princripal");
        String res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("SI")) {
            System.out.println(e.toString());
        }
    }

    public static boolean jugarDesafio(Equipo e, Desafio d, MapeoAMuchos desafiosR) {
        boolean exito = desafiosR.asociar(e, d);
        //si ese desafio todavia no habia sido resuelto
        if (exito) {
            e.setPuntajeActual(e.getPuntajeActual() + d.getPuntajeAOtorgar());
            escribirLog("El equipo con nombre " + e.getNombre() + "jugo el desafio con puntaje " + d.getPuntajeAOtorgar());
        }
        return exito;
    }

    public static boolean sePuedePasar(MapeoAMuchos puertasCompletadas, Equipo e, Habitacion h2, Grafo plano, TablaDeBusqueda infoH) {
        int nroH = e.getHabitacionActual();
        Habitacion h = (Habitacion) infoH.obtenerDato(nroH);
        Puerta p = new Puerta(nroH, h2.getCodigo());
        boolean exito = false;

        Lista puertas = puertasCompletadas.obtenerValores(e);

        //si el equipo no paso por esa puerta todavia llama al metodo para averiguar si puede pasar
        if (puertas.localizar(p) < 0) {
            if (plano.puedePasar(h, h2, e.getPuntajeActual())) {
                e.setPuntajeTotal(e.getPuntajeTotal() + e.getPuntajeActual());
                e.setPuntajeActual(0);
                puertasCompletadas.asociar(e, p);
                exito = true;
            }
        } else {
            //si la puerta esta marcada como que ya se paso entonces verificamos que la misma siga existiendo por que quizas se ha borrado
            //si existe solo avanza sin sumar ningun punto
            if (plano.existeArco(h, h2)) {
                exito = true;
            }
        }
        if (exito) {
            e.setHabitacionActual(h2.getCodigo());
            //a la habitacion en la que se encontraba antes le restamos uno
            h.setCantEquiposOcupandola(h.getCantEquiposOcupandola() - 1);
            //a la nueva habitacion se le suma un equipo ocupandola
            h2.setCantEquiposOcupandola(h2.getCantEquiposOcupandola() + 1);
        }

        return exito;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        administrarSistema();
//        TablaDeBusqueda a= new TablaDeBusqueda ();
//        a.insertar(50, "Cincuenta");
//        a.insertar(20, "Veinte");
//        a.insertar(70, "SET");
//        a.insertar(60, "SESEN");
//        a.insertar(30,"TRIENTA");
//        a.insertar(80, "OCHENTA");
//        a.insertar(87, "OCHENTAYSIETE");
//        a.insertar(19,"DIEZ Y NUEVE");
//        a.insertar(25,"VEINTICINCO");
//        System.out.println (a.listarClaves());
//         System.out.println (a.listarDatos());
//         TablaDeBusqueda b=a.clone();
//         a.eliminar(50);
//         System.out.println ("ARBOL ORIGINAL SIN EL 50=>"+a.toString());
//        System.out.println ("ARBOL CLONE"+b.toString ());

    }

}
