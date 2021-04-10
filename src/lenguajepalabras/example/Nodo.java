package lenguajepalabras.example;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Casacra4_301
 */
class Nodo {

// Variables que componen cada Nodo en el arbol 
// Centro del nodo, circulo 
    int x;
    int y;

// Tipo de Nodo 1 Operador, 2 Operando 
    int tipo;

// Contenido del nodo, texto que se muestra 
    String valor;

// apuntador al padre del nodo 
    int padre;

    Nodo() {
        x = y = tipo = 0;
        valor = "";
    }
}