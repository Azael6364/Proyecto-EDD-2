/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermetromendeley;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de una lista enlazada simple como reemplazo de ArrayList.
 * Permite almacenar elementos de cualquier tipo y recorrerlos secuencialmente.
 * 
 * @param <T> el tipo de elementos que almacena la lista
 * @author NITRO
 */
public class ListaEnlazada<T> implements Iterable<T> {
    
    /** Nodo inicial de la lista. */
    private Nodo<T> head;
    private int size;
    
    public ListaEnlazada(){
        head = null;
        size = 0;
    }
    
    /**
     * Agrega un elemento al final de la lista.
     * 
     * @param elemento el dato que se desea agregar
     */
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (head == null){
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        size++;
    }
    
    /**
     * Obtiene el elemento en la posición indicada.
     * 
     * @param indice la posición del elemento (comenzando en 0)
     * @return el elemento en la posición dada, o null si no existe
     */
    public T obtener(int indice) {
        validarIndice(indice);
        Nodo<T> actual = head;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    /**
     * Verifica si la lista contiene un elemento específico.
     * 
     * @param elemento el dato a buscar
     * @return true si el elemento está en la lista, false en caso contrario
     */
    public boolean contiene(T elemento) {
        for (T dato : this){
            if (dato.equals(elemento)) return true;
        }
        return false;
    }
    
    /**
     * Elimina la primera aparición del elemento especificado.
     * 
     * @param elemento el dato a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminar(T elemento) {
        if (head == null) return false;
        if (head.dato.equals(elemento)) {
            head = head.siguiente;
            size--;
        }
        Nodo<T> actual = head;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.equals(elemento)) {
                actual.siguiente = actual.siguiente.siguiente;
                size--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    
    /**
     * Calcula el número de elementos en la lista.
     * 
     * @return cantidad de elementos 
     */
    public int size() {
        return size;
    }
    
    /**
     * Verifica si la lista está vacía.
     * 
     * @return true si no contiene elementos, false en caso contrario 
     */
    public boolean estaVacia() {
        return size == 0;
    }
    
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }
    
    /**
     * Devuelve un iterador para recorrer la lista con for-each.
     * 
     * @return un iterador de tipo T
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = head;
            
            @Override
            public boolean hasNext() {
                return actual != null;
            }
            
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }
}
