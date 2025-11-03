package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class Grafo {
    private final Map<String, Usuario> usuarios; 
    private final Map<Usuario, List<Usuario>> adjList; 
    /**
    
     * Inicializa las estructuras HashMap para usuarios y la lista de adyacencia.
     */
    public Grafo() {
        this.usuarios = new HashMap<>();
        this.adjList = new HashMap<>();
    }

    /**
     * Agrega un nuevo usuario (nodo) al grafo dirigido.
     * Si el usuario ya existe, este método no realiza ninguna acción.
     * Se inicializa su lista de adyacencia como vacía.
     * @param nombre El nombre de usuario (String) a agregar, ej: "@pepe".
     */
    public void agregarUsuario(String nombre) {
        if (!usuarios.containsKey(nombre)) {
            Usuario nuevoUsuario = new Usuario(nombre);
            usuarios.put(nombre, nuevoUsuario);
            // Cada usuario debe tener su propia lista de vecinos (destinos)
            adjList.put(nuevoUsuario, new LinkedList<>()); 
        }
    }

    /**
     * Agrega una arista dirigida (relación de seguimiento) al grafo.
     * La arista va desde el usuario origen hacia el usuario destino.
     * @param origenNombre El nombre del usuario que sigue (origen).
     * @param destinoNombre El nombre del usuario que es seguido (destino).
     */
    public void agregarArista(String origenNombre, String destinoNombre) {
        Usuario origen = usuarios.get(origenNombre);
        Usuario destino = usuarios.get(destinoNombre);

        // Verificación esencial para la tolerancia a fallos: ¿Existen ambos usuarios?
        if (origen != null && destino != null) {
            // Se agrega 'destino' a la lista de adyacencia de 'origen'.
            adjList.get(origen).add(destino);
        } else {
            // Esto se manejaría con un JOptionPane en el Controlador para informar al usuario.
            System.err.println("Advertencia de Carga: Relación inválida con usuarios no existentes.");
        }
    }
    /**
     * Devuelve el conjunto de todos los usuarios (nodos) presentes en el grafo.S
     * Es crucial para iniciar los recorridos de búsqueda (DFS) en el algoritmo de Kosaraju.
    
     */
    public Set<Usuario> getTodosLosUsuarios() {
        return adjList.keySet();
    }
    
    /**
     * Devuelve la lista de usuarios que son seguidos por el usuario dado (los vecinos).
     * Representa las aristas salientes del nodo.
     * @param usuario El objeto Usuario del que se buscan los vecinos.
     * @return Una List de objetos Usuario que son seguidos.
     */
    public List<Usuario> getVecinos(Usuario usuario) {
        return adjList.getOrDefault(usuario, new LinkedList<>());
    }
    
    /**
     * Permite obtener un objeto Usuario específico a partir de su nombre.
     * Es útil para buscar el nodo antes de realizar operaciones de análisis.
     * @param nombre El String con el nombre del usuario.
     * @return El objeto Usuario si existe, o null si no se encuentra.
     */
    public Usuario getUsuario(String nombre) {
        return usuarios.get(nombre);
    }


public Grafo obtenerGrafoTranspuesto() {
    Grafo grafoTranspuesto = new Grafo();
    
    // 1. Agregar todos los usuarios al nuevo grafo
    // (Asegúrate de tener el método getListaDeUsuarios())
    for (Usuario u : this.getListaDeUsuarios()) {
        grafoTranspuesto.agregarUsuario(u.getNombre());
    }
    
    // 2. Recorrer todas las aristas originales y agregarlas invertidas
    // (Asegúrate de tener el método getAdyacentes())
    for (Usuario origen : this.getListaDeUsuarios()) {
        for (Usuario destino : this.getAdyacentes(origen)) {
            
            // Arista original es (origen -> destino)
            // Arista transpuesta es (destino -> origen)
            grafoTranspuesto.agregarArista(destino.getNombre(), origen.getNombre());
        }
    }
    
    return grafoTranspuesto;
}

/**
     * @return  */
    public List<Usuario> getListaDeUsuarios() {
        // Devolvemos los valores del mapa 'usuarios', que es la fuente "maestra"
       
        return new ArrayList<>(usuarios.values());
    }
    
    
    
    /**
     * Devuelve la lista de adyacentes (vecinos) de un usuario dado.
     * @param u 
     * @return  
     */
    public List<Usuario> getAdyacentes(Usuario u) {
        
        // devuelve una lista vacía para evitar errores.
        
        return adjList.getOrDefault(u, new LinkedList<>());
    }
}
   //FALTA ELIMINAR Y ACTUALIZAR
