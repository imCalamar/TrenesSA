/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PropiasTP;

import DiccionarioAVL.Diccionario;
import Utiles.TecladoIn;
import grafos.GrafoEtiquetado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import lineales.dinamicas.Lista;

/**
 *
 * @author CalamarCoder
 */
public class prueba {

    private static GrafoEtiquetado grafoMapa = new GrafoEtiquetado();
    private static Diccionario avlEstaciones = new Diccionario();
    private static Diccionario avlTrenes = new Diccionario();
    private static HashMap hashLineas = new HashMap();

    private static File txtEstaciones = new File("leeEstaciones.txt");
    private static File txtLineas = new File("leeLineas.txt");
    private static File txtMapaRieles = new File("leeMapaRieles.txt");
    private static File txtTrenes = new File("leeTrenes.txt");
    private static File txtCambios = new File("archivoLOG.txt");

    public static void main(String[] args) throws FileNotFoundException {
        int i;
        altaEstaciones();
        altaMapa();
        altaLineas();
        altaTren();
        do {
            i = menuPrincipal();
            switch (i) {
                case 1 ->
                    ABMEstaciones();
                case 2 ->
                    ABMTrenes();
                case 3 ->
                    ABMRieles();
                case 4 ->
                    ABMLineas();
                case 5 ->
                    SubMenuTrenes();
                case 6 ->
                    SubMenuViajes();
                case 7 ->
                    System.out.println("finalizando ejecucion...");
                default ->
                    System.out.println("ingresar opcion del 1 al 5");
            }
        } while (i != 7);
    }

    public static int menuPrincipal() {
        int res;
        System.out.println("""
                           Seleccione una de las opciones : 
                           1- ABM (Altas-Bajas-Modificaciones) de estaciones 
                           2- ABM (Altas-Bajas-Modificaciones) de trenes 
                           3- ABM (Altas-Bajas-Modificaciones) de lineas
                           4- ABM (Altas-Bajas-Modificaciones) de red de rieles 
                           5- consulta sobre trenes 
                           6- consulta sobre viajes 
                           10- Finalizar""");
        res = TecladoIn.readLineInt();
        return res;
    }

