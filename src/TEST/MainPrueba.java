/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TEST;

import java.io.File;
import logica.Analizador;
import logica.Lector;
import modelo.Resumen;

/**
 *
 * @author COMPUGAMER
 */
public class MainPrueba {
    public static void main(String[] args) {
        // 1. Seleccionamos el archivo
        File archivo = new File("C:\\Users\\COMPUGAMER\\Desktop\\prueba.txt");
        
        // 2. Usar tu Lector
        Lector miLector = new Lector();
        Resumen miResumen = miLector.leer(archivo);
        
        if (miResumen != null) {
            System.out.println("--- Lectura Exitosa ---");
            System.out.println(miResumen.toString());
            
            // 3. Usar tu Analizador
            System.out.println("\n--- Análisis de Frecuencia ---");
            Analizador miAnalizador = new Analizador();
            String reporte = miAnalizador.analizar(miResumen);
            System.out.println(reporte);
        } else {
            System.out.println("Falló la lectura. Revisa la ruta del archivo.");
        }
    }
}
