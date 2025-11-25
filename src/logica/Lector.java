/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader; 
import java.io.IOException;
import modelo.Resumen;

/**
 * Clase encargada de leer archivos de texto con el formato del proyecto.
 * Implementa la logica de parsing para detectar titulo, autores, cuerpo y palabras Clave
 * basandose en palabras reservadas dentro del archivo.
 * @author COMPUGAMER
 */
public class Lector {

    /**
     * Lee un archivo .txt y extrae la informacion estructurada.
     * Utiliza FileReader para compatibilidad con la codificacion por defecto del sistema.
     * * @param archivo El archivo fisico seleccionado por el usuario (JFileChooser).
     * @return Un objeto Resumen con los datos cargados, o null si ocurre un error.
     */
    public Resumen leer(File archivo) {
        String titulo = null;
        String cuerpo = "";
        String[] autores = new String[0];
        String[] palabrasClaves = new String[0];
        
        // StringBuilder para concatenar eficientemente sin crear multiples objetos String
        StringBuilder cuerpoBuilder = new StringBuilder();
        StringBuilder autoresBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            boolean leyendoAutores = false;
            boolean leyendoResumen = false;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // Saltar lineas vacias

                // 1. Logica para detectar el Titulo
                if (titulo == null) {
                    if (!esPalabraReservada(linea)) {
                        titulo = linea;
                    }
                    continue; 
                }

                // 2. Deteccion de secciones
                if (linea.equalsIgnoreCase("Autores")) {
                    leyendoAutores = true;
                    leyendoResumen = false;
                    continue; 
                } else if (linea.equalsIgnoreCase("Resumen")) {
                    leyendoAutores = false;
                    leyendoResumen = true;
                    continue;
                } else if (linea.toLowerCase().startsWith("palabras claves") || linea.toLowerCase().startsWith("palabras clave")) {
                    // Palabras Clave
                    leyendoResumen = false;
                    int indiceDosPuntos = linea.indexOf(":");
                    if (indiceDosPuntos != -1) {
                        String listaPalabras = linea.substring(indiceDosPuntos + 1).trim();
                        // Quitar punto final si existe
                        if (listaPalabras.endsWith(".")) {
                            listaPalabras = listaPalabras.substring(0, listaPalabras.length() - 1);
                        }
                        
                        // Separar por comas y limpiar espacios
                        palabrasClaves = listaPalabras.split(",");
                        for(int i=0; i < palabrasClaves.length; i++) {
                            palabrasClaves[i] = palabrasClaves[i].trim();
                        }
                    }
                    break; // Termina de leer al encontrar las palabras clave
                }

                // 3. Captura de datos segun la seccion activa
                if (leyendoAutores) {
                    // Usamos punto y coma como separador temporal para autores verticales
                    autoresBuilder.append(linea).append(";");
                } else if (leyendoResumen) {
                    cuerpoBuilder.append(linea).append(" ");
                }
            }
            
            // Procesamiento final de los datos acumulados
            String autoresString = autoresBuilder.toString();
            
            if (!autoresString.isEmpty()) {
                autores = autoresString.split(";");
                
                // Recorremos cada autor para quitar guiones y espacios extra
                for (int i = 0; i < autores.length; i++) {
                    autores[i] = autores[i].replace("-", " ").trim();
                }
                // -------------------------------------------
            }
            
            cuerpo = cuerpoBuilder.toString().trim();

            // Validacion minima: Debe existir un titulo para crear el objeto
            if (titulo != null) {
                return new Resumen(titulo, autores, cuerpo, palabrasClaves);
            }

        } catch (IOException e) {
            System.err.println("Error de Entrada/Salida al leer el archivo: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Verifica si una linea corresponde a una palabra reservada de la estructura del archivo.
     * Ayuda a evitar que 'Autores' o 'Resumen' sean confundidos con el titulo.
     * * @param linea La linea de texto a evaluar.
     * @return true si es una palabra reservada, false en caso contrario.
     */
    private boolean esPalabraReservada(String linea) {
        String texto = linea.toLowerCase();
        return texto.equals("autores") || texto.equals("resumen") || texto.startsWith("palabras clave");
    }
}