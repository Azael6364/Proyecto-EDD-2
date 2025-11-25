/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package supermetromendeley;

import Interfaz.MainFrame;

/**
 * Clase principal (Main) que inicia la aplicación.
 * @author COMPUGAMER
 */
public class SuperMetroMendeley {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Iniciar la ventana de forma segura
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame ventana = new MainFrame();
                ventana.setVisible(true);
            }
        });
    }
    
}
