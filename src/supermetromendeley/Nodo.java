/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermetromendeley;

/**
 * Esta clase representa un nodo de una lista enlazada simple.
 * Se utiliza como estructura base para implementar la clase ListaEnlazada.
 * 
 * @param <T> el tipo de dato que almacena el nodo
 * @author NITRO
 */
public class Nodo<T> {
    
    /** Dato almacenado en el nodo. */
    T dato;
    /** Referenci al siguiente nodo en la lista. */
    Nodo<T> siguiente;
    
    /**
     * Costructor que inicializa el nodo con un dato.
     * El siguiente nodo se establece como null.
     * 
     * @param dato el dato que se almacenará en el nodo
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    /**
     * Devuelve el dato almacenado en el nodo.
     * 
     * @return el dato de tipo T
     */
    public T getDato() {
        return dato;
    }

    /**
     *Devuelve el nodo siguiente en la lista.
     * 
     * @return el siguiente nodo, o null si no hay más nodos 
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el nodo siguiente en la lista.
     * 
     * @param siguiente el nodo que se enlazará como siguiente
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
