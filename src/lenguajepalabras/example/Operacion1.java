package lenguajepalabras.example;


import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Casacra4_301
 */
public class Operacion1 extends Applet implements ActionListener {

    Button b1 = new Button("CALCULAR"); // 1- se crea el componente 
    Label lexpresion = new Label("EXPRESION INFIJA");
    Label lprefija = new Label("EXPRESION PREFIJA");
    Label lposfija = new Label("EXPRESION POSFIJA");

    Label lresultado = new Label("RESULTADO NUMERICO");

    TextField texto = new TextField(20);
    TextField rprefija = new TextField(20);
    TextField rposfija = new TextField(20);

    TextField rnumerico = new TextField(20);

    String linea = new String();
    int senal = 0;
    Expre[] expresion = new Expre[20];
    double[] operan = new double[100];

    char[] infijo = new char[100];
    char[] posfijo = new char[100];
    char[] prefijo = new char[100];

// Arreglos para los gráficos
    Nodo Grafico[] = new Nodo[100]; // Almacena los nodos a graficar
    int[][] Padres = new int[100][2]; // Control de los nodos que tienen hijos 
    int Elementos;
    int ContadorHojas = 0;
    int DiametroNodo = 80;
    int XRaiz = 160;
    int YRaiz = 100;
    int EspacioHorizontal = 30;
    String CadenaPosfijo = "";

    //______________________________
    Toolkit pantalla = Toolkit.getDefaultToolkit(); // se almacena la pantalla en el objeto mipantalla
    Dimension tamañopantalla = pantalla.getScreenSize();  // se obtiene la resolucion de la pantalla en el objeto tamañopantalla
    int alturaPantalla = tamañopantalla.height; //  obtenemos la altura de la pantalla 
    int anchoPantalla = tamañopantalla.width; // obtener la anchura de la pantalla 
    int alto = alturaPantalla;
    int ancho = anchoPantalla;
    //_____________________________________

     /**
     * Nombre: Operacion1
     * Propósito: metodo constructor de la clase
     * Precondición: i =0
     * Postcondición: 
     */
    public Operacion1() {

        for (int i = 0; i < 20; i++) {
            expresion[i] = new Expre();
        }
    }
    
    /**
     * Nombre: Operacion1
     * Propósito: metodo constructor de la clase
     * Precondición: i =0
     * Postcondición: 
     */
    public void init() {

        Panel panel = new Panel();
        panel.setLayout(null);

        panel.add(lexpresion);
        lexpresion.setBounds(50, 1, 160, 20);
        panel.add(texto);
        texto.setBounds(215, 1, 160, 20);

        panel.add(lprefija);
        lprefija.setBounds(50, 20, 160, 20);
        panel.add(rprefija);
        rprefija.setBounds(215, 20, 160, 20);

        panel.add(lposfija);
        lposfija.setBounds(50, 40, 160, 20);
        panel.add(rposfija);
        rposfija.setBounds(215, 40, 160, 20);

        panel.add(lresultado);
        lresultado.setBounds(50, 60, 160, 20);
        panel.add(rnumerico);
        rnumerico.setBounds(215, 60, 160, 20);
        
        add(b1);
        b1.addActionListener(this);
        b1.setBounds(100, 100, 100, 25);

        add("North", panel);
        panel.setBounds(0, 10, 425, 85);
        panel.setBackground(Color.cyan);
        setBackground(Color.LIGHT_GRAY);
        linea = "";
        texto.requestFocus();
        setSize(ancho, alto);

    }

