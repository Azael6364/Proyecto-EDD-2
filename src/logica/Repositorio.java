/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import modelo.Resumen;

/**
 * Clase encargada de la persistencia de datos.
 * Guarda y recupera los resumenes en un archivo de texto plano.
 * No usa librerias de colecciones, solo arreglos primitivos.
 * @author COMPUGAMER
 */
public class Repositorio {

    private static final String ARCHIVO_DB = "base_datos_resumenes.txt";
    private static final String SEPARADOR = "##"; 

    /**
     * Guarda un nuevo resumen al final del archivo de base de datos.
     * @param resumen El objeto Resumen a guardar.
     * @return true si se guardo correctamente, false si hubo error.
     */
    public boolean guardarResumen(Resumen resumen) {
        if (resumen == null) return false;

        // Usa FileWriter con 'true' para hacer append
        try (PrintWriter escritor = new PrintWriter(new BufferedWriter(new FileWriter(ARCHIVO_DB, true)))) {
            
            // 1. Preparar datos
            // Reemplaza saltos de linea del cuerpo por espacios para que ocupe una sola linea
            String cuerpoLimpio = resumen.getCuerpo().replace("\n", " ").replace("\r", " ");
            
            // Unir autores con punto y coma
            String autoresUnidos = String.join(";", resumen.getAutores());
            
            // Unir palabras clave con punto y coma
            String palabrasUnidas = String.join(";", resumen.getPalabrasClaves());

            // 2. Construir la linea: Titulo ## Autores ## Palabras ## Cuerpo
            StringBuilder linea = new StringBuilder();
            linea.append(resumen.getTitulo()).append(SEPARADOR);
            linea.append(autoresUnidos).append(SEPARADOR);
            linea.append(palabrasUnidas).append(SEPARADOR);
            linea.append(cuerpoLimpio);

            // 3. Escribir en el archivo
            escritor.println(linea.toString());
            return true;

        } catch (IOException e) {
            System.err.println("Error al guardar en disco: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga todos los resumenes existentes en el archivo de base de datos.
     * @return Un arreglo de objetos Resumen.
     */
    public Resumen[] cargarResumenes() {
        File archivo = new File(ARCHIVO_DB);
        if (!archivo.exists()) {
            return new Resumen[0]; // Retornar arreglo vacio si no existe 
        }

        // 1. Contar lineas para definir tamao del arreglo
        int cantidad = contarLineas();
        Resumen[] lista = new Resumen[cantidad];

        // 2. Leer y llenar el arreglo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int i = 0;
            
            while ((linea = br.readLine()) != null && i < cantidad) {
                String[] partes = linea.split(SEPARADOR);
                
                // Valida que la linea tenga las partes necesarias
                if (partes.length >= 4) {
                    String titulo = partes[0].trim();
                    
                    String[] autores = partes[1].split(";");
                    
                    String[] palabras = partes[2].split(";");
                    
                    String cuerpo = partes[3].trim();
                    
                    // Crea el objeto y guarda en el arreglo
                    lista[i] = new Resumen(titulo, autores, cuerpo, palabras);
                    i++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando la base de datos: " + e.getMessage());
        }
        
        return lista;
    }

    /**
     * Metodo auxiliar para saber de que tamano crear el arreglo.
     * @return Numero de lineas validas en el archivo.
     */
    private int contarLineas() {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_DB))) {
            while (br.readLine() != null) {
                contador++;
            }
        } catch (IOException e) {
            // Ignorar errores 
        }
        return contador;
    }
}