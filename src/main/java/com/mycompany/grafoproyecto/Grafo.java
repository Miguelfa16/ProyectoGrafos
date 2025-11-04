package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




public class Grafo {
    private final Mapa<String, Usuario> usuarios; 
    private final Mapa<Usuario, Lista<Usuario>> adjList; 
    /**
    
     * Inicializa las estructuras HashMap para usuarios y la lista de adyacencia.
     */
    public Grafo() {
        this.usuarios = new Mapa<>();
        this.adjList = new Mapa<>();
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
            adjList.put(nuevoUsuario, new Lista<>()); 
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
        if (origen != null && destino != null) {
            adjList.get(origen).Agregar(destino);
        } else {
            System.err.println("Advertencia de Carga: Relación inválida con usuarios no existentes.");
        }
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
   
    public Lista<Usuario> getListaDeUsuarios() {
        return usuarios.values();
    }
    
    public Lista<Usuario> getTodosLosUsuarios() {
        return adjList.keySet(); 
}
    public Lista<Usuario> getVecinos(Usuario usuario) {
    Lista<Usuario> vecinos = adjList.get(usuario); 
    return (vecinos != null) ? vecinos : new Lista<>(); 
}
    
    public boolean EliminarUsuario(String nombre){ 
        Usuario UsuarioEliminar = getUsuario(nombre); 
        if (UsuarioEliminar == null){ 
            return false; 
        }
        adjList.remove(UsuarioEliminar);
        Lista<Usuario> Claves = adjList.keySet();
       for (int i = 0; i < Claves.Tamaño(); i++) {
            Usuario origen = Claves.ObtenerPorIndice(i);
            Lista<Usuario> vecinos = adjList.get(origen);
            if (vecinos != null) {
                vecinos.Remover(UsuarioEliminar);
            }
        }
        usuarios.remove(nombre);
        return true; 
    }
    
}
  