    public void paint(Graphics g) {
        int i;
        if (senal == 1) {
// Inicializar arreglos para evaluar una nueva expresion 
            for (i = 0; i < 100; i++) {
                infijo[i] = '\0';
                posfijo[i] = '\0';
                prefijo[i] = '\0';
                operan[i] = '\0';
            }

            for (i = 0; i < 20; i++) {
                expresion[i].op = '\0';
                expresion[i].valor = 0;
            }

            int m = cExpresion(linea, infijo, operan);
           
            InfijoAPosfijoCon objeto = new InfijoAPosfijoCon();
            
            
            objeto.inAPos(infijo, posfijo);
            
            infijaAPrefija pre = new infijaAPrefija();
            rprefija.setText(pre.convertir(linea));
            
            formarE(posfijo, expresion, operan);
            float resul = evaluar(expresion);
            rnumerico.setText("" + resul);
            
// Determinar cuantos elementos existen en el arreglo 
            for (i = 0; i < 100; i++) {
                if ((int) posfijo[i] != 0) {
                    Elementos = i;
                }
            }

// Cargar los datos de los nodos en el arreglo de nodos 
            CargarDatosNodos();

// Asignar Posiciones de dibujo a los nodos 
            String r = "";
            AsignarCoordenadas();
            DibujarNodos(g, 0);
            CadenaPosfijo = "";

            for (i = Elementos; i >= 0; i--) {
                CadenaPosfijo = CadenaPosfijo + Grafico[i].valor;
            }

            rposfija.setText("" + CadenaPosfijo);
            senal = 0;
        }
    }

    public void DibujarNodos(Graphics g, int NodoPadre) {
        int HijoUno = -1;
        int HijoDos = -1;
        int i;
// buscar los dos hijos del padre recibido
        for (i = 0; i <= Elementos; i++) {
            if (Grafico[i].padre == NodoPadre) {
                if (HijoUno == -1) {
                    HijoUno = i;
                } else {
                    HijoDos = i;
                    i = Elementos + 1;
                }
            }
        }

// Lineas de Conexión a los dos hijos del padre 
        if (HijoUno > 0) {
            g.drawLine(Grafico[NodoPadre].x + (DiametroNodo / 2)
                    - (EspacioHorizontal / 2), Grafico[NodoPadre].y
                    + EspacioHorizontal, Grafico[HijoUno].x + (DiametroNodo / 2)
                    - (EspacioHorizontal / 2), Grafico[HijoUno].y
                    + EspacioHorizontal);
        }

        if (HijoDos > 0) {

            g.drawLine(Grafico[NodoPadre].x + (DiametroNodo / 2)
                    - (EspacioHorizontal / 2), Grafico[NodoPadre].y
                    + EspacioHorizontal, Grafico[HijoDos].x + (DiametroNodo / 2)
                    - (EspacioHorizontal / 2), Grafico[HijoDos].y
                    + EspacioHorizontal);
// Se dibuja el nodo padre y sus dos hijos 
            g.setColor(Color.orange);
            /*g.fillArc(Grafico[NodoPadre].x,Grafico[NodoPadre].y,
             DiametroNodo-EspacioHorizontal,DiametroNodo-
             EspacioHorizontal,0,359); */

            g.fillOval(Grafico[NodoPadre].x, Grafico[NodoPadre].y,
                    DiametroNodo - EspacioHorizontal, DiametroNodo
                    - EspacioHorizontal);
// Dibujar el Texto 
            g.setColor(Color.black);
            g.drawString(Grafico[NodoPadre].valor, Grafico[NodoPadre].x
                    + (DiametroNodo / 2) - (EspacioHorizontal / 2), Grafico[NodoPadre].y
                    + EspacioHorizontal);
        }
        if (HijoUno > 0) {
            if (Grafico[HijoUno].tipo == 1) {
                DibujarNodos(g, HijoUno);
            } else {
                g.setColor(Color.yellow);
                g.fillOval(Grafico[HijoUno].x, Grafico[HijoUno].y, DiametroNodo
                        - EspacioHorizontal, DiametroNodo - EspacioHorizontal);
                g.setColor(Color.black);
                g.drawString(Grafico[HijoUno].valor, Grafico[HijoUno].x + (DiametroNodo / 2)
                        - (EspacioHorizontal / 2), Grafico[HijoUno].y + EspacioHorizontal);
            }
        }

        if (HijoDos > 0) {
            if (Grafico[HijoDos].tipo == 1) {
                DibujarNodos(g, HijoDos);
            } else {
                g.setColor(Color.yellow);
                g.fillOval(Grafico[HijoDos].x, Grafico[HijoDos].y, DiametroNodo - EspacioHorizontal, DiametroNodo - EspacioHorizontal);
                g.setColor(Color.black);
                g.drawString(Grafico[HijoDos].valor, Grafico[HijoDos].x + (DiametroNodo / 2) - (EspacioHorizontal / 2), Grafico[HijoDos].y + EspacioHorizontal);
            }
        }
    }

