/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import modelo.Resumen;

/**
 * Realiza el conteo de frecuencia de palabras en el resumen.
 * @author COMPUGAMER
 */
public class Analizador {

    /**
     * Cuenta cuantas veces aparece una palabra o frase en el texto.
     */
    public int contarFrecuencia(String cuerpo, String palabra) {
        if (cuerpo == null || palabra == null || palabra.isEmpty()) return 0;

        // Normalización 
        String textoNorm = normalizarTexto(cuerpo);
        String palabraNorm = normalizarTexto(palabra);

        System.out.println("=== BUSCANDO ===");
        System.out.println("Palabra normalizada: '" + palabraNorm + "'");
        System.out.println("Texto normalizado (inicio): '" + 
            textoNorm.substring(0, Math.min(300, textoNorm.length())) + "...'");
        
        int contador = 0;
        int indice = 0;

        // Búsqueda más flexible
        while ((indice = textoNorm.indexOf(palabraNorm, indice)) != -1) {
            System.out.println("¡COINCIDENCIA ENCONTRADA en posición " + indice + "!");
            contador++;
            indice += palabraNorm.length();
            
            // Mostrar contexto de la coincidencia
            int inicioContexto = Math.max(0, indice - 20);
            int finContexto = Math.min(textoNorm.length(), indice + 20);
            System.out.println("Contexto: ..." + textoNorm.substring(inicioContexto, finContexto) + "...");
        }
        
        System.out.println("Frecuencia final: " + contador + " para '" + palabra + "'");
        System.out.println("=== FIN BÚSQUEDA ===\n");
        return contador;
    }

    /**
     * Normalización más completa que maneja mejor los caracteres especiales
     */
    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        
        // Convertir a minúsculas
        String normalizado = texto.toLowerCase();
        
        // Manejar caracteres especiales del español de forma más completa
        normalizado = normalizado
            .replace("á", "a").replace("é", "e").replace("í", "i")
            .replace("ó", "o").replace("ú", "u").replace("ü", "u")
            .replace("ñ", "n")
            .replace("�", "")  // Eliminar caracteres corruptos
            .replace("[", " ").replace("]", " ")
            .replace("(", " ").replace(")", " ")
            .replace("{", " ").replace("}", " ")
            .replace("<", " ").replace(">", " ");
        
        // Reemplazar múltiples espacios por uno solo
        normalizado = normalizado.replaceAll("\\s+", " ").trim();
        
        return normalizado;
    }

    /**
     * Genera el String final con el análisis.
     */
    public String analizar(Resumen r) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Análisis de Frecuencia ---\n");
        sb.append("Nombre del trabajo: ").append(r.getTitulo()).append("\n");
        sb.append("Autores: ").append(r.autoresToString()).append("\n");
        
        System.out.println("=== INICIO ANÁLISIS ===");
        System.out.println("Palabras clave a buscar:");
        for (String clave : r.getPalabrasClaves()) {
            System.out.println(" - '" + clave + "'");
        }
        
        for (String clave : r.getPalabrasClaves()) {
            int freq = contarFrecuencia(r.getCuerpo(), clave);
            sb.append(clave).append(": ").append(freq).append("\n");
        }
        
        System.out.println("=== FIN ANÁLISIS ===");
        return sb.toString();
    }
}