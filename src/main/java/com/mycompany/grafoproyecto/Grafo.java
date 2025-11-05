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
            // Obtenemos la lista de vecinos
            Lista<Usuario> vecinos = adjList.get(origen);
            
           
            // Solo la agregamos si NO la contiene ya
            if (!vecinos.ContieneA(destino)) {
                vecinos.Agregar(destino);
            }
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
    
    public void eliminarArista(String origenNombre, String destinoNombre) {
        Usuario origen = getUsuario(origenNombre);
        Usuario destino = getUsuario(destinoNombre);
        
        // Si ambos usuarios existen
        if (origen != null && destino != null) {
            // 1. Obtiene la lista de vecinos del origen
            Lista<Usuario> vecinosDelOrigen = adjList.get(origen);
            
            // 2. Si la lista existe, elimina al destino de esa lista
            if (vecinosDelOrigen != null) {
                // Usa tu método Remover()
                vecinosDelOrigen.Remover(destino); 
            }
        }
    }
    
    /**
     * Crea y devuelve un NUEVO objeto Grafo que es el transpuesto del actual.Recorre todas las aristas (origen -> destino) y las agrega como
 (destino -> origen) en el nuevo grafo.
     * * @return Un objeto Grafo completamente nuevo que es el transpuesto.
     * @return 
     */
    public Grafo obtenerGrafoTranspuesto() {
        Grafo grafoTranspuesto = new Grafo();
        
        // 1. Obtenemos la lista de todos los usuarios
        Lista<Usuario> listaDeUsuarios = this.getListaDeUsuarios();
        
        // 2. Agregamos todos los usuarios al nuevo grafo (para que existan)
        for (int i = 0; i < listaDeUsuarios.Tamaño(); i++) {
            grafoTranspuesto.agregarUsuario(listaDeUsuarios.ObtenerPorIndice(i).getNombre());
        }
        
        // 3. Recorremos todas las aristas originales y agregamos las invertidas
        for (int i = 0; i < listaDeUsuarios.Tamaño(); i++) {
            Usuario origen = listaDeUsuarios.ObtenerPorIndice(i);
            
            // Usamos tu método getVecinos()
            Lista<Usuario> vecinos = this.getVecinos(origen);
            
            for (int j = 0; j < vecinos.Tamaño(); j++) {
                Usuario destino = vecinos.ObtenerPorIndice(j);
                
                // ¡LA MAGIA! Agregamos la arista invertida
                // (destino.getNombre() -> origen.getNombre())
                grafoTranspuesto.agregarArista(destino.getNombre(), origen.getNombre());
            }
        }
        return grafoTranspuesto;
    }
    
//convierte las relaciones en una lista para escribirlo en el txt en controlador grafos.   
    public Lista<String> transformarrelaciones() {
        Lista<String> relaciones = new Lista<>();
        
        // Usamos adjList.keySet() para obtener solo los usuarios
        // que SÍ siguen a alguien (los orígenes de las aristas).
        Lista<Usuario> listaOrigenes = adjList.keySet(); 
        
        for (int i = 0; i < listaOrigenes.Tamaño(); i++) {
            Usuario origen = listaOrigenes.ObtenerPorIndice(i);
            Lista<Usuario> listaDestinos = adjList.get(origen);
            
            for (int j = 0; j < listaDestinos.Tamaño(); j++) {
                Usuario destino = listaDestinos.ObtenerPorIndice(j);
                // Formato: @pepe, @maria
                relaciones.Agregar(origen.getNombre() + ", " + destino.getNombre());
            }
        }
        return relaciones;
    }
    
}
  
