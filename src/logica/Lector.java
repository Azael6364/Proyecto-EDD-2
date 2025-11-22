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

    public Resumen leer(File archivo) {
        String titulo = null;
        String cuerpo = "";
        String[] autores = new String[0];
        String[] palabrasClaves = new String[0];
        
        StringBuilder cuerpoBuilder = new StringBuilder();
        StringBuilder autoresBuilder = new StringBuilder();

        // USAMOS EL LECTOR SIMPLE (Sin InputStreamReader, Sin UTF-8)
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            boolean leyendoAutores = false;
            boolean leyendoResumen = false;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; 

                // 1. Detectar Titulo
                if (titulo == null) {
                    if (!esPalabraReservada(linea)) titulo = linea;
                    continue; 
                }

                // 2. Control de secciones
                if (linea.equalsIgnoreCase("Autores")) {
                    leyendoAutores = true;
                    leyendoResumen = false;
                    continue; 
                } else if (linea.equalsIgnoreCase("Resumen")) {
                    leyendoAutores = false;
                    leyendoResumen = true;
                    continue;
                } else if (linea.toLowerCase().startsWith("palabras claves") || linea.toLowerCase().startsWith("palabras clave")) {
                    leyendoResumen = false;
                    int indiceDosPuntos = linea.indexOf(":");
                    if (indiceDosPuntos != -1) {
                        String listaPalabras = linea.substring(indiceDosPuntos + 1).trim();
                        if (listaPalabras.endsWith(".")) listaPalabras = listaPalabras.substring(0, listaPalabras.length() - 1);
                        palabrasClaves = listaPalabras.split(",");
                        for(int i=0; i < palabrasClaves.length; i++) palabrasClaves[i] = palabrasClaves[i].trim();
                    }
                    break; 
                }

                // 3. Captura
                if (leyendoAutores) autoresBuilder.append(linea).append(";");
                else if (leyendoResumen) cuerpoBuilder.append(linea).append(" ");
            }
            
            // Procesar finales
            String autoresString = autoresBuilder.toString();
            if (!autoresString.isEmpty()) autores = autoresString.split(";");
            cuerpo = cuerpoBuilder.toString().trim();

            if (titulo != null) return new Resumen(titulo, autores, cuerpo, palabrasClaves);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    private boolean esPalabraReservada(String linea) {
        String texto = linea.toLowerCase();
        return texto.equals("autores") || texto.equals("resumen") || texto.startsWith("palabras clave");
    }
}