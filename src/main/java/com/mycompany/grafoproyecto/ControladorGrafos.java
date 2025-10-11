package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**

 * @author Samj 
 */
public class ControladorGrafos {

    // Variable estática para mantener la única instancia del grafo cargado en memoria
    private static Grafo grafoActual = null; 
    
    // Logger para registrar errores internos
    private static final Logger LOGGER = Logger.getLogger(ControladorGrafos.class.getName());

    /**
     * Carga el archivo de texto por defecto al iniciar el programa 
     * (Requerimiento C - Carga Inicial).
     * El archivo se busca con el nombre "grafo_inicial.txt" en la raíz del proyecto.
     */
    public static void GrafoInicial() {
        try {
            // Asume que el archivo de texto por defecto se llama "grafo_inicial.txt"
            File archivoInicial = new File("grafo_inicial.txt"); 
            
            // Llama al método principal de carga con el archivo por defecto
            CargarGrafo(archivoInicial); 
            
        } catch (Exception e) {
            // Maneja el caso en que el archivo inicial no exista o haya un error
            LOGGER.severe("Error: No se pudo cargar el archivo inicial 'grafo_inicial.txt'.");
            // Muestra una advertencia, pero no detiene el programa
            JOptionPane.showMessageDialog(null, "Advertencia: No se encontró el archivo de carga inicial.", "Carga Inicial", JOptionPane.WARNING_MESSAGE);
        }
    } 
    
    /**
     * Carga la información de usuarios y relaciones desde un archivo de texto 
     * para construir el objeto Grafo (Requerimiento A).
     * @param archivo El objeto File seleccionado por JFileChooser.
     */
    public static void CargarGrafo(File archivo) {
        
        // 1. Verificación de Guardado (Requerimiento A - Alerta)
        if (grafoActual != null) {
            // Se debe notificar al usuario que los datos en memoria se perderán.
            System.out.println("ADVERTENCIA: Se procederá a cargar un grafo nuevo, se perderán los datos en memoria.");
        }
        
        grafoActual = new Grafo(); // Inicializa un grafo nuevo
        String modo = "NONE"; // Bandera para controlar si leemos USERS o RELS
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // Ignora líneas vacías

                // Lógica de Parsing para detectar las secciones del archivo
                if (line.equals("usuarios")) {
                    modo = "USUARIOS";
                    continue;
                } else if (line.equals("relaciones")) {
                    modo = "RELACIONES";
                    continue;
                }
                
                // 2. Construcción del Grafo
                if (modo.equals("USUARIOS")) {
                    grafoActual.agregarUsuario(line);
                    
                } else if (modo.equals("RELACIONES")) {
                    // Separa la cadena por la coma y el espacio (", ")
                    String[] parts = line.split(", "); 
                    if (parts.length == 2) {
                        grafoActual.agregarArista(parts[0], parts[1]);
                    }
                }
            }
            
            // Éxito: El grafo está cargado.
            // Aquí iría el llamado a la visualización (Requerimiento D)
            // Visuales.mostrarGrafo(grafoActual);
            
        } catch (IOException e) {
            // Manejo de error de lectura (Tolerancia a Fallos)
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error de lectura: {0}", e.getMessage());
        }
    }
    
    /**
     * Permite obtener la instancia actual del grafo cargado en memoria.
     * @return El objeto Grafo Dirigido o null si no se ha cargado nada.
     */
    public static Grafo getGrafoActual() {
        return grafoActual;
    }
}

