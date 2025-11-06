package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * representacion de grafo dirigido 
 * @author Samir Nassar, Miguel Figueroa
 */
public class Grafo {
    /** 
     * guarda todos los usuarios por su nombre 
     */
    private final Mapa<String, Usuario> usuarios; 
    /** 
     * guarda las relaciones 
     */
    private final Mapa<Usuario, Lista<Usuario>> adjList; 
    /**
     * constructor del grafo 
     */
    public Grafo() {
        this.usuarios = new Mapa<>();
        this.adjList = new Mapa<>();
    }

    /**
     * Agrega un nuevo usuarioal grafo
     * si ya existe el usuario no hace nada 
     * @param nombre El nombre de usuario a agregar
     */
    public void agregarUsuario(String nombre) {
        if (!usuarios.containsKey(nombre)) {
            Usuario nuevoUsuario = new Usuario(nombre);
            usuarios.put(nombre, nuevoUsuario);
            adjList.put(nuevoUsuario, new Lista<>()); 
        }
    }

    /**
     * agrega relaciones desde un usuario especidfico a otro
     * @param origenNombre El nombre del usuario que sigue 
     * @param destinoNombre El nombre del usuario que es seguido 
     */
    public void agregarArista(String origenNombre, String destinoNombre) {
        Usuario origen = usuarios.get(origenNombre);
        Usuario destino = usuarios.get(destinoNombre);
        if (origen != null && destino != null) {
            Lista<Usuario> vecinos = adjList.get(origen);
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
     * Crea y devuelve un NUEVO objeto Grafo que es el transpuesto del actual.Recorre todas las aristas (origen -> destino) y las agrega como (destino -> origen) en el nuevo grafo.
     * * @return Un objeto Grafo completamente nuevo que es el transpuesto.
     * @return 
     */
    public Grafo obtenerGrafoTranspuesto() {
        Grafo grafoTranspuesto = new Grafo();
        Lista<Usuario> listaDeUsuarios = this.getListaDeUsuarios();
        for (int i = 0; i < listaDeUsuarios.Tamaño(); i++) {
            grafoTranspuesto.agregarUsuario(listaDeUsuarios.ObtenerPorIndice(i).getNombre());
        }
        for (int i = 0; i < listaDeUsuarios.Tamaño(); i++) {
            Usuario origen = listaDeUsuarios.ObtenerPorIndice(i);
            Lista<Usuario> vecinos = this.getVecinos(origen);           
            for (int j = 0; j < vecinos.Tamaño(); j++) {
                Usuario destino = vecinos.ObtenerPorIndice(j);
                grafoTranspuesto.agregarArista(destino.getNombre(), origen.getNombre());
            }
        }
        return grafoTranspuesto;
    }
     
    public Lista<String> transformarrelaciones() {
        Lista<String> relaciones = new Lista<>();
        Lista<Usuario> listaOrigenes = adjList.keySet(); 
        for (int i = 0; i < listaOrigenes.Tamaño(); i++) {
            Usuario origen = listaOrigenes.ObtenerPorIndice(i);
            Lista<Usuario> listaDestinos = adjList.get(origen);          
            for (int j = 0; j < listaDestinos.Tamaño(); j++) {
                Usuario destino = listaDestinos.ObtenerPorIndice(j);
                relaciones.Agregar(origen.getNombre() + ", " + destino.getNombre());
            }
        }
        return relaciones;
    }
    
}
  
