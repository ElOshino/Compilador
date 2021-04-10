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
class InfijoAPosfijoCon {

    static int[][] m = new int[7][7]; // Matriz de prioridad 

    InfijoAPosfijoCon() {
        iniciaMatriz(); // Inicializa matriz de prioridades 
    }

    void inAPos(char[] infijo, char[] posfijo) {
        int i, j;
        char elemento;
        Pila pila = new Pila();
        i = 0;
        j = -1;
        pila.initPila();
        while (infijo[i] != '#') {
            if (operando(infijo[i])) {
                posfijo[++j] = infijo[i++];
            } else {
                while (!pila.pilaVacia() && prioridad(pila.topePila(), infijo[i]) == 1) {
                    elemento = pila.retiraPila();
                    posfijo[++j] = elemento;
                }
                if (infijo[i] == ')') {
                    elemento = pila.retiraPila();
                } else {
                    pila.insPila(infijo[i]);
                }
                i++;
            }
        }
        while (!pila.pilaVacia()) {
            elemento = pila.retiraPila();
            posfijo[++j] = elemento;
        }
        posfijo[++j] = '\0';
    }

    boolean operando(char c) {
        return (c != '+' && c != '-' && c != '*' && c != '/' && c != '^' && c != ')' && c != '(');
    }

// Inicializa matriz de prioridades 
    void iniciaMatriz() {
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j <= i; j++) {
                if (j <= i) {
                    m[i][j] = 1;
                } else {
                    m[i][j] = 0;
                }
            }
        }
        m[0][1] = m[2][3] = 1;

// Para los parentesis 
        for (j = 0; j < 7; j++) {
            m[5][j] = 0;
            m[j][5] = 0;
            m[j][6] = 1;
        }
        m[5][6] = 0; // Porque el m[5][6] quedo en 1.
        
        for(int x=0; x<7; x++){
            for(int y=0; y<7; y++){
         //   System.out.print(" "+m[x][y]);
            }
           //System.out.println("");
        }
        
    }

    int prioridad(char op1, char op2) {
        int i = 0;
        int j = 0;

        switch (op1) {
            case '+':
                i = 0;
                break;
            case '-':
                i = 1;
                break;
            case '*':
                i = 2;
                break;
            case '/':
                i = 3;
                break;
            case '^':
                i = 4;
                break;
            case '(':
                i = 5;
            case ')':
                j = 6;
                break;
        }
        switch (op2) {
            case '+':
                j = 0;
                break;
            case '-':
                j = 1;
                break;
            case '*':
                j = 2;
                break;
            case '/':
                j = 3;
                break;
            case '^':
                j = 4;
                break;
            case '(':
                j = 5;
                break;
            case ')':
                j = 6;
                break;
        }
      
        return (m[i][j]);
        
    }
      
}