    public void AsignarCoordenadas() {
        int i;
// contar cuantos elementos tipo 2 existen 
        for (i = 0; i <= Elementos; i++) {
            if (Grafico[i].tipo == 2) {
                ContadorHojas++;
            }
        }
// buscar la posisción del nodo raiz 
        BuscarNodoRaiz(Elementos);
        Grafico[0].x = ancho / 2;
        Grafico[0].y = 100;
        AsignarXY(0);
    }

    public void AsignarXY(int NodoPadre) {
        int HijoUno = -1;
        int HijoDos = -1;
        int i;

// buscar los dos hijos del padre recibido 
        for (i = 0; i <= Elementos; i++) {
            if (Grafico[i].padre == NodoPadre) {
                if (HijoUno == -1) {
                    HijoUno = i;
                } else {
                    HijoDos = i;
                    i = Elementos + 1;
                }
            }
        }

        if (HijoUno > 0) {
            if (Grafico[HijoUno].tipo == 1) {
                Grafico[HijoUno].x = Grafico[NodoPadre].x + DiametroNodo + DiametroNodo;
            } else {
                Grafico[HijoUno].x = Grafico[NodoPadre].x + DiametroNodo;
            }
            Grafico[HijoUno].y = Grafico[NodoPadre].y + DiametroNodo;
        }
        if (HijoDos > 0) {
            if (Grafico[HijoDos].tipo == 1) {
                Grafico[HijoDos].x = Grafico[NodoPadre].x - DiametroNodo - DiametroNodo;
            } else {
                Grafico[HijoDos].x = Grafico[NodoPadre].x - DiametroNodo;
            }
            Grafico[HijoDos].y = Grafico[NodoPadre].y + DiametroNodo;
        }

        if (HijoUno > 0) {
            if (Grafico[HijoUno].tipo == 1) {
                AsignarXY(HijoUno);
            }
        }

        if (HijoDos > 0) {
            if (Grafico[HijoDos].tipo == 1) {
                AsignarXY(HijoDos);
            }
        }
    }

    public void BuscarNodoRaiz(int Nodo) {
// Recibe un indice al arreglo de nodos 
        XRaiz = XRaiz + DiametroNodo;
        if (Grafico[Nodo].tipo < 0) {
            BuscarNodoRaiz(Grafico[Nodo].padre);
        } else {
            return;
        }
    }

