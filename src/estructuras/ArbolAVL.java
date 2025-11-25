/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import java.util.*;
/**
 * Árbol AVL para indexear autores o palabras clave.
 * Cada nodo guarda un conjunto de títulos de investigaciones asociadas.
 * @param <T> tipo de clave (ejemplo String para autores o palabras clave)
 * @author NITRO
 */
public class ArbolAVL<T extends Comparable<T>> {
    
    /** Nodo raíz del árbol AVL. */
    private NodoAVL<T> raiz;
    
    /**
     * Nodo interno del árbol AVL.
     * Contiene una clave, un conjunto de títulos asociados, referencias a hijos izquierdo y derecho,
     * y la altura del nodo para mantener el balance del árbol.
     * 
     * @param <T> tipo de clave almacenada en el nodo (debe ser comparable)
     */
    private static class NodoAVL<T> {
        
        T clave; // Clave del nodo (por ejemplo, autor o palabra clave).
        Set<String> titulos; // índice de investigaciones asociadas.
        NodoAVL<T> izquierdo, derecho; // Referencia al hijo izquierdo y derecho del nodo.
        int altura; // Altura del nodo dentro del árbol AVL.

        /**
         * Constructor que inicializa un nodo con una clave y un título asociado.
         * Se crea un conjunto de títulos y se establece la altura inicial en 1.
         * 
         * @param clave la clave que se desea indexear
         * @param titulo el título de investigación asociado a la clave
         */
        NodoAVL(T clave, String titulo) {
            this.clave = clave;
            this.titulos = new LinkedHashSet<>();
            this.titulos.add(titulo);
            this.altura = 1;
        }
    }
    
    // --- Métodos públicos ---
    
    /**
     * Inserta una clave con el título asociado.
     * @param clave
     * @param titulo
     */
    public void insertar(T clave, String titulo) {
        raiz = insertar(raiz, clave, titulo);
    }
    
    /**
     * Devuelve el conjunto de títulos asociados a una clave.
     * @param clave
     * @return 
     */
    public Set<String> obtenerTitulos(T clave) {
        NodoAVL<T> nodo = buscar(raiz, clave);
        return nodo == null ? Collections.emptySet() : nodo.titulos;
    }
    
    /**
     * Busca de forma recursiva un nodo que contenga la clave especificada dentro del árbol AVL.
     * Compara la clave buscada con la clave del nodo actual y decide si continúa por el subárbol izquierdo o derecho.
     * 
     * @param nodo el nodo desde el cual comienza la búsqueda (puede ser la raíz o un subárbol)
     * @param clave la clave que se desea localizar en el árbol
     * @return el nodo que contiene la clave, o null si no se encuentra
     */
    private NodoAVL<T> buscar(NodoAVL<T> nodo, T clave) {
        if (nodo == null) return null;
        int cmp = clave.compareTo(nodo.clave);
        if (cmp == 0) return nodo;
        else if (cmp < 0)  return buscar(nodo.izquierdo, clave);
        else return buscar(nodo.derecho, clave);
    }
    
    /**
     * Devuelve todas las claves en orden alfábetico.
     * @return 
     */
    public List<T> inorden() {
        List<T> lista = new ArrayList<>();
        recorridoInorden(raiz, lista);
        return lista;
    }
    
    // --- Métodos privados ---
    
    private NodoAVL<T> insertar(NodoAVL<T> nodo, T clave, String titulo) {
        if (nodo == null) return new NodoAVL<>(clave, titulo);
        
        int cmp = clave.compareTo(nodo.clave);
        if (cmp < 0) {
           nodo.izquierdo = insertar(nodo.izquierdo, clave, titulo);           
        } else if (cmp > 0) {
            nodo.derecho = insertar(nodo.derecho, clave, titulo);
        } else {
            nodo.titulos.add(titulo); // clave ya existe, solo agregamos título
            return nodo;
        }
        
        actualizarAltura(nodo);
        return balancear(nodo);        
    }
    
    private void recorridoInorden(NodoAVL<T> nodo, List<T> lista) {
        if (nodo == null) return;
        recorridoInorden(nodo.izquierdo, lista);
        lista.add(nodo.clave);
        recorridoInorden(nodo.derecho, lista);
    }
    
    // --- Balanceo y rotaciones ---
    
    /**
     * Actualiza la altura de un nodo en función de las alturas de sus hijos.
     * 
     * @param nodo el nodo cuya altura se desea actualizar 
     */
    private void actualizarAltura(NodoAVL<T> nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }
    
    /**
     * Devuelve la altura de un nodo.
     * 
     * @param nodo el nodo cuya altura se desea consultar
     * @return la altura del nodo, o 0 si es null
     */
    private int altura(NodoAVL<T> nodo) {
        return nodo == null ? 0 : nodo.altura;
    }
    
    /**
     * Calcula el factor de balance de un nodo.
     * El factor se define como la diferencia entre la altura del subárbol izquierdo y derecho.
     * 
     * @param nodo el nodo cuyo balance se desea calcular
     * @return el factor de balance del nodo
     */
    private int factorBalance(NodoAVL<T> nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }
    
    /**
     * Realiza el balanceo de un nodo si su factor de balance indica desequilibrio.
     * Aplica rotaciones simples o dobles según sea el caso: LL, RR, LR, RL.
     * 
     * @param nodo el nodo que se desea balancear
     * @return el nuevo nodo raíz del subárbol balanceado
     */
    private NodoAVL<T> balancear(NodoAVL<T> nodo) {
        int fb = factorBalance(nodo);
        
        // Caso LL
        if (fb > 1 && factorBalance(nodo.izquierdo) >= 0) {
            return rotacionDerecha(nodo);
        }
        // Caso RR
        if (fb < -1 && factorBalance(nodo.derecho) <= 0) {
            return rotacionIzquierda(nodo);
        }
        // Caso LR
        if (fb > 1 && factorBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        // Caso RL
        if (fb < -1 && factorBalance(nodo.derecho) > 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        } 
        // No requiere balanceo
        return nodo;
    }
    
    /**
     * Realiza una rotación simple hacia la derecha sobre el subárbol desequilibrado.
     * Este método se aplica en el caso LL (Left-Left), donde el desequilibrio ocurre en el hijo izquierdo del hijo izquierdo.
     * 
     * @param y el nodo raíz del subárbol desequilibrado
     * @return el nuevo nodo raíz del subárbol después de la rotación
     */
    private NodoAVL<T> rotacionDerecha(NodoAVL<T> y) {
        NodoAVL<T> x = y.izquierdo;
        NodoAVL<T> T2 = x.derecho;
        
        x.derecho = y;
        y.izquierdo = T2;
        
        actualizarAltura(y);
        actualizarAltura(x);
        
        return x;
    }
    
    /**
     * Realiza una rotación simple hacia la izquierda sobre el subárbol desequilibrado.
     * Este método se aplica en el caso RR (Right-Right), donde el desequilibrio ocurre en el hijo derecho del hijo derecho.
     * 
     * @param x el nodo raíz del subárbol desequilibrado
     * @return el nuevo nodo raíz del subárbol después de la rotación
     */
    private NodoAVL<T> rotacionIzquierda(NodoAVL<T> x) {
        NodoAVL<T> y = x.derecho;
        NodoAVL<T> T2 = y.izquierdo;
        
        y.izquierdo = x;
        x.derecho = T2;
        
        actualizarAltura(x);
        actualizarAltura(y);
        
        return y;
    }
}
