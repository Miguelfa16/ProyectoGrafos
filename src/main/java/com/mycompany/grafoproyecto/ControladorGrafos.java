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

    
    private static Grafo grafoActual = null; 
    
    // Logger para registrar errores internos
    private static final Logger LOGGER = Logger.getLogger(ControladorGrafos.class.getName());

    /**
     * Carga el archivo de texto por defecto al iniciar el programa 
     * (Requerimiento C - Carga Inicial).
     * El archivo se busca con el nombre "grafo_inicial.txt" en la raíz del proyecto.
     */
    public static void GrafoInicial() {
    File archivoInicial = new File("grafo_inicial.txt");

    if (archivoInicial.exists() && archivoInicial.canRead()) {
        try {
            CargarGrafo(archivoInicial);
        } catch (IOException e) {
            LOGGER.severe("Error al leer el archivo inicial: " + e.getMessage());
        } 
    } 
    else { 
        LOGGER.severe("Error: No se pudo encontrar el archivo inicial 'grafo_inicial.txt'.");
    }
}

    
    /**
     * Carga la información de usuarios y relaciones desde un archivo de texto 
     * para construir el objeto Grafo (Requerimiento A).
     * @param archivo El objeto File seleccionado por JFileChooser.
     * @throws java.io.IOException
     */
    public static void CargarGrafo(File archivo) throws IOException  {
        grafoActual = new Grafo(); 
        String modo = "NONE";        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()){
                    continue;
                } 
                if (line.equals("usuarios")) {
                    modo = "USUARIOS";
                    continue;
                } else if (line.equals("relaciones")) {
                    modo = "RELACIONES";
                    continue;
                }                
                // Construcción del Grafo
                if (modo.equals("USUARIOS")) {
                    grafoActual.agregarUsuario(line);                    
               } else if (modo.equals("RELACIONES")) {                               
                String[] partes = line.split(", ");                                
                if (partes.length == 2) {                                        
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();                   
                    if (!origen.isEmpty() && !destino.isEmpty()) {
                        grafoActual.agregarArista(origen, destino);
                    }
                } 
                
            } 
            
            
        } 
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
