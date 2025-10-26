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


public class Grafo {
    
    //buscar rápidamente un objeto Usuario por su nombre
    private final Map<String, Usuario> usuarios; 
    
    // Lista de Adyacencia: Almacena las aristas dirigidas
    // Clave: El Usuario que SIGUE (Origen)
    // Valor: La lista de Usuarios que ES seguido (Destino)
    private final Map<Usuario, List<Usuario>> ListaAdj; 

    /**
     * Constructor de la clase Grafo
     * Inicializa las estructuras HashMap para usuarios y la lista de adyacencia
     */
    public Grafo() {
        this.usuarios = new HashMap<>();
        this.ListaAdj = new HashMap<>();
    }

    /**
     * Agrega un nuevo usuario (nodo) al grafo dirigido
     * Si el usuario ya existe, este método no realiza ninguna acción
     * Se inicializa su lista de adyacencia como vacía
     * @param nombre El nombre de usuario (String) a agregar
     */
    public void agregarUsuario(String nombre) {
        if (!usuarios.containsKey(nombre)) {
            Usuario nuevoUsuario = new Usuario(nombre);
            usuarios.put(nombre, nuevoUsuario);
            // Cada usuario debe tener su propia lista de vecinos (destinos)
            ListaAdj.put(nuevoUsuario, new LinkedList<>()); 
        }
    }

    /**
     * Agrega una arista dirigida (relación de seguimiento) al grafo.
     * La arista va desde el usuario origen hacia el usuario destino.
     * @param origenNombre El nombre del usuario que sigue (origen).
     * @param destinoNombre El nombre del usuario que es seguido (destino).
     */
    public void agregarRelaciones(String origenNombre, String destinoNombre) {
        Usuario origen = usuarios.get(origenNombre);
        Usuario destino = usuarios.get(destinoNombre);
        if (origen != null && destino != null) {
            ListaAdj.get(origen).add(destino);
        } else {
            // JOptionPane para manejo de error
            System.err.println("Advertencia de Carga: Relación inválida con usuarios no existentes.");
        }
    }
    /**
     * Devuelve el conjunto de todos los usuarios (nodos) presentes en el grafo.
     * Es crucial para iniciar los recorridos de búsqueda (DFS) en el algoritmo de Kosaraju.
     * @return Un Set de objetos Usuario.
     */
    public Set<Usuario> getTodosLosUsuarios() {
        return ListaAdj.keySet();
    }
    
    /**
     * Devuelve la lista de usuarios que son seguidos por el usuario dado (los vecinos).
     * Representa las aristas salientes del nodo.
     * @param usuario El objeto Usuario del que se buscan los vecinos.
     * @return Una List de objetos Usuario que son seguidos.
     */
    public List<Usuario> getVecinos(Usuario usuario) {
        return ListaAdj.getOrDefault(usuario, new LinkedList<>());
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
     }


    // ¡RECUERDA!
    // Aquí es donde deberás implementar la lógica para el Requerimiento B (Eliminar Usuario)
    // y el Requerimiento C (Actualizar Repositorio, que requerirá un método para
    // recorrer y generar el texto de salida).

