/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import estructuras.ArbolAVL;
import estructuras.HashTable;
import estructuras.ListaEnlazada; 
import java.io.File;
import java.util.Set;
import modelo.Resumen;

/**
 * Controlador que conecta la Interfaz con la Lógica y las Estructuras.
 * @author COMPUGAMER
 */
public class Controlador {

    private final Lector lector;
    private final Analizador analizador;
    private final Repositorio repositorio;
    
    // ESTRUCTURAS DE DATOS REALES
    // HashTable<String, Resumen>: Clave=Título, Valor=Objeto Resumen
    private final HashTable<String, Resumen> tablaResumenes; 
    
    // ArbolAVL<String>: Clave=NombreAutor, Guarda=Set de Títulos
    private final ArbolAVL<String> arbolAutores;
    
    // ArbolAVL<String>: Clave=PalabraClave, Guarda=Set de Títulos
    private final ArbolAVL<String> arbolPalabrasClave;

    public Controlador() {
        this.lector = new Lector();
        this.analizador = new Analizador();
        this.repositorio = new Repositorio();
        
        // Inicialización
        this.tablaResumenes = new HashTable<>(); 
        this.arbolAutores = new ArbolAVL<>();
        this.arbolPalabrasClave = new ArbolAVL<>();
        
        cargarDatosAlInicio();
    }

    private void cargarDatosAlInicio() {
        Resumen[] guardados = repositorio.cargarResumenes();
        for (Resumen r : guardados) {
            if (r != null) indexarResumen(r);
        }
    }

    /**
     * Agrega un resumen desde un archivo.
     */
    public String agregarResumen(File archivo) {
        Resumen nuevo = lector.leer(archivo);
        
        if (nuevo == null) return "Error: No se pudo leer el archivo o formato incorrecto.";
        
        // Validación de duplicados en O(1) usando la Hash Table
        if (tablaResumenes.contieneClave(nuevo.getTitulo())) {
            return "Error: El resumen ya existe en la base de datos.";
        }
        
        // Guardar en memoria y disco
        indexarResumen(nuevo);
        repositorio.guardarResumen(nuevo);
        
        return "¡Éxito! Resumen '" + nuevo.getTitulo() + "' agregado correctamente.";
    }
    
    /**
     * Inserta el resumen en la Tabla Hash y en los Árboles AVL.
     */
    private void indexarResumen(Resumen r) {
        // 1. Hash Table
        tablaResumenes.put(r.getTitulo(), r);
        
        // 2. Árbol Autores: Convertimos nombre a minúscula para la clave
        for (String autor : r.getAutores()) {
            // Usamos toLowerCase() para que la búsqueda sea insensible a mayúsculas
            arbolAutores.insertar(autor.trim(), r.getTitulo());
        }
        
        // 3. Árbol Palabras Clave: Convertimos a minúscula para la clave
        for (String palabra : r.getPalabrasClaves()) {
            // Usamos toLowerCase() para estandarizar la búsqueda
            arbolPalabrasClave.insertar(palabra.trim().toLowerCase(), r.getTitulo());
        }
    }
    
    /**
     * Busca un resumen completo dado su título exacto.
     */
    public Resumen buscarResumenPorTitulo(String titulo) {
        return tablaResumenes.get(titulo);
    }

    /**
     * Busca investigaciones por Autor.
     * Recupera los títulos del AVL y luego busca los objetos en la Hash Table.
     */
    public ListaEnlazada<Resumen> buscarPorAutor(String autor) {
        Set<String> titulos = arbolAutores.obtenerTitulos(autor);
        return convertirTitulosAResumenes(titulos);
    }

    /**
     * Busca investigaciones por Palabra Clave.
     */
    public estructuras.ListaEnlazada<Resumen> buscarPorPalabraClave(String palabra) {
        // Convertimos lo que escribió el usuario a minúscula antes de buscar en el árbol
        String busqueda = palabra.trim().toLowerCase();
        
        java.util.Set<String> titulos = arbolPalabrasClave.obtenerTitulos(busqueda);
        return convertirTitulosAResumenes(titulos);
    }
    
    /**
     * Método auxiliar para convertir una lista de títulos (del AVL) 
     * en una lista de objetos Resumen (de la Hash Table).
     */
    private ListaEnlazada<Resumen> convertirTitulosAResumenes(Set<String> titulos) {
        ListaEnlazada<Resumen> resultados = new ListaEnlazada<>();
        for (String titulo : titulos) {
            Resumen r = tablaResumenes.get(titulo);
            if (r != null) {
                resultados.agregar(r);
            }
        }
        return resultados;
    }

    public String analizarResumen(Resumen r) {
        return analizador.analizar(r);
    }
    
    /**
     * Obtiene todos los títulos guardados (útil para llenar listas en la GUI).
     */
    public ListaEnlazada<String> obtenerTodosLosTitulos() {
        return tablaResumenes.claves();
    }
    
    /**
     * Obtiene la lista de todos los autores ordenados alfabéticamente.
     * Usa el recorrido InOrden del Árbol AVL.
     */
    public java.util.List<String> obtenerAutoresRegistrados() {
        return arbolAutores.inorden();
    }
}