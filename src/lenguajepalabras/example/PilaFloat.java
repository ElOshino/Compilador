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
class PilaFloat {

    final int MAXIMO = 20;
    int t;      // Numero de objetos actualmente almacenados 

    float[] a;     // Para almacenar los objetos 

    PilaFloat() {
        t = 0;
        a = new float[MAXIMO];
    }

    void initPila() {
        t = 0;
    }

    boolean pilaVacia() {
        return t == 0;
    }

    int insPila(float objeto) {
        if (t == MAXIMO - 1) {
            return -1; // No soporta mas elementos 
        } else {
            t++;
            a[t - 1] = objeto;
        }
        return 1;
    }

    float retiraPila() {
        if (pilaVacia()) {
            return -1;
        } else {
            t--;

            return a[t];
        }
    }
}