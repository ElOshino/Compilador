/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguajepalabras.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**Clase que implementa la logica de tokenizar los archivos cargados
 *
 * @author Nelson
 */
public class Logica {
    // Listas para almacenar informacion de los archivos
    private ArrayList<String> operadoresAritmeticos;
    private ArrayList<String> operadoresLogicos;
    private ArrayList<String> llaves;
    private ArrayList<String> palabrasReservadas;
    private ArrayList<String> textos;
    private final ArrayList<String> analizador;

    
    public Logica() {
        this.operadoresAritmeticos = new ArrayList<>();
        this.operadoresLogicos = new ArrayList<>();
        this.llaves = new ArrayList<>();
        this.palabrasReservadas = new ArrayList<>();
        this.textos = new ArrayList<>();
        this.analizador = new ArrayList<>();
    }

    /**
     * Nombre: loadPalabrasRes
     * Proposito: Cargar de un archivo las palabras reservadas para el programa
     * PreCondicion: Existencia de la ruta del archivo recibido
     * PostCondicion: Palabras reservadas cargadas en las listas de valores correspondientes
     */
    public void loadPalabrasRes(String path) {
        operadoresAritmeticos = new ArrayList<>();
        operadoresLogicos = new ArrayList<>();
        llaves = new ArrayList<>();
        palabrasReservadas = new ArrayList<>();
        try {
            String cadena;
            StringBuilder salida = new StringBuilder();
            FileReader f = new FileReader(path);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                salida.append(cadena).append("\n");
                if (cadena.contains("operadoresAritmeticos")) {
                    this.operadoresAritmeticos.addAll(Arrays.asList(cadena.split(":")[1].split(",")));
                }

                if (cadena.contains("operadoresLogicos")) {
                    this.operadoresLogicos.addAll(Arrays.asList(cadena.split(":")[1].split(",")));
                }

                if (cadena.contains("palabrasReservadas")) {
                    this.palabrasReservadas.addAll(Arrays.asList(cadena.split(":")[1].split(",")));
                }

                if (cadena.contains("llaves")) {
                    this.llaves.addAll(Arrays.asList(cadena.split(":")[1].split(",")));
                }
            }
        } catch (IOException e) {
            System.err.println("Error en lectura de archivo" + path);
            System.err.println(e.toString());
        }
    }

    public void loadTextod(String path) {
        this.textos = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(path);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                textos.add(cadena);
            }
        } catch (IOException e) {
            System.err.println("Error en lectura de archivo de Textos: " + path);
            System.err.println(e.toString());
        }
    }

    /**
     * Nombre: toStringTexto
     * Proposito: Construir una cadena de valores de la lista que contiene el programa
     * PreCondicion: La lista debe estar cargada con los datos del programa
     * PostCondicion: cadena de valores del programa
     */
    public String toStringTexto() {
        StringBuilder cadena = new StringBuilder();
        this.textos.forEach((texto) -> {
            cadena.append("\n");
            cadena.append(texto);
        });
        return cadena.toString();
    }

    /**
     * Nombre: toStringOperadores
     * Proposito: Construir una cadena de valores de la lista que contiene las palabras reservadas
     * PreCondicion: La lista debe estar cargada con los datos de las palabras reservadas
     * PostCondicion: cadena de valores
     */
    public String toStringOperadores() {
        StringBuilder cadena = new StringBuilder();
        cadena.append("Operadores Arimeticos:");
        this.operadoresAritmeticos.forEach((texto) -> {
            cadena.append("\t");
            cadena.append(texto);
        });
        cadena.append("\nOperadores Logicos:");
        this.operadoresLogicos.forEach((texto) -> {
            cadena.append("\t");
            cadena.append(texto);
        });
        cadena.append("\nLlaves:");
        this.llaves.forEach((texto) -> {
            cadena.append("\t");
            cadena.append(texto);
        });
        cadena.append("\nPalabras Reservadas");
        this.palabrasReservadas.forEach((texto) -> {
            cadena.append("\t");
            cadena.append(texto);
        });
        return cadena.toString();
    }

    /**
     * Nombre: validarTexto
     * Proposito: Realiza las validaciones del texto del programa cargado y lo clasifica de acuerdo al listado de palabras reservadas
     * PreCondicion: Tanto como la lista de palabras reservadas y del programa debieron ser cargadas previamente
     * PostCondicion: cadena de valores del programa clasificando cada palabra
     */
    public String validarTexto() {
        if(this.llaves.isEmpty() ||
                this.operadoresAritmeticos.isEmpty()  ||
                this.operadoresLogicos.isEmpty() ||
                this.palabrasReservadas.isEmpty()){
            this.analizador.add("No se ha cargado el archivo de palabras Reservadas");
            return this.analizador.toString();
        }
        StringBuilder cadena = new StringBuilder();
    
        this.textos.forEach((String valores) -> {
            int escadena = 0;
            StringBuilder palabra = new StringBuilder();
            System.out.println("\t" + valores);
            for (Character c : valores.toCharArray()) {

                if (c.toString().equals("'") || escadena > 0) {

                    if (escadena == 0) {
                        agregarvalores(palabra.toString().trim(), escadena > 0);
                        palabra = new StringBuilder();
                    }

                    palabra.append(c);
                    escadena = c.toString().equals("'") ? escadena + 1 : escadena;

                    if (escadena == 2) {
                        agregarvalores(palabra.toString().trim(), escadena > 0);
                        palabra = new StringBuilder();
                        escadena = 0;
                    }
                } else if (this.llaves.contains(c.toString())) {
                    agregarvalores(palabra.toString().trim(), escadena > 0);
                    palabra = new StringBuilder(c.toString());
                    agregarvalores(palabra.toString().trim(), escadena > 0);
                    palabra = new StringBuilder();
                } else if (!c.toString().equals(" ")) {
                    palabra.append(c);
                    System.out.println("* " + palabra.toString());
                } else {
                    System.out.println("**" + palabra.toString());
                    agregarvalores(palabra.toString().trim(), escadena > 0);
                    palabra = new StringBuilder();
                }
            }
            agregarvalores(palabra.toString().trim(), escadena > 0);
        });

        this.analizador.forEach((texto) -> cadena.append("\n").append(texto));
        return cadena.toString();
    }

    /**
     * Nombre: agregarvalores
     * Proposito: Adiciona a la lista del analizador la clasificacion de palabras
     * PreCondicion: La lista de palabras reservadas debio ser cargada
     * PostCondicion: Lista de analizador cargadas con la clasificacion de palabras
     */
    private void agregarvalores(String palabra, boolean escadena) {
        System.out.println("**" + palabra);
        if (escadena) {
            this.analizador.add(palabra + "\t Cadena");
        }else if (palabra.equals("")) {
            // espacio en blanco
        } else if (this.operadoresAritmeticos.contains(palabra)) {
            this.analizador.add(palabra + "\t Operador Aritmetico");
        } else if (this.operadoresLogicos.contains(palabra)) {
            this.analizador.add(palabra+ "\t Operador Logicos");
        } else if (this.llaves.contains(palabra)) {
            this.analizador.add(palabra + "\t Llaves");
        } else if (this.palabrasReservadas.contains(palabra)) {
            this.analizador.add(palabra + "\t Palabras Reservadas");
        } else {
            this.analizador.add(palabra + "\t Identificador");
        }
    }
}
