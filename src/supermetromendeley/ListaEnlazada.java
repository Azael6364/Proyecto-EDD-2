/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermetromendeley;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista enlazada como reemplazo de ArrayList.
 * @author NITRO
 */
public class ListaEnlazada<T> implements Iterable<T> {
    private Nodo<T> head;
    private int size;
    
    public ListaEnlazada(){
        head = null;
        size = 0;
    }
    
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
    
    
    
    public T obtener(int indice) {
        validarIndice(indice);
        Nodo<T> actual = head;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    public boolean contiene(T elemento) {
        for (T dato : this){
            if (dato.equals(elemento)) return true;
        }
        return false;
    }
    
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
    
    public int size() {
        return size;
    }
    
    public boolean estaVacia() {
        return size == 0;
    }
    
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }
    
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
