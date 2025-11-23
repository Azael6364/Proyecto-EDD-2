/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import modelo.Resumen;

/**
 * Clase encargada del analisis estadistico del resumenes.
 * Realiza el conteo de frecuencia de palabras clave y genera reportes.
 * @author COMPUGAMER
 */
public class Analizador {

    /**
     * Cuenta cuantas veces aparece una palabra o frase en el texto.
     * @param cuerpo El texto completo donde se realizara la busqueda.
     * @param palabra La palabra o frase clave a buscar.
     * @return Numero entero que representa la frecuencia de aparicion.
     */
    public int contarFrecuencia(String cuerpo, String palabra) {
        if (cuerpo == null || palabra == null || palabra.isEmpty()) return 0;

        // 1. Normalizacion del texto para asegurar coincidencias precisas
        String textoNorm = normalizarTexto(cuerpo);
        String palabraNorm = normalizarTexto(palabra);

        // 2. Algoritmo de busqueda secuencial
        int contador = 0;
        int indice = 0;

        // indexOf devuelve -1 si no encuentra la frase.
        // Si la encuentra, devuelve la posicion y sumamos al contador.
        while ((indice = textoNorm.indexOf(palabraNorm, indice)) != -1) {
            contador++;
            // Avanzamos el indice la longitud de la palabra encontrada para seguir buscando adelante
            indice += palabraNorm.length(); 
        }
        
        return contador;
    }

    /**
     * Metodo auxiliar para normalizar cadenas de texto.
     * Convierte a minusculas y reemplaza signos de puntuacion por espacios
     * para evitar falsos negativos (ej: "palabra." vs "palabra").
     * * @param texto La cadena original.
     * @return La cadena normalizada y limpia.
     */
    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        
        String normalizado = texto.toLowerCase();
        
        // Reemplazamos puntuacion por espacios para no pegar palabras adyacentes
        normalizado = normalizado
            .replace(".", " ")
            .replace(",", " ")
            .replace(";", " ")
            .replace(":", " ")
            .replace("(", " ")
            .replace(")", " ");
            
        // Eliminamos espacios dobles que hayan podido quedar
        return normalizado.trim();
    }

    /**
     * Genera el reporte final con el formato solicitado en el planteamiento del problema.
     * * @param r El objeto Resumen a analizar.
     * @return Un String formateado con el titulo, autores y frecuencias.
     */
    public String analizar(Resumen r) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Analisis de Frecuencia ---\n");
        sb.append("Nombre del trabajo: ").append(r.getTitulo()).append("\n");
        sb.append("Autores: ").append(r.autoresToString()).append("\n");
        
        // Recorremos todas las palabras clave del resumen y calculamos su frecuencia
        for (String clave : r.getPalabrasClaves()) {
            int freq = contarFrecuencia(r.getCuerpo(), clave);
            sb.append(clave).append(": ").append(freq).append("\n");
        }
        
        return sb.toString();
    }
}