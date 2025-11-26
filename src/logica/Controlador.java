/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import estructuras.ArbolAVL;
import estructuras.HashTable;
import estructuras.ListaEnlazada; 
import java.io.File;
import modelo.Resumen;

/**
 * Controlador principal que gestiona toda la lógica de la aplicacion.
 * Coordina las operaciones entre la interfaz grafica, las estructuras de datos 
 * y el sistema de persistencia. Implementa las funcionalidades requeridas 
 * utilizando HashTable para busquedas O(1) y Arboles AVL para ordenamientos eficientes.
 * 
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

    /**
     * Constructor principal del Controlador.
     * Inicializa todas las estructuras de datos y carga la información existente.
     * Si no hay datos previos, precarga ejemplos para demostracion.
     */
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

    /**
     * Carga los datos al iniciar la aplicacion desde el repositorio persistente.
     * Si no existen datos previos, carga ejemplos demostrativos automaticamente.
     * Garantiza que el sistema siempre tenga datos para trabajar.
     */
    private void cargarDatosAlInicio() {
        // 1. Cargar datos existentes del repositorio
        Resumen[] guardados = repositorio.cargarResumenes();
        for (Resumen r : guardados) {
            if (r != null) indexarResumen(r);
        }
        
        // 2. Si no hay datos, precargar ejemplos automáticamente
        if (tablaResumenes.size() == 0) {
            precargarEjemplos();
        }
    }

    /**
     * Precarga resumenes de ejemplo desde archivos de texto cuando la base de datos está vacía.
     * Los archivos deben ubicarse en la carpeta 'ejemplos/' en la raíz del proyecto.
     * 
     */
    private void precargarEjemplos() {
        System.out.println("Iniciando precarga de ejemplos...");
        
        // Lista de archivos de ejemplo ubicados en la carpeta 'ejemplos/'
        String[] archivosEjemplo = {
            "ejemplos/ejemplo1.txt",
            "ejemplos/ejemplo2.txt", 
            "ejemplos/ejemplo3.txt"
        };
        
        int contadorCargados = 0;
        
        for (String rutaArchivo : archivosEjemplo) {
            try {
                File archivo = new File(rutaArchivo);
                if (archivo.exists()) {
                    Resumen nuevo = lector.leer(archivo);
                    if (nuevo != null && !tablaResumenes.contieneClave(nuevo.getTitulo())) {
                        indexarResumen(nuevo);
                        repositorio.guardarResumen(nuevo);
                        contadorCargados++;
                        System.out.println("✓ Precargado: " + nuevo.getTitulo());
                    }
                } else {
                    System.out.println("⚠ Archivo no encontrado: " + rutaArchivo);
                }
            } catch (Exception e) {
                System.err.println("✗ Error al precargar " + rutaArchivo + ": " + e.getMessage());
            }
        }
        
        System.out.println("Precarga completada. " + contadorCargados + " resúmenes agregados.");
    }

    /**
     * Agrega un resumen desde un archivo de texto.
     * Realiza validaciones de formato y duplicados antes de proceder con el almacenamiento.
     * 
     * @param archivo Archivo de texto con el resumen en formato estructurado
     * @return Mensaje de resultado indicando éxito o error especifico
     */
    public String agregarResumen(File archivo) {
        Resumen nuevo = lector.leer(archivo);
        
        if (nuevo == null) return "Error: No se pudo leer el archivo o formato incorrecto.";
        
        // Validacion de duplicados en O(1) usando la Hash Table
        if (tablaResumenes.contieneClave(nuevo.getTitulo())) {
            return "Error: El resumen ya existe en la base de datos.";
        }
        
        // Guardar en memoria y disco
        indexarResumen(nuevo);
        repositorio.guardarResumen(nuevo);
        
        return "¡Éxito! Resumen '" + nuevo.getTitulo() + "' agregado correctamente.";
    }
    
    /**
     * Inserta el resumen en la Tabla Hash y en los Arboles AVL.
     * Este metodo es fundamental para mantener la consistencia entre las estructuras de datos.
     * 
     * @param r Objeto Resumen a indexar en todas las estructuras
     */
    private void indexarResumen(Resumen r) {
        // 1. Hash Table - Búsqueda por titulo en O(1)
        tablaResumenes.put(r.getTitulo(), r);
        
        // 2. Arbol Autores - Búsqueda y ordenamiento eficiente
        for (String autor : r.getAutores()) {
            // Usamos toLowerCase() para que la búsqueda sea insensible a mayúsculas
            arbolAutores.insertar(autor.trim(), r.getTitulo());
        }
        
        // 3. Arbol Palabras Clave - Busqueda y ordenamiento eficiente
        for (String palabra : r.getPalabrasClaves()) {
            // Usamos toLowerCase() para estandarizar la busqueda
            arbolPalabrasClave.insertar(palabra.trim().toLowerCase(), r.getTitulo());
        }
    }
    
    /**
     * Busca un resumen completo dado su titulo exacto.
     * 
     * 
     * @param titulo Titulo exacto del resumen a buscar
     * @return Objeto Resumen encontrado o null si no existe
     */
    public Resumen buscarResumenPorTitulo(String titulo) {
        return tablaResumenes.get(titulo);
    }

    /**
     * Busca investigaciones por Autor.
     * .
     * 
     * @param autor Nombre del autor a buscar
     * @return Lista enlazada con los resúmenes del autor especificado
     */
    public ListaEnlazada<Resumen> buscarPorAutor(String autor) {
        ListaEnlazada<String> titulos = arbolAutores.obtenerTitulos(autor);
        return convertirTitulosAResumenes(titulos);
    }

    /**
     * Busca investigaciones por Palabra Clave.
     * Realiza busqueda insensible a mayusculas mediante normalizacion.
     * 
     * @param palabra Palabra clave a buscar en los resumenes
     * @return Lista enlazada con los resumenes que contienen la palabra clave
     */
    public estructuras.ListaEnlazada<Resumen> buscarPorPalabraClave(String palabra) {
        // Convertimos lo que escribió el usuario a minuscula antes de buscar en el arbol
        String busqueda = palabra.trim().toLowerCase();
        
        ListaEnlazada<String> titulos = arbolPalabrasClave.obtenerTitulos(busqueda);
        return convertirTitulosAResumenes(titulos);
    }
    
    /**
     * Metodo auxiliar para convertir una lista de títulos 
     * en una lista de objetos Resumen.
     * Garantiza la integridad de los datos durante la conversion.
     * 
     * @param titulos Lista de títulos obtenida del Árbol AVL
     * @return Lista de objetos Resumen completos
     */
    private ListaEnlazada<Resumen> convertirTitulosAResumenes(ListaEnlazada<String> titulos) {
        ListaEnlazada<Resumen> resultados = new ListaEnlazada<>();
        for (String titulo : titulos) {
            Resumen r = tablaResumenes.get(titulo);
            if (r != null) {
                resultados.agregar(r);
            }
        }
        return resultados;
    }

    /**
     * Genera un analisis completo de frecuencias de palabras clave para un resumen.
     * 
     * @param r Resumen a analizar
     * @return String formateado con el reporte de análisis
     */
    public String analizarResumen(Resumen r) {
        return analizador.analizar(r);
    }
    
    /**
     * Obtiene todos los titulos guardados (util para llenar listas en la GUI).
     * 
     * @return Lista enlazada con todos los titulos de resúmenes almacenados
     */
    public ListaEnlazada<String> obtenerTodosLosTitulos() {
        return tablaResumenes.claves();
    }
    
    /**
     * Obtiene la lista de todos los autores ordenados alfabeticamente.
     * Usa el recorrido InOrden del Árbol AVL para garantizar el orden.
     * 
     * @return Lista enlazada con autores ordenados alfabeticamente
     */
    public ListaEnlazada<String> obtenerAutoresRegistrados() {
        return arbolAutores.inorden();
    }
    
    /**
     * Obtiene todas las palabras clave ordenadas alfabeticamente.
     * Utiliza el recorrido InOrden del Arbol AVL de palabras clave.
     * 
     * @return Lista enlazada con palabras clave ordenadas alfabeticamente
     */
    public ListaEnlazada<String> obtenerPalabrasClaveListadas() {
        return arbolPalabrasClave.inorden();
    }
}