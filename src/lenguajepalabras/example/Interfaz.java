package lenguajepalabras.example;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jpcas
 */
public class Interfaz extends javax.swing.JFrame {

    /**
     * Nombre: Interfaz
     * Propósito: metodo constructor de la clase
     * Precondición: setLocationRelativeTo = null ; setResizable= false
     * Postcondición: pantalla inicial
     */
    public Interfaz() {
    initComponents();
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        contenido = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        salida = new javax.swing.JTextArea();
        ejecutar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contenido.setColumns(20);
        contenido.setRows(5);
        jScrollPane1.setViewportView(contenido);

        salida.setColumns(20);
        salida.setRows(5);
        jScrollPane2.setViewportView(salida);

        ejecutar.setText("SELECCIONAR ARCHIVO");
        ejecutar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ejecutarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(ejecutar)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * Nombre: ejecutarMouseClicked
     * Propósito: Leer archivo y mostrar resultados
     * @param evt
     * Precondición: Archivo permitido extencion txt; palabras = null; línea =0
     * Postcondición: muestra resultados de análisis de datos
     */
    private void ejecutarMouseClicked(java.awt.event.MouseEvent evt) {                                      
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("txt", "txt");
        chooser.setFileFilter(filter2);
        chooser.showOpenDialog(null);

        try {
            dirarch = new URL("file:" + chooser.getSelectedFile().getAbsolutePath());
        } catch (MalformedURLException e) {
            contenido.setText("Imposible cargar el archivo arch.txt");
        }

        String texto;
        try {
            entrada = dirarch.openStream();
            datosEntrada = new BufferedReader(new InputStreamReader(entrada));
           
            while ((texto = datosEntrada.readLine()) != null) {
                String palabras[] = null;
                contenido.append(texto + "\n");
                contarEspacion(texto);
                palabras = texto.split(" ");
                analizaPalabra(palabras);
                contarORelacionales(palabras);
                contarSEspeciales(palabras);
                linea++;
            }
            salida.setText("Cantidad de palabras en mayúsculas= " + pmayuscula + "\nCantidad de palabras en minúsculas= " + pminuscula
                    + "\nCantidad de caracteres en mayúsculas= " + cmayuscula + "\nCantidad de espacios en blanco= " + espacios + "\nCantidad de operadores matemáticos= "
                    + omatematico + "\nCantidad de símbolos especiales operadores de relación= " + orelacion + "\nCantidad de símbolos especiales= " + sespecial
                    + "\nCantidad de líneas= " + linea);

            datosEntrada.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }                                    

   
    /**
     * Nombre: analizaPalabra
     * Propósito: contar numero de palabras en mayuscula, minuscula y operadores matematicos  
     * @param palabras Arreglo de palabras de cada linea del documento
     * Precondición: palabras != null; pma = 0; pmi = 0
     * Postcondición: asigna valores a contadores globales
     */
    public void analizaPalabra(String[] palabras) {
        int pma = 0;
        int pmi = 0;

        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];

            for (int j = 0; j < palabra.length(); j++) {

                if (palabra.codePointAt(j) < 90 && palabra.codePointAt(j) > 64) {
                    cmayuscula++;
                    pma=pma+1;
                }

                if (palabra.codePointAt(j) < 123 && palabra.codePointAt(j) > 96) {
                    pmi=pmi+1;

                }
                if (palabra.codePointAt(j) == 42 || palabra.codePointAt(j) == 43 || palabra.codePointAt(j) == 45
                        || palabra.codePointAt(j) == 47 || palabra.codePointAt(j) == 94) {
                    omatematico++;
                }
            }
           
            // System.out.println(pmi+" " +" "+palabra.length());
            if (pmi == palabra.length() && pmi != 0) {
                pminuscula++;
                 }

            if (pma == palabra.length() && pma != 0) {

                pmayuscula++;
            }

            pma = 0;
            pmi = 0;
        }

    }

    /**
     * Nombre: contarEspacion
     * Propósito: contar numero de espacios en blanco
     * @param linea cadena correspondiente a cada linea del archivo
     * Precondición: linea != null; linea!= ''
     * Postcondición: asigna valores a contadores de espacio
     */
    public void contarEspacion(String linea) {

        for (int i = 0; i < linea.length(); i++) {
            if (linea.charAt(i) == ' ') {
                espacios++;
            }

        }

    }
   
    /**
     * Nombre: contarORelacionales
     * Propósito: para contar numero de operadores de relacion (>, >=, <, <=, <>)
     * @param palabras Arreglo de palabras de cada linea del documento
     * Precondición: p= " "; palabra =" "; palabras <> null
     * Postcondición: asignar valores a operadores de relacion
     */
    public void contarORelacionales(String[] palabras) {

        String p = " ";
        String palabra = " ";

        for (int i = 0; i < palabras.length; i++) {
            palabra = palabras[i];

            for (int j = 0; j < palabra.length(); j++) {

                if (palabra.codePointAt(j) == 62 || palabra.codePointAt(j) == 60 || palabra.codePointAt(j) == 61) {

                    p = palabra + " ";

                    if (((p.codePointAt(j) + p.codePointAt(j + 1)) == 123) || ((p.codePointAt(j) + p.codePointAt(j + 1)) == 121)
                            || ((p.codePointAt(j) + p.codePointAt(j + 1)) == 122)) {
                        orelacion++;
                        j++;
                    } else {
                        orelacion++;

                    }
                }

            }
        }

    }
   
    /**
     * Nombre: contarSEspeciales
     * Propósito: contar numero de simbolos especiales como (, ; .)
     * @param palabras Arreglo de palabras de cada linea del documento
     * Precondición: palabra =" "; palabras <> null
     * Postcondición: asignar valores de símbolos especiales
     */
   
    public void contarSEspeciales(String[] palabras) {

        String palabra = " ";

        for (int i = 0; i < palabras.length; i++) {
            palabra = palabras[i];

            for (int j = 0; j < palabra.length(); j++) {

                if (palabra.codePointAt(j) == 44 || palabra.codePointAt(j) == 59
                        || palabra.codePointAt(j) == 58 || palabra.codePointAt(j) == 34 || palabra.codePointAt(j) == 39) {
                    sespecial++;

                }

            }
        }

    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    private int pmayuscula = 0;
    private int pminuscula = 0;
    private int cmayuscula = 0;
    private int espacios = 0;
    private int omatematico = 0;
    private int orelacion = 0;
    private int sespecial = 0;
    private int linea = 0;
    private URL dirarch;
    private InputStream entrada;
    private BufferedReader datosEntrada;

    // Variables declaration - do not modify                    
    private javax.swing.JTextArea contenido;
    private javax.swing.JButton ejecutar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea salida;
    // End of variables declaration                  
}