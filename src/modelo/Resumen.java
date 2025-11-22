/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Representa la información del artículo científico.
 * Contenedor de datos (titulo, autores, cuerpo, palabras clave).
 * @author COMPUGAMER
 */
public class Resumen {
    
    private String titulo;
    private String[] autores;      
    private String cuerpo;
    private String[] palabrasClaves;

    public Resumen(String titulo, String[] autores, String cuerpo, String[] palabrasClaves) {
        this.titulo = titulo;
        this.autores = autores;
        this.cuerpo = cuerpo;
        this.palabrasClaves = palabrasClaves;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return the autores
     */
    public String[] getAutores() {
        return autores;
    }

    /**
     * @return the cuerpo
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * @return the palabrasClaves
     */
    public String[] getPalabrasClaves() {
        return palabrasClaves;
    }

   // --- MÉTODOS DE AYUDA ---

    /**
     * @return Un String con todos los autores separados por coma.
     */
    public String autoresToString() {
        if (autores == null) return "";
        // Unimos el arreglo en un solo String
        return String.join(", ", autores);
    }

    /**
     * Método para ver la info en consola.
     */
    @Override
    public String toString() {
        return "TITULO: " + titulo + "\n" +
               "AUTORES: " + autoresToString() + "\n" +
               "CUERPO: " + (cuerpo.length() > 30 ? cuerpo.substring(0, 30) + "..." : cuerpo);
    }
}

