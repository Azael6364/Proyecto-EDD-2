/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TEST;

import java.io.File;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.File;
import logica.Analizador;
import logica.Lector;
import modelo.Resumen;
import javax.swing.JOptionPane;

/**
 *
 * @author COMPUGAMER
 */
public class MainPrueba {

    public static void main(String[] args) {
        // 1. Seleccionamos el archivo (Asegurate que la ruta sea correcta)
        File archivo = new File("C:\\Users\\COMPUGAMER\\Desktop\\prueba.txt");
        
        // 2. Usar tu Lector
        Lector miLector = new Lector();
        Resumen miResumen = miLector.leer(archivo);
        
        if (miResumen != null) {
            // 3. Usar tu Analizador
            Analizador miAnalizador = new Analizador();
            String reporte = miAnalizador.analizar(miResumen);
            
            // 4. CONSTRUIR EL TEXTO FINAL PARA MOSTRAR
            StringBuilder salidaVisual = new StringBuilder();
            salidaVisual.append("=== PRUEBA DE LECTURA Y ANÁLISIS ===\n\n");
            
            salidaVisual.append("--- DATOS CRUDOS DEL OBJETO ---\n");
            salidaVisual.append(miResumen.toString()).append("\n\n");
            
            salidaVisual.append("--- RESULTADO DEL ANALIZADOR ---\n");
            salidaVisual.append(reporte);

            // 5. CREAR LA VENTANA (JTextArea dentro de un Scroll)
            // Esto usa las fuentes nativas de Windows, por lo que los acentos se verán BIEN.
            JTextArea areaTexto = new JTextArea(salidaVisual.toString());
            areaTexto.setLineWrap(true);       // Cortar líneas largas automáticamente
            areaTexto.setWrapStyleWord(true);  // Cortar por palabras completas
            areaTexto.setEditable(false);      // Solo lectura
            
            // Ponemos el texto dentro de un panel con barras de desplazamiento
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(600, 500)); // Tamaño de la ventana
            
            // Mostrar la ventana emergente
            JOptionPane.showMessageDialog(null, scroll, "Resultado de la Prueba - Azael", JOptionPane.INFORMATION_MESSAGE);
            
        } else {
            JOptionPane.showMessageDialog(null, "Error: No se pudo leer el archivo.\nVerifica que exista en el escritorio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}