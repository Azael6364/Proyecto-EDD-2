/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermetromendeley;

/**
 * Implementación de la tabla de dispersión con direccionamiento abierto.
 * @param <K> Tipo de clave
 * @param <V> Tipo de valor
 * @author NITRO
 */
public class HashTable<K, V> {
    private static final double FACTOR_CARGA_MAXIMO = 0.65;
    
    private Entrada<K, V>[] tabla;
    private int size;
    
    /**
     * Clase interna para representar cada entrada en la tabla.
     */
    private static class Entrada<K, V> {
        K clave;
        V valor;
        boolean borrado;
        
        Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.borrado = false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public HashTable() {
        tabla = (Entrada<K, V>[]) new Entrada[17]; // tamaño inicial 
        size = 0;
    }
    
    /**
     * Función hash personalizada basada en mezcla de caracteres.
     */
    private int hash(K clave) {
        String s = clave.toString();
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);
        }
        return Math.abs(h) % tabla.length;
    }
    
    /**
     *  Inserta o actualiza una entrada.
     * @param clave
     * @param valor
     */
    public void put(K clave, V valor) {
        if ((size + 1.0) / tabla.length > FACTOR_CARGA_MAXIMO) {
            redimensionar();
        }
        
        int idx = hash(clave);
        int inicio = idx;
        int primerBorrado = -1;
        
        while (true) {
            Entrada<K, V> e = tabla[idx];
            if (e == null) {
                if (primerBorrado != -1) idx = primerBorrado;
                tabla[idx] = new Entrada<>(clave, valor);
                size++;
                return;
            }
            if (e.borrado && primerBorrado == -1) {
                primerBorrado = idx;
            } else if (!e.borrado && e.clave.equals(clave)) {
                e.valor = valor;
                return;
            }
            idx = (idx + 1) % tabla.length;
            if (idx == inicio) break;
        }
    }
    
    /**
     * Obtiene el valor asociado a una clave.
     * @param clave
     * @return 
     */
    public V get(K clave) {
        int idx = hash(clave);
        int inicio = idx;
        
        while (true) {
            Entrada<K, V> e = tabla[idx];
            if (e == null) return null;
            if(!e.borrado && e.clave.equals(clave)) return e.valor;
            idx = (idx + 1) % tabla.length;
            if (idx == inicio) return null;
        }
    }
    
    /**
     * Verifica si la clave existe.
     * @param clave
     * @return 
     */
    public boolean contieneClave(K clave) {
        return get(clave) != null;
    }
    
    /**
     * Elimina una clave (marcado lógico).
     * @param clave
     * @return 
     */
    public boolean remove(K clave) {
        int idx = hash(clave);
        int inicio = idx;
        
        while (true) {
            Entrada<K, V> e = tabla[idx];
            if (e == null) return false;
            if (!e.borrado && e.clave.equals(clave)) {
                e.borrado = true;
                size--;
                return true;
            }
            idx = (idx + 1) % tabla.length;
            if (idx == inicio) return false;
        }
    }
    
    /**
     * Devuelve el número de elementos activos.
     * @return 
     */
    public int size() {
        return size;
    }
    
    /**
     * Devuelve todas las claves activas.
     * @return 
     */
    public ListaEnlazada<K> claves() {
        ListaEnlazada<K> lista = new ListaEnlazada<>();
        for (Entrada<K, V> e : tabla) {
            if (e != null && !e.borrado) {
                lista.agregar(e.clave);
            }
        }
        return lista;
    }
    
    /**
     * Redimensiona la tabla cuando se supera el factor de carga.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        Entrada<K, V>[] vieja = tabla;
        tabla = (Entrada<K, V>[]) new Entrada[vieja.length * 2 + 1];
        size = 0;
        
        for (Entrada<K, V> e : vieja) {
            if (e != null && !e.borrado) {
                put(e.clave, e.valor);
            }
        }
    }
}
