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
     * Cuenta cuántas veces aparece una palabra en el texto del resumen.
     * @param cuerpo El texto donde buscar.
     * @param palabra La palabra a buscar.
     * @return Número de veces que aparece.
     */
    public int contarFrecuencia(String cuerpo, String palabra) {
        if (cuerpo == null || palabra == null) return 0;

        // 1. Normalizar: pasar todo a minúsculas
        String textoNorm = cuerpo.toLowerCase();
        String palabraNorm = palabra.toLowerCase();

        // 2. Limpiar la puntuación sencilla / puntos y comas
        textoNorm = textoNorm.replace(".", " ").replace(",", " ").replace(";", " ");

        // 3. Separar en palabras individuales
        String[] todasLasPalabras = textoNorm.split("\\s+"); 

        // 4. Contar
        int contador = 0;
        for (String p : todasLasPalabras) {
            if (p.equals(palabraNorm)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Genera el String final que pide el profesor.
     */
    public String analizar(Resumen r) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre del trabajo: ").append(r.getTitulo()).append("\n");
        sb.append("Autores: ").append(r.autoresToString()).append("\n");
        
        for (String clave : r.getPalabrasClaves()) {
            int freq = contarFrecuencia(r.getCuerpo(), clave);
            sb.append(clave).append(": ").append(freq).append("\n");
        }
        return sb.toString();
    }
}