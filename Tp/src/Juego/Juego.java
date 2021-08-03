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
        /*este metodo lee el txt que se encuentra en la direccion recibida por parametro y almacena el texto en la variable texto*/
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

    public static void leerArchivo(String texto, Grafo g, TablaDeBusqueda infoH, TablaDeBusqueda desafios, TDB equipos) {
        /*este metodo lee la cadena ingresada por parametro y crea los objetos segun lo indiquen las letras*/
        StringTokenizer st = new StringTokenizer(texto, "|");
        String cad = "";
        while (st.hasMoreTokens()) {
            cad = st.nextToken();
            switch (cad) {
                //luego de obtener el resultado de que letra se va a trabajar se llama al metodo propio para crear el objeto de esa clase
                case "H":
                    trabajarInfoHabitaciones(st, infoH, g);
                    break;
                case "D":
                    trabajarInfoDesafios(st, desafios);
                    break;
                case "E":
                    trabajarInfoEquipos(st, equipos,infoH);
                    break;
                case "P":
                    trabajarInfoPuertas(st, infoH, g);
                    break;
                default:

            }
        }
    }

    public static void trabajarInfoHabitaciones(StringTokenizer st, TablaDeBusqueda infoH, Grafo g) {
        String cod = st.nextToken();
        boolean exito = agregarHabitacion(cod, st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), infoH, g);
        if (exito) {
            escribirLog("Carga de Habitacion " + cod + "\n");
        }
    }

    public static void trabajarInfoDesafios(StringTokenizer st, TablaDeBusqueda desafios) {
        String cod = st.nextToken();
        boolean exito = agregarDesafio(cod, st.nextToken(), st.nextToken(), desafios);
        if (exito) {
            escribirLog("Carga de Desafio con puntaje  " + cod + "\n");
        }
    }

    public static void trabajarInfoEquipos(StringTokenizer st, TDB equipos,TablaDeBusqueda infoH) {
        String cod = st.nextToken();
        boolean exito = agregarEquipo(cod, st.nextToken(), st.nextToken(), equipos,infoH);
        if (exito) {
            escribirLog("Carga de Equipo  " + cod + "\n");
        }
    }

    public static void trabajarInfoPuertas(StringTokenizer st, TablaDeBusqueda infoH, Grafo g) {
        String cod = st.nextToken();
        String codD = st.nextToken();
        boolean exito = agregarPuerta(cod, codD, st.nextToken(), infoH, g);
        if (exito) {
            escribirLog("Puerta añadida, Origen " + cod + " Destino " + codD + "\n");
        }
    }

    public static void escribirLog(String texto) {
        /*este metodo escribi las operaciones realizas durante el juego en un archivo de texto*/
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\LOG.txt", true));
            writer.println(texto);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean agregarPuerta(String codOrigen, String codDestino, String puntaje, TablaDeBusqueda h, Grafo g) {
        /*este metodo crea una puerta*/
        int codO = Integer.parseInt(codOrigen);
        int codD = Integer.parseInt(codDestino);
        int pun = Integer.parseInt(puntaje);
        Object hab = h.obtenerDato(codO);
        Object habD = h.obtenerDato(codD);
        return g.insertarArco(hab, habD, pun);
    }

    public static boolean agregarHabitacion(String codigo, String nombre, String planta, String mCuadrados, String tieneSalida,
            TablaDeBusqueda h, Grafo g) {
        /*este metodo agrega una habitacion*/
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
        /*este metodo agrega un desafio a la tabla de busqueda*/
        int puntajeO = Integer.parseInt(puntajeAOtorgar);
        Desafio nuevoD = new Desafio(puntajeO, nombre, tipo);
        return d.insertar(puntajeO, nuevoD);

    }

    public static boolean agregarEquipo(String nombre, String puntajeExigido, String habitacionActual, TDB e,TablaDeBusqueda infoH) {
        /*este metodo agrega un desafio*/
        int puntE = Integer.parseInt(puntajeExigido);
        int habAct = Integer.parseInt(habitacionActual);
        if (habAct!=0){
             Habitacion hA=(Habitacion)infoH.obtenerDato(habAct);
             hA.setCantEquiposOcupandola(hA.getCantEquiposOcupandola()+1);
        }
        Equipo nuevoE = new Equipo(nombre, puntE, habAct);
        return e.insertar(nombre, nuevoE);
    }

    public static void administrarSistema() {
        /*este metodo administra el sistema de juego*/
        //creacion de las estructuras a utilizar durante el juego
        Grafo plano = new Grafo();
        TablaDeBusqueda infoH = new TablaDeBusqueda();
        TablaDeBusqueda infoD = new TablaDeBusqueda();
        TDB infoE = new TDB();
        MapeoAMuchos desafiosR = new MapeoAMuchos();
        MapeoAMuchos puertasCompletadas = new MapeoAMuchos();
        String direccion = ("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        //se muestra el menu hasta que el usuario desee finalizar
        int n = mostrarMenu();
        while (n >= 1 && n <= 6) {
            switch (n) {
                case 1:
                    //se cargan los datos almacenados en el txt
                    iniciarPrograma(direccion, plano, infoH, infoD, infoE);
                    break;
                case 2:
                    //altas bajas y modificaciones
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
                    //toString de todas las estructuras
                    mostrarSistema(plano, infoH, infoD, infoE, desafiosR, puertasCompletadas);
                    break;
                default:
                    System.out.println("El numero ingresado es incorrecto por favor intentelo de nuevo");
            }
            n = mostrarMenu();
        }
    }

    public static int mostrarMenu() {
        /*este metodo le muestra el menu al usuario y guarda la opcion ingresada, la retorna al metodo administrarSistema*/
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

    public static void iniciarPrograma(String direccion, Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE) {
        /*este metodo inicia el proceso de lectura del archivo con los datos de las habitaciones, las puertas, los equipos y los desafios*/
        String texto = leerTxt("C:\\Users\\Anto\\Documents\\UNCO\\Tpfinal\\datos.txt");
        leerArchivo(texto, plano, infoH, infoD, infoE);

    }

    public static void mostrarSistema(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR, MapeoAMuchos puertasSuperadas) {
        /*este metodo muestra un menu para elegir que estructura desea ver el usuario, luego la muestra*/
        System.out.println("Ingrese que desea ver");
        System.out.println("P-Plano de la casa");
        System.out.println("H-Informacion de las habitaciones");
        System.out.println("D-Informacion de los desafios");
        System.out.println("E-Informacion de los equipos");
        System.out.println("R-Los desafios resueltos por cada equipo");
        System.out.println("C-Las puertas por las que paso cada equipo");
        char res = TecladoIn.readLineNonwhiteChar();
        switch (res) {
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
            case 'C':
                System.out.println(puertasSuperadas.toString());
                break;
            default:
                System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");
        }

    }

    public static void ABM(Grafo plano, TablaDeBusqueda infoH, TablaDeBusqueda infoD, TDB infoE, MapeoAMuchos desafiosR) {
        /*este metodo muestra un menu para elegir con que operar para las bajas altas y modificaciones*/
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
                ABMEquipos(infoE, desafiosR, infoH, infoD);
                break;
            case 'P':
                ABMPuertas(plano, infoH);
                break;
            default:
                System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");

        }
    }

    public static void ABMHabitaciones(Grafo plano, TablaDeBusqueda infoH) {
        /*este metodo le muestra al usuario la opcion de crear una nueva habitacion o de trabajar con alguna ya existente*/
        switch (menuABMHabitaciones()) {
            case 'N':
                administrarCreacionHabitacion(plano, infoH);
                break;
            case 'E':
                administrarEliminacionHabitacion(plano, infoH);
                break;
            case 'M':
                administrarModificacionesHabitaciones(plano, infoH);
                break;
            default:
                System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");

        }
    }

    public static char menuABMHabitaciones() {
        System.out.println("Ingrese N- si desea crear una habitacion");
        System.out.println("Ingrese E- si desea eliminar una habitacion");
        System.out.println("Ingrese M- si desea modificar una habitacion");
        char n = TecladoIn.readLineNonwhiteChar();
        return n;
    }

    public static void administrarCreacionHabitacion(Grafo plano, TablaDeBusqueda infoH) {
        if (!crearHabitacion(plano, infoH)) {
            System.out.println("ERROR.La habitacion ya existe, por favor intentelo de nuevo ");
        }
    }

    public static boolean crearHabitacion(Grafo plano, TablaDeBusqueda infoH) {
        /*este metodo crea una habitacion pidiendole todos los datos al usuario*/
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

    public static void administrarEliminacionHabitacion(Grafo plano, TablaDeBusqueda infoH) {
        System.out.println("Ingrese el codigo de la habitacion que desea eliminar");
        int cod = TecladoIn.readLineInt();
        Habitacion hEliminar = (Habitacion) infoH.obtenerDato(cod);
        if (hEliminar != null) {
            if (hEliminar.getCantEquiposOcupandola() == 0) {
                eliminarHabitacion(plano, infoH, hEliminar);
            } else {
                System.out.println("LO sentimos la habitacion tiene equipos ocupandola por lo tanto no se puede eliminar");
            }
        } else {
            System.out.println("Lo sentimos la habitacion ingresada no existe por favor intentelo de nuevo");
        }

    }

    public static void eliminarHabitacion(Grafo plano, TablaDeBusqueda infoH, Habitacion hEliminar) {
        /*este metodo elimina una habitacion*/
        infoH.eliminar(hEliminar.getCodigo());
        plano.eliminarVertice(hEliminar);
        escribirLog("Se elimino la habitacion con codigo " + hEliminar.getCodigo() + "\n");
    }

    public static void administrarModificacionesHabitaciones(Grafo plano, TablaDeBusqueda infoH) {
        Habitacion h = obtenerHabitacion(infoH);
        if (h != null) {
            modificacionesHabitaciones(h);
        } else {
            System.out.println("Lo sentimos la habitacion ingresada no existe, intentelo de nuevo");
        }
    }

    public static void modificacionesHabitaciones(Habitacion h) {
        /*este metodo le muestra al usuario una menu sobre que desea modificar de la habitacion ingresada
        luego trabaja segun la opcion seleccionada
         */
        switch (menuModificacionesHabitaciones()) {
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

    public static char menuModificacionesHabitaciones() {
        System.out.println("Ingrese E- si desea eliminar la habitacion");
        System.out.println("Ingrese N- si desea modificar el nombre");
        System.out.println("Ingrese P- si desea modificar el nro de planta");
        System.out.println("Ingrese M-si desea modificar la cantidad de m cuadrados");
        System.out.println("Ingrese S-si desea cambiar el atributo de salida");
        char m = TecladoIn.readLineNonwhiteChar();
        return m;
    }

    public static void cambiarNombre(Habitacion h) {
        /*este metodo le pide al usuario el nuevo nombre para la habitacion recibida por parametro*/
        System.out.println("Ingrese el nuevo nombre");
        String res = TecladoIn.readLine();
        h.setNombre(res);
        escribirLog("Se le modifico el nombre a la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void cambiarNroPlanta(Habitacion h) {
        /*este metodo le pide al usuario el nuevo nro de planta para la habitacion recibida por parametro*/
        System.out.println("Ingrese el nuevo nro de planta");
        int nro = TecladoIn.readLineInt();
        h.setPlanta(nro);
        escribirLog("Se modifico el nro de planta de la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void cambiarCantMCuadrados(Habitacion h) {
        /*este metodo le pide al usuario la nueva cantMCuadrados para la habitacion recibida por parametro*/
        System.out.println("Ingrese la cantidad de m cuadrados");
        Double mC = TecladoIn.readLineDouble();
        h.setmCuadrados(mC);
        escribirLog("Se modificaron los mCuadradros de la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void cambiarTieneSalida(Habitacion h) {
        /*este metodo le pide al usuario el nuevo valor para el atributo tiene salida para la habitacion recibida por parametro*/
        System.out.println("Ingrese el nuevo valor para el atributo tieneSalida");
        boolean tieneS = TecladoIn.readLineBoolean();
        h.setTieneSalida(tieneS);
        escribirLog("Se le modifico el atributo tieneSalida a la habitacion con codigo " + h.getCodigo() + "\n");
    }

    public static void ABMDesafios(TablaDeBusqueda infoD) {
        /*este metodo le muestra al usuario la opcion de crear un nuevo desafio o de trabajar con alguno ya existente*/
        switch (menuABMDesafios()) {
            case 'N':
                administrarCreacionDesafio(infoD);
                break;
            case 'E':
                administrarEliminacionDesafio(infoD);
                break;
            case 'M':
                administrarModificacionesDesafios(infoD);
                break;
            default:
                System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");
        }
    }

    public static char menuABMDesafios() {
        System.out.println("Ingrese N- si desea crear un nuevo desafio");
        System.out.println("Ingrese E- si desea eliminar un desafio");
        System.out.println("Ingrese M- si desea modificar un desafio");
        char n = TecladoIn.readLineNonwhiteChar();
        return n;
    }

    public static void administrarCreacionDesafio(TablaDeBusqueda infoD) {
        if (!crearDesafio(infoD)) {
            System.out.println("ERROR.El desafio ya existe, por favor intentelo de nuevo ");
        }
    }

    public static boolean crearDesafio(TablaDeBusqueda infoD) {
        /*se crea un nuevo desafio*/
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

    public static void administrarEliminacionDesafio(TablaDeBusqueda infoD) {
        System.out.println("Ingrese el puntaje del desafio que desea eliminar");
        int n = TecladoIn.readLineInt();
        if (!eliminarDesafio(infoD, n)) {
            System.out.println("ERROR.El desafio no existe, por favor intentelo de nuevo ");
        }
    }

    public static boolean eliminarDesafio(TablaDeBusqueda infoD, int n) {
        boolean exito = infoD.eliminar(n);
        if (exito) {
            escribirLog("Se elimino el desafio con codigo " + n + "\n");
        }
        return exito;
    }

    public static void administrarModificacionesDesafios(TablaDeBusqueda infoD) {
        Desafio d = obtenerDesafio(infoD);
        if (d != null) {
            modificacionesDesafios(infoD, d);
        } else {
            System.out.println("ERROR.El desafio no existe, por favor intentelo de nuevo ");
        }
    }

    public static void modificacionesDesafios(TablaDeBusqueda infoD, Desafio d) {
        /*este metodo le muestra al usuario una menu sobre que desea modificar del desafio ingresado
        luego trabaja segun la opcion seleccionada
         */
        switch (menuModificacionesDesafios()) {
            case 'N':
                modificarNombreDesafio(d);
                break;
            case 'T':
                modificarTipoDesafio(d);
                break;
            default:
                System.out.println("La letra ingresada es incorrecta");
        }
    }

    public static char menuModificacionesDesafios() {
        System.out.println("Ingrese N- si desea modificar el nombre");
         System.out.println("Ingrese T- si desea modificar el tipo");
        char m = TecladoIn.readLineNonwhiteChar();
        return m;
    }

    public static void modificarNombreDesafio(Desafio d) {
        System.out.println("Ingrese el nuevo nombre");
        String res = TecladoIn.readLine();
        d.setNombre(res);
        escribirLog("Se cambio el nombre del desafio con puntaje " + d.getPuntajeAOtorgar() + "\n");
    }

    public static void modificarTipoDesafio(Desafio d) {
        System.out.println("Ingrese el tipo");
        String tipo = TecladoIn.readLine();
        d.setTipo(tipo);
        escribirLog("Se cambio el tipo del desafio con puntaje " + d.getPuntajeAOtorgar() + "\n");
    }

    public static void ABMEquipos(TDB infoE, MapeoAMuchos desafiosR, TablaDeBusqueda infoH, TablaDeBusqueda infoD) {
        /*este metodo le muestra al usuario la opcion de crear un nuevo equipo o de trabajar con alguno ya existente*/
        switch (menuABMEquipos()) {
            case 'N':
                administrarCreacionEquipo(infoE, infoH);
                break;
            case 'E':
                administrarEliminacionEquipo(infoE, infoH);
                break;
            case 'M':
                administrarModificacionesEquipo(infoE, infoD, desafiosR, infoH);
                break;
            default:
                System.out.println("La letra ingresada es invalida, por favor intentelo de nuevo");
        }
    }

    public static char menuABMEquipos() {
        System.out.println("Ingrese N- si desea crear un nuevo equipo");
        System.out.println("Ingrese E- si desea eliminar un equipo");
        System.out.println("Ingrese M- si desea modificar un equipo");
        char res = TecladoIn.readLineNonwhiteChar();
        return res;
    }

    public static void administrarCreacionEquipo(TDB infoE, TablaDeBusqueda infoH) {
        if (!crearEquipo(infoE, infoH)) {
            System.out.println("Lo sentimos los datos ingresados fueron incorrectos, por favor intentelo de nuevo ");
        }
    }

    public static boolean crearEquipo(TDB infoE, TablaDeBusqueda infoH) {
        /*se crea un nuevo equipo*/
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
                Habitacion hActual =(Habitacion) infoH.obtenerDato(habitacionAct);
                if (hActual!=null) {
                    nuevoEquipo = new Equipo(nombre, puntaje, habitacionAct);
                    hActual.setCantEquiposOcupandola(hActual.getCantEquiposOcupandola()+1);
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

    public static void administrarEliminacionEquipo(TDB infoE, TablaDeBusqueda infoH) {
        Equipo e = obtenerEquipo(infoE);
        Habitacion h = (Habitacion) infoH.obtenerDato(e.getHabitacionActual());
        eliminarEquipo(e, infoE, h);
    }

    public static void eliminarEquipo(Equipo e, TDB infoE, Habitacion hActual) {
        /*se elimina un equipo*/
        String nombre = e.getNombre();
        infoE.eliminar(nombre);
        if (e.getHabitacionActual() != 0 && hActual != null) {
            hActual.setCantEquiposOcupandola(hActual.getCantEquiposOcupandola() - 1);
            escribirLog("Se elimino el equipo con nombre " + nombre + "\n");
        }
    }

    public static void administrarModificacionesEquipo(TDB infoE, TablaDeBusqueda infoD, MapeoAMuchos desafiosR, TablaDeBusqueda infoH) {
        Equipo e = obtenerEquipo(infoE);
        if (e != null) {
            modificacionesEquipos(e, infoD, desafiosR, infoH);
        } else {
            System.out.println("ERROR.El equipo no existe, por favor intentelo de nuevo ");
        }

    }

    public static void modificacionesEquipos(Equipo e, TablaDeBusqueda infoD, MapeoAMuchos desafiosR, TablaDeBusqueda infoH) {
        /*este metodo le muestra al usuario una menu sobre que desea modificar del equipo ingresado
        luego trabaja segun la opcion seleccionada
         */
        switch (menuModificacionesEquipos()) {
            case 'P':
                modificarPuntajeExigido(e);
                break;
            case 'T':
                modificarPuntajeTotal(e);
                break;
            case 'H':
                administrarModifacionHabitacionActual(e, infoH);
                break;
            case 'A':
                modificarPuntajeHabitacionActual(e);
                break;
            case 'D':
                administrarAdicionDesafioResuelto(e, infoD, desafiosR);
                break;
            default:
                System.out.println("Respuesta incorrecta");
        }
    }

    public static char menuModificacionesEquipos() {
        System.out.println("Ingrese P- si desea modificar el puntaje exigido para salir de la casa");
        System.out.println("Ingrese T-si desea modificar el puntaje total");
        System.out.println("Ingrese H-si desea cambiar la habitacion actual donde se encuentra el equipo");
        System.out.println("Ingrese A-si desea modificar el puntaje actual en la habitacion donde esta posicionado");
        System.out.println("Ingrese D-si desea añadir un desafio resuelto");
        char m = TecladoIn.readLineNonwhiteChar();
        return m;
    }

    public static void modificarPuntajeExigido(Equipo e) {
        /*se le modifica el puntaje exigido para salir de la casa al equipo e*/
        System.out.println("Ingrese el nuevo puntaje");
        int cant = TecladoIn.readLineInt();
        e.setPuntajeExigido(cant);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico el puntaje exigido para salir de la casa" + "\n");
    }

    public static void modificarPuntajeTotal(Equipo e) {
        /*se le modifica el puntaje total al equipo e*/
        System.out.println("Ingrese el nuevo puntaje total");
        int puntT = TecladoIn.readLineInt();
        e.setPuntajeTotal(puntT);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico el puntaje total" + "\n");
    }

    public static void administrarModifacionHabitacionActual(Equipo e, TablaDeBusqueda infoH) {
        Habitacion nuevaHabActual = obtenerHabitacion(infoH);
        if (nuevaHabActual != null) {
            modificarHabitacionActual(e, nuevaHabActual, infoH);
        } else {
            System.out.println("Lo sentimos la habitacion ingresada es invalida por favor intente de nuevo");
        }
    }

    public static void modificarHabitacionActual(Equipo e, Habitacion nuevaHabActual, TablaDeBusqueda infoH) {
        /*se le modifica la habitacion actual al equipo e*/
        Habitacion habActual = (Habitacion) infoH.obtenerDato(e.getHabitacionActual());
        if (e.getHabitacionActual() != 0) {
            //si el equipo se encuentra en una habitacion valida entonces le seteamos la cantidad de equipos que la estan ocupadno
            habActual.setCantEquiposOcupandola(habActual.getCantEquiposOcupandola() - 1);
        }
        //cambiamos al equipo de habitacion
        e.setHabitacionActual(habActual.getCodigo());
        nuevaHabActual.setCantEquiposOcupandola(nuevaHabActual.getCantEquiposOcupandola() + 1);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico la habitacion en la que se encuentra actualmente" + "\n");

    }

    public static void modificarPuntajeHabitacionActual(Equipo e) {
        /*se le modifica el puntaje actual en la habitacion al equipo e*/
        System.out.println("Ingrese el nuevo puntaje actual en la habitacion");
        int puntA = TecladoIn.readLineInt();
        e.setPuntajeActual(puntA);
        escribirLog("Al equipo con nombre " + e.getNombre() + " se le modifico el puntaje actual dentro de la habitacion" + "\n");
    }

    public static void administrarAdicionDesafioResuelto(Equipo e, TablaDeBusqueda infoD, MapeoAMuchos desafiosR) {
        if (e.getHabitacionActual() != 0) {
            if (!añadirDesafioResuelto(e, infoD, desafiosR)) {
                System.out.println("Lo sentimos los datos ingresados fueron incorrectos por favor intentelo de nuevo");
            }
        } else {
            System.out.println("El equipo no se encuentra en ninguna habitacion de la casa por lo tanto no puede jugar, por favor intentelo de nuevo");
        }
    }

    public static boolean añadirDesafioResuelto(Equipo e, TablaDeBusqueda infoD, MapeoAMuchos desafiosR) {
        /*al equipo e se le añada un desafo resuelto*/
        Desafio d = obtenerDesafio(infoD);
        boolean exito = false;
        if (d != null) {
            //si ya no habian jugado ese desafio
            if (desafiosR.asociar(e, d)) {
                //se asignan los puntos correspondientes al desafio
                e.setPuntajeActual(d.getPuntajeAOtorgar());
                exito = true;
                escribirLog("El equipo con nombre " + e.getNombre() + "resolvio el desafio con puntaje " + d.getPuntajeAOtorgar() + "\n");
            }
        }
        return exito;
    }

    public static void ABMPuertas(Grafo plano, TablaDeBusqueda infoH) {
        /*este metodo le muestra al usuario la opcion de crear una nueva puerta o de trabajar con alguna ya existente*/
        char eleccion = menuABMPuertas();
        Habitacion origen = obtenerHabitacion(infoH);
        Habitacion destino = obtenerHabitacion(infoH);
        switch (eleccion) {
            case 'N':
                administrarCreacionPuerta(origen, destino, plano);
                break;
            case 'E':
                administrarEliminacionPuerta(origen, destino, plano);
                break;
            case 'M':
                administrarModificacionPuerta(origen, destino, plano);
                break;
            default:
                System.out.println("La letra ingresada es incorrecta por favor intentelo de nuevo");
        }

    }

    public static char menuABMPuertas() {
        System.out.println("Ingrese N- si desea crear una nueva puerta");
        System.out.println("Ingrese E- si desea eliminar una puerta");
        System.out.println("Ingrese M- si desea modificar una puerta");
        char res = TecladoIn.readLineNonwhiteChar();
        return res;
    }

    public static void administrarCreacionPuerta(Habitacion origen, Habitacion destino, Grafo plano) {
        if (origen != null && destino != null) {
            System.out.println("Ingrese el puntaje exigido para pasar de la habitacion origen a la de destino");
            int puntaje = TecladoIn.readLineInt();
            crearPuerta(origen, destino, puntaje, plano);
        } else {
            System.out.println("Los datos ingresados son invalidos por favor intentelo de nuevo");
        }
    }

    public static boolean crearPuerta(Habitacion habO, Habitacion habD, int puntaje, Grafo plano) {
        /*se crea una puerta segun los datos ingresados*/
        boolean encontrado = plano.insertarArco(habO, habD, puntaje);
        escribirLog("Se agrego nueva puerta entre la habitacion con codigo " + habO.getCodigo() + " y la habitacion con codigo " + habD.getCodigo() + "puntaje necesario " + puntaje + "\n");
        return encontrado;

    }

    public static void administrarEliminacionPuerta(Habitacion origen, Habitacion destino, Grafo plano) {
        if (origen != null && destino != null) {
            eliminarHabitacion(plano, origen, destino);
        } else {
            System.out.println("Los datos ingresados son invalidos por favor intentelo de nuevo");
        }
    }

    public static void eliminarHabitacion(Grafo plano, Habitacion habO, Habitacion habD) {
        boolean exito = plano.eliminarArco(habO, habD);
        if (exito) {
            escribirLog("Se elimino la puerta entre la habitacion con codigo " + habO.getCodigo() + "y la habitacion con codigo " + habD.getCodigo() + "\n");
        }

    }

    public static void administrarModificacionPuerta(Habitacion origen, Habitacion destino, Grafo plano) {
        if (origen != null && destino != null) {
            System.out.println("Desea modificar el puntaje para pasar de una habitacion a la otra?SI/NO");
            String res = TecladoIn.readLine();
            if (res.equalsIgnoreCase("SI")) {
                modificacionPuntaje(plano, origen, destino);
            }
        } else {
            System.out.println("Los datos ingresados son invalidos, por favor intentelo de nuevo");
        }
    }

    public static void modificacionPuntaje(Grafo plano, Habitacion habO, Habitacion habD) {
        System.out.println("Ingrese el nuevo puntaje");
        int nro = TecladoIn.readLineInt();
        boolean exito = plano.cambiarPeso(habO, habD, nro);
        if (exito) {
            escribirLog("Se modifo el puntaje en la puerta entre la habitacion con codigo " + habO.getCodigo() + " y la habitacion con codigo " + habD.getCodigo() + "\n");
        }
    }

    //Consultas sobre habitaciones
    public static void consultasSobreHabitaciones(TablaDeBusqueda infoH, Grafo plano) {
        /*este metodo le muestra al usuario un menu para hacer consultas sobre las habitaciones
        luego trabaja segun la opcion seleccionada
         */
        int n = menuConsultasHabitaciones();
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
                    administrarConsultaEsPosibleLlegar(plano, h, infoH);
                    break;
                case 4:
                    administrarConsultaFormasDePasar(h, infoH, plano);
                default:
                    System.out.println("La respuesta ingresada es incorrecta");
            }
        } else {
            System.out.println("Lo sentimos la habitacion ingresada no existe");
        }

    }

    public static int menuConsultasHabitaciones() {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Mostrar habitacion");
        System.out.println("2-Dado una habitacion mostrar las habitaciones contiguas y el puntaje necesario para pasar a ellas");
        System.out.println("3-Dadas dos habitaciones y una cantidad de puntaje averiguar si es posible llegar de la una a la otra acumulando esa cant de puntaje");
        System.out.println("4-Dadas 3 habitaciones y una cantidad de puntaje, mostrar las formas de pasar de una a otra sin pasar por la 3 habitacion y sin superar el puntaje");
        int n = TecladoIn.readLineInt();
        return n;
    }

    public static Habitacion obtenerHabitacion(TablaDeBusqueda infoH) {
        /*este metodo le pido al usuario el codigo de una habitacion y luego la busca en la estructura correspondiente
        si la encuentra la retorna y sino retorna null
         */
        System.out.println("Ingrese el codigo de habitacion");
        int cod = TecladoIn.readLineInt();
        Habitacion h = (Habitacion) infoH.obtenerDato(cod);
        return h;
    }

    public static void administrarConsultaEsPosibleLlegar(Grafo plano, Habitacion h, TablaDeBusqueda infoH) {
        Habitacion h2 = obtenerHabitacion(infoH);
        if (h2 != null) {
            esPosibleLlegar(plano, h, h2);
        } else {
            System.out.println("La habitacion ingresada no existe por favor intentelo de nuevo");
        }
    }

    public static void esPosibleLlegar(Grafo plano, Habitacion h, Habitacion h2) {
        /*este metodo le muestra al usuario si es posible ir desde h a h2 sin superar el puntaje que se ingresa*/
        System.out.println("Ingrese el puntaje");
        int pun = TecladoIn.readLineInt();
        //llamada al metodo que buscara si es posible llegar bajo las condiciones dades
        if (plano.esPosibleLlegar(h, h2, pun)) {
            System.out.println("Si es posible llegar");
        } else {
            System.out.println("No es posible llegar");
        }
    }

    public static void administrarConsultaFormasDePasar(Habitacion h, TablaDeBusqueda infoH, Grafo plano) {
        Habitacion h2 = obtenerHabitacion(infoH);
        Habitacion h3 = obtenerHabitacion(infoH);
        if (h2 != null && h3 != null) {
            formasDePasar(plano, h, h2, h3);
        } else {
            System.out.println("Los datos ingresados fueron incorrectos. Por favor intentelo de nuevo");
        }
    }

    public static void formasDePasar(Grafo plano, Habitacion h, Habitacion h2, Habitacion h3) {
        /*este metodo muestra las formas de psar de h a h2 sin pasar por h3 y sin superar el puntaje ingresado
        para ello llama un metodo auxiliar 
         */
        System.out.println("Ingrese el puntaje");
        int pun = TecladoIn.readLineInt();
        mostrarCaminos(plano.sinPasarPor(h, h2, h3, pun));
    }

    public static void mostrarCaminos(Cola caminos) {
        /*este metodo muestra todos los caminos almacenados en la cola*/
        if (caminos.esVacia()) {
            System.out.println("No existen caminos bajo las condiciones indicadas");
        } else {
            //mientras haya caminos en la cola
            while (!caminos.esVacia()) {
                Lista caminoActual = (Lista) caminos.obtenerFrente();
                int i = 1, maxLongitud = caminoActual.longitud();
                //se itera sobre un camino mostrando la informacion
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

    public static void mostrarHabitacionesContiguas(Lista habitacionesContiguas) {
        /*este metodo muestra la lista de habitaciones contiguas recibidas por parametro*/
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
        /*este metodo le muestra al usuario un menu para hacer consultas sobre los desafios
        luego trabaja segun la opcion seleccionada
         */
        switch (menuConsultasDesafios()) {
            case 1:
                administrarMuestraDeDesafio(infoD);
                break;
            case 2:
                administrarMuestraDesafiosRPorEquipo(infoE, desafiosR);

                break;
            case 3:
                administrarMuestraDesafiosTipo(infoD);
                break;
            default:
                System.out.println("Respuesta ingresa incorrecta");
        }
    }

    public static int menuConsultasDesafios() {
        System.out.println("Ingrese que desea hacer");
        System.out.println("1-Mostrar un desafio");
        System.out.println("2-Dado un equipo mostrar los desafios que resolvio");
        System.out.println("3-Dado dos puntajes y un tipo, mostrar los desafios de ese tipo y en el rango de los puntajes");

        int n = TecladoIn.readLineInt();
        return n;
    }

    public static Desafio obtenerDesafio(TablaDeBusqueda infoD) {
        /*este metodo le pido al usuario el puntaje de un desafio y luego lo busca en la estructura correspondiente
        si lo encuentra lo retorna y sino retorna null
         */
        System.out.println("Ingrese el puntaje del desafio");
        int puntaje = TecladoIn.readLineInt();
        Desafio d = (Desafio) infoD.obtenerDato(puntaje);
        return d;
    }

    public static void administrarMuestraDeDesafio(TablaDeBusqueda infoD) {
        /*este metodo administra la muestra de un desafio*/
        Desafio d = obtenerDesafio(infoD);
        if (d != null) {
            System.out.println(d.toString());
        } else {
            System.out.println("Lo sentimos el desafio ingresado no existe");
        }
    }

    public static void administrarMuestraDesafiosRPorEquipo(TDB infoE, MapeoAMuchos desafiosR) {
        /*este metodo administra la muestra de desafios resueltos por equipo*/
        Equipo e = obtenerEquipo(infoE);
        if (e != null) {
            mostrarDesafiosResueltosPorEquipo(e, desafiosR);
        } else {
            System.out.println("Lo sentimos el equipo ingresado no existe");
        }
    }

    public static void mostrarDesafiosResueltosPorEquipo(Equipo e, MapeoAMuchos desafiosR) {
        /*este metodo muestra los desafios que resolvio el equipo recibido por parametro*/
        Lista desafiosRes = desafiosR.obtenerValores(e);
        if (desafiosRes.esVacia()) {
            System.out.println("El equipo ingresado todavia no ha completado ningun desafio");
        } else {
            System.out.println(desafiosRes.toString());
        }
    }

    public static void administrarMuestraDesafiosTipo(TablaDeBusqueda infoD) {
        /*este metodo muestra los desafios de un cierto tipo y que estan en el rango [puntMin,puntMax]*/
        System.out.println("Ingrese el tipo");
        String tipo = TecladoIn.readLine();
        System.out.println("Ingrese el puntajeMinimo");
        int puntMin = TecladoIn.readLineInt();
        System.out.println("Ingrese el puntajeMaximo");
        int puntMax = TecladoIn.readLineInt();
        String cad = buscarDesafiosTipo(tipo, puntMin, puntMax, infoD);
        //si no retorna nada el metodo es por que no hay desafios segun lo que se ingreso
        if (cad.equals("")) {
            System.out.println("No existe ningun desafio con los datos ingresados, por favor intentelo de nuevo");
        } else {
            System.out.println(cad);
        }
    }

    public static String buscarDesafiosTipo(String tipo, int puntMin, int puntMax, TablaDeBusqueda infoD) {
        /*este metodo busca los desafios de un cierto tipo y que estan en el rango [puntMin,puntMax]*/
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
        /*este metodo le muestra al usuario un menu para hacer consultas sobre los equipos
        luego trabaja segun la opcion seleccionada
         */
        Equipo e = obtenerEquipo(infoE);
        if (e != null) {
            if (e.getHabitacionActual() == 0) {
                consultasReducidasEquipo(e);
            } else {
                switch (consultasRegularesEquipo()) {
                    case 1:
                        System.out.println(e.toString());
                        break;
                    case 2:
                        ejecucionJuegoDesafio(infoD, desafiosR, e);
                        break;
                    case 3:
                        ejecucionConsultaSePuedePasar(puertasCompletadas, e, plano, infoH);
                        break;
                    case 4:
                        puedeSalir(infoH, e);
                        break;
                    default:
                        System.out.println("Respuesta ingresada incorrecta");

                }
            }

        }
        else{
            System.out.println ("El equipo ingresado no existe por favor intentelo de nuevo");
        }
    }

    public static Equipo obtenerEquipo(TDB infoE) {
        /*este metodo le pido al usuario el nombre de un equipo y luego lo busca en la estructura correspondiente
        si lo encuentra lo retorna y sino retorna null
         */
        System.out.println("Ingrese el nombre de el equipo");
        String codE = TecladoIn.readLine();
        Equipo e = (Equipo) infoE.obtenerDato(codE);
        return e;
    }

    public static int consultasRegularesEquipo() {
        System.out.println("Seleccione que desea hacer");
        System.out.println("1-Mostrar la informacion del equipo");
        System.out.println("2-Jugar un desafio");
        System.out.println("3-Dado el equipo y una habitacion, averiguar si puede pasar a la habitacion, si es posible avanza");
        System.out.println("4-Dado el equipo averiguar si puede salir");
        int n = TecladoIn.readLineInt();
        return n;
    }

    public static void consultasReducidasEquipo(Equipo e) {
        /*este metodo le muestra al usuario consultas reducidas que puede realizar si no esta en una habitacion valida*/
        System.out.println("Dado que el equipo no se encuentra en una habitacion de la casa, podra solo pedir que se muestre la info del mismo. \n"
                + "Si desea realizar otro tipo de consultas por favor asegurese que el equipo se encuentre en una habitacion valida. ");
        System.out.println("Ingrese SI-Si desea mostrar la informacion,NO-Si desea volver al menu principal");
        String res = TecladoIn.readLine();
        if (res.equalsIgnoreCase("SI")) {
            System.out.println(e.toString());
        }
    }

    public static void ejecucionJuegoDesafio(TablaDeBusqueda infoD, MapeoAMuchos desafiosR, Equipo e) {
        Desafio d = obtenerDesafio(infoD);
        if (d != null) {
            if (!jugarDesafio(e, d, desafiosR)) {
                System.out.println("El equipo ya resolvio el desafio ingresado por favor intentelo de nuevo");
            } else {
                escribirLog("El equipo con nombre " + e.getNombre() + " jugo el desafio con puntaje " + d.getPuntajeAOtorgar());
            }
        } else {
            System.out.println("El desafio ingresado no existe por favor intentelo de nuevo");
        }
    }

    public static boolean jugarDesafio(Equipo e, Desafio d, MapeoAMuchos desafiosR) {
        /*este metodo juega un desafio y lo registra en los desafios resueltos por equipo
        devuelve true si el desafio no habia sido resuelto por el equipo 
        false en el caso contrario*/
        boolean exito = desafiosR.asociar(e, d);
        //si ese desafio todavia no habia sido resuelto
        if (exito) {
            e.setPuntajeActual(e.getPuntajeActual() + d.getPuntajeAOtorgar());
        }
        return exito;
    }

    public static void ejecucionConsultaSePuedePasar(MapeoAMuchos puertasCompletadas, Equipo e, Grafo plano, TablaDeBusqueda infoH) {
        Habitacion h2 = obtenerHabitacion(infoH);
        if (h2 != null) {
            if (sePuedePasar(puertasCompletadas, e, h2, plano, infoH)) {
                System.out.println("El equipo puede pasar a la habitacion ingresada");
            } else {
                System.out.println("El equipo NO puede pasar a la habitacion ingresada");
            }
        } else {
            System.out.println("Lo sentimos la habitacion ingresada no existe,por favor intentelo de nuevo");
        }
    }

    public static boolean sePuedePasar(MapeoAMuchos puertasCompletadas, Equipo e, Habitacion h2, Grafo plano, TablaDeBusqueda infoH) {
        /*este metodo averigua si se puede pasar desde la habitacion actual hasta la h2, sin pasar por h3 y sn superar el puntaje ingresado
        devuelve true si se puede, false si no se puede*/
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

    public static void puedeSalir(TablaDeBusqueda infoH, Equipo e) {
        Habitacion hac = (Habitacion) infoH.obtenerDato(e.getHabitacionActual());
        //si el puntaje total alcanza o si el puntajeActual mas el total alcanza y estan en una habitacion que tiene salida se le permite salir 
        if (hac.tieneSalida()) {
            if ((e.getPuntajeTotal() >= e.getPuntajeExigido()) || (e.getPuntajeTotal() + e.getPuntajeActual() >= e.getPuntajeExigido())) {
                System.out.println("Puede salir");
            } else {
                System.out.println("No se puede salir de la casa, el puntaje alcanzado no es suficiente, por favor continue jugando");
            }
        } else {
            System.out.println("No se puede salir de la casa, la habitacion en la que se encuentra no tiene salida al exterior");
        }
    }

    public static void main(String[] args) {
        administrarSistema();
       

    }

}
