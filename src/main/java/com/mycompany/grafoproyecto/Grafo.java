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
   /**
    * Devuelve una lista de todos los objetos usuario en el grafo 
    * @return Lista ed usuarios 
    */
    public Lista<Usuario> getListaDeUsuarios() {
        return usuarios.values();
    }
    /** 
     * obtiene todos los usuarios que tienen relaciones 
     * @return lista de usuarios  
     */
    public Lista<Usuario> getTodosLosUsuarios() {
        return adjList.keySet(); 
}
    /** 
     * devuelve una lista de vecinos de un usuario especifico 
     * @param usuario Usuario dado
     * @return Lista de usuarios a los que sigue 
     */
    public Lista<Usuario> getVecinos(Usuario usuario) {
    Lista<Usuario> vecinos = adjList.get(usuario); 
    return (vecinos != null) ? vecinos : new Lista<>(); 
}
    /**
     * elimina un usuario del grafo 
     * @param nombre usuario a eliminar 
     * @return true si el usuario existia, false si no
     */
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
    /** 
     * elimina una relacion de dos usuarios especificos 
     * @param origenNombre usario de donde sale la relacion 
     * @param destinoNombre usuario donde llega la relacion 
     */
    public void eliminarArista(String origenNombre, String destinoNombre) {
        Usuario origen = getUsuario(origenNombre);
        Usuario destino = getUsuario(destinoNombre);
        if (origen != null && destino != null) {
            Lista<Usuario> vecinosDelOrigen = adjList.get(origen);
            if (vecinosDelOrigen != null) {
                vecinosDelOrigen.Remover(destino); 
            }
        }
    }
    
    /**
     * Crea y devuelve un nuevo objeto Grafo que es igual al original pero con las aristas invertidas 
     * * @return objeto grafo 
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
    
    /** 
     * transforma la lista de adyacencia en una lista de strings 
     * @return lista de strings, cada string es una relacion  
     */
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
  
