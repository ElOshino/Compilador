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
class Pila {

    final int MAXIMO = 20;
    int t;   // Numero de objetos actualmente almacenados 
    char[] a;  // Objetos almacenados 

    Pila() {
        t = 0;
        a = new char[MAXIMO];
    }

    Pila(int tamaño) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void initPila() {
        t = 0;
    }

    boolean pilaVacia() {
        return t == 0;
    }

    int insPila(char objeto) {
        if (t == MAXIMO - 1) {
            return -1; // No soporta mas elementos 
        } else {
            t++;
            a[t - 1] = objeto;
        }
        return 1;
    }

    char retiraPila() {
        if (pilaVacia()) {
            return '#';
        } else {
            t--;
            return a[t];
        }
    }

    char topePila() {
        if (pilaVacia()) {
            return '#';
        } else {
            return a[t - 1]; // No retira el elemento
        }
    }
}