    public static void modificarTxt(File archivo, String cadena) {
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            BufferedWriter buffer = new BufferedWriter(escribirArchivo);
            buffer.write(cadena);
            buffer.newLine();
            buffer.close();
        } catch (Exception ex) {
        }
    }

    public static boolean altaEstaciones() throws FileNotFoundException {
        Scanner sc = new Scanner(txtEstaciones);
        String[] datos = new String[7];
        boolean res = true;
        while (sc.hasNextLine()) {
            datos = sc.nextLine().split(";");
            if (datos.length == 7) {
                String nombre = datos[0];
                String calle = datos[1];
                int numero = Integer.parseInt(datos[2]);
                String ciudad = datos[3];
                int codPostal = Integer.parseInt(datos[4]);
                int cantVias = Integer.parseInt(datos[5]);
                int cantPlataformas = Integer.parseInt(datos[6]);
                Estacion nuevaEstacion = new Estacion(nombre, calle, numero, ciudad, codPostal, cantVias, cantPlataformas);
                avlEstaciones.insertar(nuevaEstacion, datos[0]);
                grafoMapa.insertarVertice(nuevaEstacion);
            } else {
                res = false;
            }
        }
        return res;
    }

    public static boolean altaMapa() throws FileNotFoundException {
//        Scanner sc = cargaArchivo("D:\\Users\\CalamarCoder\\Documents\\NetBeansProjects\\prueba\\src\\main\\java\\Datos\\\\leeTrenes.txt");
        Scanner sc = new Scanner(new File("E:\\Users\\CalamarCoder\\Documents\\NetBeansProjects\\TrenesSA\\src\\main\\java\\Datos\\leeMapaRieles.txt"));
        String[] datos = new String[3];
        boolean res = true;
        while (sc.hasNextLine()) {
            datos = sc.nextLine().split(";");
            if (datos.length == 3) {
                String nombreEstacion1 = ((String) datos[0]);
                Estacion estacion1 = (Estacion) avlEstaciones.obtenerDato(nombreEstacion1);

                String nombreEstacion2 = ((String) datos[1]);
                Estacion estacion2 = (Estacion) avlEstaciones.obtenerDato(nombreEstacion2);

                if (estacion1 != null && estacion2 != null) {
                    int km = Integer.parseInt((String) datos[2]);
                    res = grafoMapa.insertarArco(estacion1, estacion2, km);
                }
            } else {
                res = false;
            }
        }
        return res;
    }

    public static boolean altaTren() throws FileNotFoundException {
//        Scanner sc = cargaArchivo("D:\\Users\\CalamarCoder\\Documents\\NetBeansProjects\\prueba\\src\\main\\java\\Datos\\\\leeTrenes.txt");
        Scanner sc = new Scanner(new File("E:\\Users\\CalamarCoder\\Documents\\NetBeansProjects\\TrenesSA\\src\\main\\java\\Datos\\leeTrenes.txt"));
        String[] datos = new String[5];
        boolean res = true;
        while (sc.hasNextLine()) {
            datos = sc.nextLine().split(";");
            if (datos.length == 5) {
                int codigo = Integer.parseInt(datos[0]);
                String tipoPropulsion = datos[1];
                int cantidadVagonesPasajeros = Integer.parseInt(datos[2]);
                int cantidadVagonesCarga = Integer.parseInt(datos[3]);
                String lineAsignada = datos[4];
                avlTrenes.insertar(new Tren(codigo, tipoPropulsion, cantidadVagonesPasajeros, cantidadVagonesCarga, lineAsignada), datos[0]);
            } else {
                res = false;
            }
        }
        return res;
    }

    public static boolean altaLineas() throws FileNotFoundException {
//        Scanner sc = cargaArchivo("D:\\Users\\CalamarCoder\\Documents\\NetBeansProjects\\prueba\\src\\main\\java\\Datos\\\\leeTrenes.txt");
        Scanner sc = new Scanner(new File("E:\\Users\\CalamarCoder\\Documents\\NetBeansProjects\\TrenesSA\\src\\main\\java\\Datos\\leeLineas.txt"));
        String[] datos = new String[10];
        boolean res = true;
        int i;
        while (sc.hasNextLine()) {
            datos = sc.nextLine().split(";");
            if (datos.length != 0) {
                String nombre = datos[0];
                i = 1;
                Estacion aux;
                Lista listaEstaciones = new Lista();
                while (i < datos.length) {
                    aux = (Estacion) avlEstaciones.obtenerDato(datos[i]);
                    grafoMapa.caminoConTopeDeVertices(res, res, 2);
                    listaEstaciones.insertar(aux, i);
                    i++;
                }
                hashLineas.put(nombre, new Linea(nombre, listaEstaciones));
            } else {
                res = false;
            }
        }
        return res;
    }

    private static void ABMEstaciones() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Estacion:  
                           1- Alta Estacion 
                           2- Baja Estacion 
                           3- Modificar Estacion 
                           4- Terminar""");
            res = TecladoIn.readLineInt();

            switch (res) {
                case 1 ->
                    altaNuevaEstacion();
                case 2 ->
                    bajaEstacion();
                case 3 ->
                    modificarAeropuerto();
                case 4 ->
                    System.out.println("Fin ABM estaciones");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }

    public static void altaNuevaEstacion() {
        Estacion nuevaEst;
        String nombre;
        do {
            System.out.println("Ingrese nombre estacion");
            nombre = TecladoIn.readLine();
        } while (avlEstaciones.obtenerDato(nombre) != null);// mientras no exista previamente la estacion

        System.out.println("Ingrese calle");
        String calle = TecladoIn.readLine();
        System.out.println("Ingrese numero de estacion");
        int num = TecladoIn.readLineInt();
        System.out.println("Ingrese ciudad");
        String ciudad = TecladoIn.readLine();
        System.out.println("Ingrese codigo postal");
        int codPostal = TecladoIn.readLineInt();
        System.out.println("Ingrese cantidad de vias");
        int cantVias = TecladoIn.readLineInt();
        System.out.println("Ingrese cantidad de plataformas");
        int cantPlataformas = TecladoIn.readLineInt();
        nuevaEst = new Estacion(nombre, calle, num, ciudad, codPostal, cantVias, cantPlataformas);

        if (avlEstaciones.insertar(nuevaEst, nombre)) {
            grafoMapa.insertarVertice(nuevaEst);
            System.out.println("nueva Estacion ingresada");
        } else {
            System.out.println("Ya existe esa Estacion");
        }
    }

    public static void bajaEstacion() {
        Estacion nuevaEst;
        String nombre;
        do {
            System.out.println("Ingrese nombre de estacion a Eliminar");
            nombre = TecladoIn.readLine();
            nuevaEst = (Estacion) avlEstaciones.obtenerDato(nombre);
        } while (nuevaEst == null);// hasta encontrar la estacion

        grafoMapa.eliminarVertice(nuevaEst);// elimina la estacion del mapa y sus ady
        //corregir hash 
        avlEstaciones.eliminar(nombre);// elimina la estacion
        System.out.println("La estacion fue eliminada");
    }

    private static void modificarAeropuerto() {
        System.out.println("Ingrese nombre de Estacion a modificar");
        String n = TecladoIn.readLine();
        Estacion aux = (Estacion) avlEstaciones.obtenerDato(n);
        if (aux != null) {
            System.out.println("Ingrese nuevo numero");
            aux.setNumero(TecladoIn.readLineInt());
            // AGREGAR MAS MODIFICACIONES
        } else {
            System.out.println("Estacion no existe");
        }
    }

    private static void ABMTrenes() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Tren:  
                           1- Alta Tren 
                           2- Baja Tren 
                           3- Modificar Tren 
                           4- Terminar""");
            res = TecladoIn.readLineInt();

            switch (res) {
                case 1 ->
                    altaNuevoTren();
                case 2 ->
                    bajaTren();
                case 3 ->
                    modificarTren();
                case 4 ->
                    System.out.println("Fin ABM trenes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }

    public static void altaNuevoTren() {
        Tren nuevoTren;
        int codigo;
        do {
            System.out.println("Ingrese codigo de Tren");
            codigo = TecladoIn.readLineInt();
        } while (avlTrenes.obtenerDato(codigo) != null);// mientras no exista previamente el tren

        System.out.println("Ingrese tipo de propulsion");
        String tipoPropulsion = TecladoIn.readLine();
        System.out.println("Ingrese numero de cantidad de Vagones Pasageros");
        int cantVagonesPasageros = TecladoIn.readLineInt();
        System.out.println("Ingrese cantidad de Vagones Carga");
        int cantVagonesCarga = TecladoIn.readLineInt();
        System.out.println("Ingrese linea asignada");

        String lineaAsignada = TecladoIn.readLine();
        nuevoTren = new Tren(codigo, tipoPropulsion, cantVagonesPasageros, cantVagonesCarga, lineaAsignada);

        if (avlTrenes.insertar(nuevoTren, codigo)) {
            System.out.println("nuevo Tren ingresado");
        } else {
//            System.out.println("Ya existe esa Estacion");
        }
    }

    public static void bajaTren() {
        Tren trenAux;
        int cod;
        do {
            System.out.println("Ingrese codigo del tren a Eliminar");
            cod = TecladoIn.readLineInt();
            trenAux = (Tren) avlTrenes.obtenerDato(cod);
        } while (trenAux == null);// hasta encontrar lel tren

        avlTrenes.eliminar(cod);// elimina el tren
        System.out.println("El tren fue eliminado");
    }

    private static void modificarTren() {
        System.out.println("Ingrese codigo del tren a modificar");
        int c = TecladoIn.readLineInt();
        Tren aux = (Tren) avlTrenes.obtenerDato(c);
        if (aux != null) {
            System.out.println("Ingrese nueva linea");
            aux.setLineaAsignada(TecladoIn.readLine());
            // AGREGAR MAS MODIFICACIONES
        } else {
            System.out.println("Tren no existe");
        }
    }

    private static void ABMRieles() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Riel :  
                           1- Alta Riel 
                           2- Baja Riel  
                           3- Modificar Riel  
                           4- Terminar""");
            res = TecladoIn.readLineInt();

            switch (res) {
                case 1 ->
                    altaNuevoRiel();
                case 2 ->
                    bajaRiel();
//                case 3 ->
//                    modificarTren();
                case 4 ->
                    System.out.println("Fin ABM rieles");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }

    public static void altaNuevoRiel() {
        Estacion aux1, aux2;
        String nomb1, nomb2;
        do {
            System.out.println("Ingrese nombre de la primera Estacion");
            nomb1 = TecladoIn.readLine();
            aux1 = (Estacion) avlEstaciones.obtenerDato(nomb1);
        } while (aux1 == null);
//        NodoVerticeEtiquetado a= grafoMapa.recuperarVertice(aux2);
        do {
            System.out.println("Ingrese nombre de la segunda Estacion");
            nomb2 = TecladoIn.readLine();
            aux2 = (Estacion) avlEstaciones.obtenerDato(nomb2);
        } while (aux2 == null);
        // se encontraron las dos estaciones

        System.out.println("Ingrese los Km del nuevo Riel ");
        double riel = TecladoIn.readLineDouble();

        grafoMapa.insertarArco(aux1, aux2, riel);
        System.out.println("nuevo riel ingresado");
    }

    public static void bajaRiel() {
        Estacion aux1, aux2;
        String nomb1, nomb2;
        do {
            System.out.println("Ingrese nombre de la primera Estacion");
            nomb1 = TecladoIn.readLine();
            aux1 = (Estacion) avlEstaciones.obtenerDato(nomb1);
        } while (aux1 == null);
//        NodoVerticeEtiquetado a= grafoMapa.recuperarVertice(aux2);
        do {
            System.out.println("Ingrese nombre de la segunda Estacion");
            nomb2 = TecladoIn.readLine();
            aux2 = (Estacion) avlEstaciones.obtenerDato(nomb2);
        } while (aux2 == null);
        // se encontraron las dos estaciones

//        Lista listaAux= grafoMapa.caminoConTopeDeVertices(aux1, aux2, 2);
        grafoMapa.eliminarArco(aux1, aux2);
//        int i=1;
//        boolean encontrado=false;
//        while(i<=listaAux.longitud()|| encontrado==false){
//            EslistaAux.recuperar(i)
//        }
        System.out.println("El riel fue eliminado");
    }

    private static void ABMLineas() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Linea :  
                           1- Alta Linea 
                           2- Baja Linea  
                           3- Modificar Linea  
                           4- Terminar""");
            res = TecladoIn.readLineInt();

            switch (res) {
                case 1 ->
                    altaNuevaLinea();
//                case 2 ->
//                    bajaLineas();
//                case 3 ->
//                    modificarTren();
                case 4 ->
                    System.out.println("Fin ABM Lineas");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }

    public static void altaNuevaLinea() {
//        Linea nuevaLinea;
        String nombre;
        do {
            System.out.println("Ingrese nombre de la nueva linea");
            nombre = TecladoIn.readLine();
        } while (hashLineas.get(nombre) != null);// mientras no exista previamente el tren
        boolean seguir = true;
        String aux;
        Lista listaEst = new Lista();
        while (seguir) {
            System.out.println("ingrese estacion");
            aux = TecladoIn.readLine();
            if (avlEstaciones.obtenerDato(aux) != null) {
                listaEst.insertar(aux, listaEst.longitud());
                System.out.println("estacion insertada");
            } else {
                System.out.println("no existe estacion");
            }
            System.out.println("desea ingresar otra estacion  si/no");
            if ("no".equals(TecladoIn.readLine())) {
                seguir = false;
            }
        }

        hashLineas.put(nombre, new Linea(nombre, listaEst));

//        if (avlTrenes.insertar(nuevoTren, nombre)) {
//            System.out.println("nuevo Tren ingresado");
//        } else {
////            System.out.println("Ya existe esa Estacion");
//        }
    }

    private static void SubMenuTrenes() {
        int res;
        do {
            System.out.println("""
                           Sub Menu de opciones de Trenes :  
                           1- mostrar toda la información del Tren 
                           2- verificar si está destinado a alguna línea 
                           3- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    mostrarInfoTrenes();
                case 2 ->
                    verificarTrenLinea();
                case 3 ->
                    System.out.println("Fin sub menu trenes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 3");
            }
        } while (res != 3);
    }

    public static void mostrarInfoTrenes() {
        int cod;
        Tren trenAux;
        do {
            System.out.println("Ingrese Codigo del Tren");
            cod = TecladoIn.readLineInt();
            trenAux = (Tren) avlTrenes.obtenerDato(cod);
        } while (trenAux == null);

        System.out.println(trenAux.toString());
    }

    public static void verificarTrenLinea() {
        int cod;
        Tren trenAux;

        do {
            System.out.println("Ingrese Codigo del Tren");
            cod = TecladoIn.readLineInt();
            trenAux = (Tren) avlTrenes.obtenerDato(cod);
        } while (trenAux == null);

        if (!"no-asignado".equals(trenAux.getLineaAsignada())) {
            Linea lineaAux = (Linea) hashLineas.get(trenAux.getLineaAsignada());
            System.out.println("La linea asignada del tren: " + trenAux.getCodigo() + ", es: " + lineaAux.getNombre());
            System.out.println("las ciudades que visitara son: ");
            lineaAux.getListaLineas().toString();
        } else {
            System.out.println("el tren no tiene una linea asignada");
        }
    }

    private static void SubMenuViajes() {
        int res;
        do {
            System.out.println("""
                           Sub Menu de opciones de Viajes :  
                           1- Obtener el camino que llegue de A a B que pase por menos estaciones 
                           2- Obtener el camino que llegue de A a B de menor distancia en kilómetros
                           4- Obtener todos los caminos posibles para llegar de A a B sin pasar por una estación C dada
                           5- Verificar si es posible llegar de A a B recorriendo como máximo una cantidad X de kilómetros
                           6- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    caminoDeAaBMenosEstaciones();
                case 2 ->
                    caminoDeAaBMenosKm();
                case 3 ->
                    posiblesCaminosDeAaBsinPasarPorUnaEstaciónC();
                case 4 ->
                    System.out.println("Fin sub menu viajes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 3");
            }
        } while (res != 3);
    }

    public static void caminoDeAaBMenosEstaciones() {
        String nom1, nom2;
        Estacion est1, est2;
        do {
            System.out.println("Ingrese nombre del estacion A");
            nom1 = TecladoIn.readLine();
            est1 = (Estacion) avlEstaciones.obtenerDato(nom1);
        } while (est1 == null);
        do {
            System.out.println("Ingrese nombre del estacion B");
            nom2 = TecladoIn.readLine();
            est2 = (Estacion) avlEstaciones.obtenerDato(nom2);
        } while (est2 == null);
        Lista aux = grafoMapa.caminoMasCorto(est1, est2);
        System.out.println("el camino mas corto entre " + est1.getNombre() + "y " + est2.getNombre() + " es: ");
        System.out.println(aux.toString());
    }

    public static void caminoDeAaBMenosKm() {
        String nom1, nom2;
        Estacion est1, est2;
        do {
            System.out.println("Ingrese nombre del estacion A");
            nom1 = TecladoIn.readLine();
            est1 = (Estacion) avlEstaciones.obtenerDato(nom1);
        } while (est1 == null);
        do {
            System.out.println("Ingrese nombre del estacion B");
            nom2 = TecladoIn.readLine();
            est2 = (Estacion) avlEstaciones.obtenerDato(nom2);
        } while (est2 == null);
        Lista aux = grafoMapa.caminoConMenosPeso(est1, est2);
        System.out.println("el camino mas corto entre " + est1.getNombre() + "y " + est2.getNombre() + " es: ");
        System.out.println(aux.toString());
    }

    public static void posiblesCaminosDeAaBsinPasarPorUnaEstaciónC() {
        String nom1, nom2, nom3;
        Estacion est1, est2, est3;
        do {
            System.out.println("Ingrese nombre del estacion A");
            nom1 = TecladoIn.readLine();
            est1 = (Estacion) avlEstaciones.obtenerDato(nom1);
        } while (est1 == null);
        do {
            System.out.println("Ingrese nombre del estacion B");
            nom2 = TecladoIn.readLine();
            est2 = (Estacion) avlEstaciones.obtenerDato(nom2);
        } while (est2 == null);
        do {
            System.out.println("Ingrese nombre del estacion C por la cual no debe pasar");
            nom3 = TecladoIn.readLine();
            est3 = (Estacion) avlEstaciones.obtenerDato(nom3);
        } while (est3 == null);

        Lista aux = grafoMapa.listaConCaminosQueNoTieneC(est1, est2, est3);
        System.out.println("la lista de caminos entre " + est1.getNombre() + "y " + est2.getNombre() + " sin pasar por " + est2.getNombre() + "  es: ");
        System.out.println(aux.toString());
    }

}
