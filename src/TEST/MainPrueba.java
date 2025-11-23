/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TEST;

import java.io.File;
import javax.swing.JOptionPane;
import logica.Lector;
import logica.Repositorio;
import modelo.Resumen;
/**
 *
 * @author COMPUGAMER
 */
public class MainPrueba {

    public static void main(String[] args) {
        // 1. Leer el archivo original (Simulando que el usuario carga uno)
        File archivo = new File("C:\\Users\\COMPUGAMER\\Desktop\\prueba.txt");
        Lector lector = new Lector();
        Resumen resumenNuevo = lector.leer(archivo);
        
        if (resumenNuevo != null) {
            // 2. Guardar en la "Base de Datos"
            Repositorio repo = new Repositorio();
            boolean exito = repo.guardarResumen(resumenNuevo);
            
            if (exito) {
                JOptionPane.showMessageDialog(null, "¡Resumen guardado exitosamente en la BD!");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar.");
            }
            
            // 3. Simular que cerramos el programa y volvemos a entrar (Cargar todo)
            System.out.println("--- CARGANDO DESDE LA BASE DE DATOS ---");
            Resumen[] todosLosResumenes = repo.cargarResumenes();
            
            System.out.println("Se encontraron " + todosLosResumenes.length + " resumenes guardados:");
            for (Resumen r : todosLosResumenes) {
                if (r != null) {
                    System.out.println("Titulo: " + r.getTitulo());
                }
            }
            
        } else {
            System.out.println("No se pudo leer el archivo original.");
       }
    } 
}