/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa un articulo cientifico.
 * Almacena la informacion sacada del archivo de texto para luego ser procesada
 * en las estructuras de datos.
 * @author COMPUGAMER
 */
public class Resumen {
    
    private String titulo;
    private String[] autores;      
    private String cuerpo;
    private String[] palabrasClaves;

    /**
     * Constructor principal de la clase Resumen.
     * @param titulo El titulo de la investigacion.
     * @param autores Arreglo de Strings con los nombres de los autores.
     * @param cuerpo El texto completo del contenido del resumen.
     * @param palabrasClaves Arreglo de Strings con las palabras clave normalizadas.
     */
    public Resumen(String titulo, String[] autores, String cuerpo, String[] palabrasClaves) {
        this.titulo = titulo;
        this.autores = autores;
        this.cuerpo = cuerpo;
        this.palabrasClaves = palabrasClaves;
    }

    /**
     * Obtiene el titulo de la investigacion.
     * Se utilizara como clave para la funcion Hash.
     * @return String con el titulo original.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtiene la lista de autores del articulo.
     * Se utilizara para llenar el arbol AVL de autores.
     * @return Un arreglo de Strings, donde cada elemento es un nombre.
     */
    public String[] getAutores() {
        return autores;
    }

    /**
     * Obtiene el cuerpo o contenido del resumen.
     * Se utiliza para realizar el conteo de frecuencia de palabras.
     * @return String con el texto completo.
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * Obtiene el arreglo de palabras claves asociadas al articulo.
     * Se utilizara para llenar el arbol AVL de palabras clave.
     * @return Un arreglo de Strings con las frases clave.
     */
    public String[] getPalabrasClaves() {
        return palabrasClaves;
    }

   // METODOS

    /**
     * Genera una cadena de texto con todos los autores separados por coma.
     * Util para mostrar la informacion en la interfaz grafica.
     * @return String concatenado de autores.
     */
    public String autoresToString() {
        if (autores == null) return "";
        // Unir el arreglo en un solo String separado por comas
        return String.join(", ", autores);
    }

    /**
     * Representacion en texto del objeto para depuracion en consola.
     * @return String con el formato legible del resumen.
     */
    @Override
    public String toString() {
        return "TITULO: " + titulo + "\n" +
               "AUTORES: " + autoresToString() + "\n" +
               "CUERPO: " + (cuerpo.length() > 30 ? cuerpo.substring(0, 30) + "..." : cuerpo);
    }
}
