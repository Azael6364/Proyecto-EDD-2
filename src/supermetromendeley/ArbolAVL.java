/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermetromendeley;
import java.util.*;
/**
 * Árbol AVL para indexear autores o palabras clave.
 * Cada nodo guarda un conjunto de títulos de investigaciones asociadas.
 * @param <T> tipo de clave (ejemplo String para autores o palabras clave)
 * @author NITRO
 */
public class ArbolAVL<T extends Comparable<T>> {
    
    private NodoAVL<T> raiz;
    
    /**
     * Nodo interno del árbol AVL.
     */
    private static class NodoAVL<T> {
        T clave;
        Set<String> titulos; // índice de investigaciones asociadas
        NodoAVL<T> izquierdo, derecho;
        int altura;

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
    
    private void actualizarAltura(NodoAVL<T> nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }
    
    private int altura(NodoAVL<T> nodo) {
        return nodo == null ? 0 : nodo.altura;
    }
    
    private int factorBalance(NodoAVL<T> nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }
    
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
        
        return nodo;
    }
    
    private NodoAVL<T> rotacionDerecha(NodoAVL<T> y) {
        NodoAVL<T> x = y.izquierdo;
        NodoAVL<T> T2 = x.derecho;
        
        x.derecho = y;
        y.izquierdo = T2;
        
        actualizarAltura(y);
        actualizarAltura(x);
        
        return x;
    }
    
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