    public void CargarDatosNodos() {
// Carga los datos de los nodos en el arreglo 
        int i;
        int ContadorPadres = 0;
        int ContadorNodos = 0;
        int PosicionesAtras = 0;
        int UltimoElemento = 0;
        Nodo Temp;

        for (i = Elementos; i >= 0; i--) {
            Temp = new Nodo();
            Grafico[i] = Temp;
        }

        for (i = Elementos; i >= 0; i--) {
            if ((posfijo[i] == '+') || (posfijo[i] == '-') || (posfijo[i] == '*') || (posfijo[i] == '/')) {
// elementos un operador se debe cargar en el arreglo de padres 
                Padres[ContadorPadres][0] = ContadorNodos;
                Padres[ContadorPadres][1] = 0;
                Grafico[ContadorNodos].tipo = 1;
                ContadorPadres++;
                PosicionesAtras = 2;
            } else {
                PosicionesAtras = 1;
                Grafico[ContadorNodos].tipo = 2;
            }
// Cargar datos del nodo 
            Grafico[ContadorNodos].x = 0;
            Grafico[ContadorNodos].y = 0;
            Grafico[ContadorNodos].valor = "" + posfijo[i];
            if (ContadorPadres > 1) {
                Padres[ContadorPadres - PosicionesAtras][1]++;
                if ((Padres[ContadorPadres - PosicionesAtras][1] > 2) && (ContadorPadres - PosicionesAtras != 0)) {
                    ContadorPadres--;
                    Padres[ContadorPadres - PosicionesAtras][1]++;

                }
            }
// Si es el primer nodo su padre es null 
            if (ContadorNodos == 0) {
                Grafico[ContadorNodos].padre = -1;
            } else {
                Grafico[ContadorNodos].padre = Padres[ContadorPadres - PosicionesAtras][0];
                if ((Padres[ContadorPadres - PosicionesAtras][1] == 2) && (ContadorPadres - PosicionesAtras != 0)) {
                    ContadorPadres--;
                }
            }
            ContadorNodos++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();

        if (s.equals("CALCULAR")) {
            if (linea.equalsIgnoreCase(texto.getText())) {
                senal = 0;
            } else {

                senal = 1;
                linea = texto.getText();
                ContadorHojas = 0;
                DiametroNodo = 80;
                XRaiz = 160;
                YRaiz = 100;
                EspacioHorizontal = 30;
                Elementos = 0;
                repaint();

            }

        }

    }

    int cExpresion(String linea, char[] infijo, double[] operan) {
        double cifra = 0;
        int i, j, k, m;
        char[] auxiliar = new char[10];
        i = k = m = 0;
        while (i < linea.length()) {
            j = 0;
            while (linea.charAt(i) >= '0' && linea.charAt(i) <= '9' || linea.charAt(i) == '.') {
                auxiliar[j++] = linea.charAt(i);
                i++;
                if (i == linea.length()) {
                    break;
                }
            }
            if (j != 0) {
                auxiliar[j] = '\0';
                String aux = String.copyValueOf(auxiliar, 0, j);
                Float f = new Float(aux);
                cifra = f.floatValue();
                infijo[k++] = (char) (m + 48); //'0'; 
                operan[m++] = cifra;
            }
            if (i < linea.length()) {
                infijo[k++] = linea.charAt(i);
                i++;
            }
        }
        infijo[k++] = '#';
        infijo[k] = '\0';
        return m;
    }

    boolean operando(char c) {
        return (c != '+' && c != '-' && c != '*' && c != '/' && c != '^' && c != ')' && c != '(');
    }

    void formarE(char[] p, Expre[] posfijo, double[] operan) {
        int i, j;
        i = j = 0;
        while (p[i] != '\0') {
            if (operando(p[i])) {
                posfijo[j].op = 'v';
                posfijo[j].valor = operan[(int) p[i] - 48]; //'0'
            } else {
                posfijo[j].op = 'r';
                posfijo[j].valor = p[i];
            }
            i++;
            j++;
        }
        posfijo[j].op = 'r';
        posfijo[j].valor = '#';

    }

    float evaluar(Expre[] posfijo) {
        float aux, a, b, r;
        int i = 0;
        char c;
        PilaFloat pila = new PilaFloat();
        pila.initPila();
        while (posfijo[i].valor != '#' || posfijo[i].op != 'r') {
            if (posfijo[i].op == 'v') {
                aux = (float) posfijo[i].valor;
                pila.insPila(aux);
            } else {
                b = pila.retiraPila();
                a = pila.retiraPila();
                c = (char) posfijo[i].valor;
                r = F(a, b, c);
                pila.insPila(r);
            }
            i++;
        }
        r = pila.a[0];
        pila = null;
        return r;
    }

    float F(float a, float b, char op) {
        float r;
        switch (op) {
            case '+':
                r = (a + b);
                break;
            case '-':
                r = (a - b);
                break;
            case '*':
                r = a * b;
                break;
            case '/':
                r = (a / b);
                break;
            case '^':
                r = (float) Math.exp(b * (float) Math.log(a));
                break;
            default:
                r = Float.NaN; // Error.... 
        }

        return r;

    }

}
