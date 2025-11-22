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
 * Adapta la lectura a autores en multiples lineas.
 * @author COMPUGAMER
 */
public class Lector {

    /**
     * Lee un archivo .txt y extrae la informacion de un articulo
     * Asume el formato: Titulo -> "Autores" -> Lista de nombres -> "Resumen" -> Cuerpo -> "Palabras claves".
     * @param archivo El archivo seleccionado por el usuario.
     * @return Un objeto Resumen con los datos cargados, o null si falla.
     */
    public Resumen leer(File archivo) {
        String titulo = null;
        String cuerpo = "";
        String[] autores = new String[0];
        String[] palabrasClaves = new String[0];
        
        //StringBuilders para ir acumulando texto
        StringBuilder cuerpoBuilder = new StringBuilder();
        StringBuilder autoresBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            // Identificadores para saber en que seccion estamos
            boolean leyendoAutores = false;
            boolean leyendoResumen = false;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim(); // Quitar espacios extra al inicio y final
                if (linea.isEmpty()) continue; // Saltar lineas vacias

                // 1. Detectar el Titulo 
                if (titulo == null) {
                    // Si la linea es una palabra clave reservada, ignorarla
                    if (!esPalabraReservada(linea)) {
                        titulo = linea;
                    }
                    continue; 
                }

                // 2. Control de flujo por secciones
                if (linea.equalsIgnoreCase("Autores")) {
                    leyendoAutores = true;
                    leyendoResumen = false;
                    continue; // Pasamos a la siguiente linea
                } else if (linea.equalsIgnoreCase("Resumen")) {
                    leyendoAutores = false;
                    leyendoResumen = true;
                    continue;
                } else if (linea.toLowerCase().startsWith("palabras claves") || linea.toLowerCase().startsWith("palabras clave")) {
                    leyendoResumen = false;
                    // Extraer lo que esta despues de los dos puntos
                    int indiceDosPuntos = linea.indexOf(":");
                    if (indiceDosPuntos != -1) {
                        String listaPalabras = linea.substring(indiceDosPuntos + 1).trim();
                        // Separar por comas
                        palabrasClaves = listaPalabras.split(",");
                        // Limpiar cada palabra
                        for(int i=0; i < palabrasClaves.length; i++) {
                            palabrasClaves[i] = palabrasClaves[i].trim();
                        }
                    }
                    break; // Terminamos de leer al encontrar las palabras clave
                }

                // 3. Captura de datos segun el identificador activa
                if (leyendoAutores) {
                    // Se usa un separador temporal (;) para luego convertir a arreglo
                    autoresBuilder.append(linea).append(";");
                } else if (leyendoResumen) {
                    // Esta en el cuerpo del resumen
                    cuerpoBuilder.append(linea).append(" ");
                }
            }

            // 4. Procesar los autores acumulados
            String autoresString = autoresBuilder.toString();
            if (!autoresString.isEmpty()) {
                autores = autoresString.split(";");
            }
            
            cuerpo = cuerpoBuilder.toString().trim();

            // Solo devuelve el objeto si tenemos al menos el titulo
            if (titulo != null) {
                return new Resumen(titulo, autores, cuerpo, palabrasClaves);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Metodo auxiliar para evitar confundir titulos con palabras reservadas.
     * @param linea La linea a evaluar.
     * @return true si es una palabra reservada.
     */
    private boolean esPalabraReservada(String linea) {
        String texto = linea.toLowerCase();
        return texto.equals("autores") || texto.equals("resumen") || texto.startsWith("palabras clave");
    }
}
