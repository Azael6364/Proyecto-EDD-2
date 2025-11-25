/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Comparador lexicográfico en español que respeta la ñ, acentos y diéresis.
 * Se utiliza para ordenar cadenas en estructuras como árboles AVL.
 * 
 * @author NITRO
 */
public class ComparadorEspañol implements Comparator<String> {
    private final Collator collator;
    
    /**
     * Crea comparador configurado para el idioma español.
     * Ignora diferencias de mayúsculas y acentos al comparar.
     */
    public ComparadorEspañol() {
        collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); // Ignora acentos y mayúsculas
    }
    
    /**
     * Compara dos cadenas según reglas del idioma español.
     * 
     * @param s1 primera cadena
     * @param s2 segunda cadena
     * @return valor negativo si s1 < s2, positivo si s1 > s2, 0 si son iguales
     */
    @Override
    public int compare(String s1, String s2) {
        return collator.compare(s1, s2);
    }
}
