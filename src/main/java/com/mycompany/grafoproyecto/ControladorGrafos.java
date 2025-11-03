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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**

 * @author Samj 
 */
public class ControladorGrafos {

    
    private static Grafo grafoActual = null; 
    
    // Logger para registrar errores internos
    private static final Logger LOGGER = Logger.getLogger(ControladorGrafos.class.getName());

    /**
     * Carga el archivo de texto por defecto al iniciar el programa 
     *
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

public static List<List<Usuario>> encontrarComponentes() {
        if (grafoActual == null || grafoActual.getListaDeUsuarios().isEmpty()) {
            LOGGER.warning("No hay grafo cargado para analizar.");
            return new ArrayList<>(); // Devuelve lista vacía
        }

        // 

        // --- PASO 1: Primer DFS (sobre G) para llenar la Pila ---
        // Se usa la Pila<T> que implementaste.
        Pila<Usuario> pila = new Pila<>();
        
        // El HashMap 'visitados' rastrea qué nodos ya hemos procesado
        HashMap<Usuario, Boolean> visitados = new HashMap<>();
        
        // Inicializa todos los usuarios como "no visitados"
        for (Usuario u : grafoActual.getListaDeUsuarios()) { 
            visitados.put(u, false);
        }

        // Ejecuta DFS por cada usuario que no haya sido visitado
        for (Usuario u : grafoActual.getListaDeUsuarios()) {
            if (!visitados.get(u)) {
                dfsPaso1(u, visitados, pila);
            }
        }

        // --- PASO 2: Obtener el grafo transpuesto (G^T) ---
        // (Debes implementar este método en Grafo.java)
        Grafo grafoTranspuesto = grafoActual.obtenerGrafoTranspuesto(); 

        // --- PASO 3: Segundo DFS (sobre G^T) usando la Pila ---
        List<List<Usuario>> componentes = new ArrayList<>();
        
        // Reinicia el mapa 'visitados' para el segundo recorrido
        visitados.clear();
        for (Usuario u : grafoTranspuesto.getListaDeUsuarios()) {
            visitados.put(u, false);
        }

        // Mientras la pila no esté vacía, saca un usuario
        while (!pila.isEmpty()) {
            Usuario u = pila.pop();
            
            // Buscamos el nodo 'u' pero en el grafo transpuesto
            Usuario uTranspuesto = grafoTranspuesto.getUsuario(u.getNombre()); 
            
            // Si existe y no ha sido visitado, encontramos un nuevo CFC
            if (uTranspuesto != null && !visitados.get(uTranspuesto)) {
                List<Usuario> nuevoComponente = new ArrayList<>();
                // Inicia el DFS (paso 2) para encontrar todos los miembros de este CFC
                dfsPaso2(uTranspuesto, visitados, nuevoComponente, grafoTranspuesto);
                componentes.add(nuevoComponente);
            }
        }
        
        LOGGER.info("Análisis de Kosaraju completado. Encontrados " + componentes.size() + " CFCs.");
        return componentes;
    }

    /**
     * Helper DFS para el Paso 1 de Kosaraju (Llena la pila).
     * Recorre el grafo G.
     */
    private static void dfsPaso1(Usuario u, HashMap<Usuario, Boolean> visitados, Pila<Usuario> pila) {
        // Marca el nodo actual como visitado
        visitados.put(u, true);
        
        // Recorre todos sus vecinos (a los que sigue)
        for (Usuario v : grafoActual.getAdyacentes(u)) { 
            if (!visitados.get(v)) {
                dfsPaso1(v, visitados, pila);
            }
        }
        
        // Cuando el nodo termina (no tiene más vecinos por visitar), 
        // se añade a la pila.
        pila.push(u);
    }

    /**
     * Helper DFS para el Paso 2 de Kosaraju (Construye los componentes).
     * Recorre el grafo Transpuesto G^T.
     */
    private static void dfsPaso2(Usuario u, HashMap<Usuario, Boolean> visitados, List<Usuario> componente, Grafo transpuesto) {
        // Marca el nodo actual como visitado
        visitados.put(u, true);
        // Lo añade a la lista del componente actual
        componente.add(u); 

        // Recorre sus vecinos (esta vez en el grafo transpuesto)
        for (Usuario v : transpuesto.getAdyacentes(u)) {
            if (!visitados.get(v)) {
                dfsPaso2(v, visitados, componente, transpuesto);
            }
        }
    }
}

