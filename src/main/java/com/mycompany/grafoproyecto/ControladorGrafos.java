package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * clase que maneja al grafo cargado 
 * carga, guarda, modifica y analiza el grafo 
 * @author Miguel Figueroa, Samir Nassar 
 */
public class ControladorGrafos {
    /** 
     * grafo cargado 
     */
    private static Grafo grafoActual = null; 
    /**
     * archivo txt de donde se cargo el grafo 
     */
    private static File ArchivoCargado = null;
      
    /** 
     * metodo para cargar el grafo desde un archivo grafo_inicial.txt 
     * @throws IOException si no encuentra el archivo o no lo puede leer  
     */
    public static void GrafoInicial() throws IOException {
        File archivoInicial = new File("grafo_inicial.txt");
        ArchivoCargado = archivoInicial;
        if (archivoInicial.exists() && archivoInicial.canRead()) {
          
            CargarGrafo(archivoInicial);
        } else { 
           
            throw new IOException("No se pudo encontrar el archivo inicial 'grafo_inicial.txt'.");
        }
    }

    /** 
     * Carga un grafo seleccionado por el usuario desde un .txt
     * @param archivo archivo de donde se va a cargar el grafo 
     * @throws IOException si hay errores de lectura 
     */
    public static void CargarGrafo(File archivo) throws IOException {
        grafoActual = new Grafo(); 
        ArchivoCargado = archivo;
        String modo = "NONE";         
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();                
                if (line.isEmpty()) {
                    continue; 
                }
                if (line.equals("usuarios")) {
                    modo = "USUARIOS";
                    continue;
                } else if (line.equals("relaciones")) {
                    modo = "RELACIONES";
                    continue;
                }                
                if (modo.equals("USUARIOS")) {
                    grafoActual.agregarUsuario(line);                    
                } else if (modo.equals("RELACIONES")) {
                    String[] partes = line.split("\\s*,\\s*"); // Separa por "," con o sin espacios
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
     * obtiene el grafo que esta cargado actualmente
     * @return el grafo cargado 
     */
    public static Grafo getGrafoActual() {
        return grafoActual;
    }
    /** 
     * Metodo para guaradar los cambios en el archivo .txt
     */
    public static void GuardarCambios() {
        if (grafoActual == null) {
        JOptionPane.showMessageDialog(null, "No hay grafo en la memoria","Error de Guardado",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(ArchivoCargado))) {
            pw.println("usuarios");
            Lista<Usuario> usuarios = grafoActual.getListaDeUsuarios(); 
            for (int i = 0; i < usuarios.Tamaño(); i++) {
                pw.println(usuarios.ObtenerPorIndice(i).getNombre());
            }
            pw.println("relaciones");
            Lista<String> relaciones = grafoActual.transformarrelaciones();
            for (int i = 0; i < relaciones.Tamaño(); i++) {
                pw.println(relaciones.ObtenerPorIndice(i));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar automaticamente" +e.getMessage(),"Error de Gurdado",JOptionPane.ERROR_MESSAGE);
        }
    }
    /** 
     * agrega un usaurio dado al grafo 
     * @param nombreUsuario usuario a agregar
     */
    public static void agregarUsuario(String nombreUsuario) {
        if (grafoActual != null) {
            grafoActual.agregarUsuario(nombreUsuario);
        }
    }
    /** 
     * elimina un usuario dado 
     * @param nombreUsuario usuario a eliminar 
     */
    public static void eliminarUsuario(String nombreUsuario) {
        if (grafoActual != null) {
       
            grafoActual.EliminarUsuario(nombreUsuario);
        }
    }
    /** 
     * establece una relacion entre dos usuarios dados 
     * @param origen usuario de donde sale la relacion 
     * @param destino usuario a donde llega la relacion 
     */
    public static void agregarRelacion(String origen, String destino) {
        if (grafoActual != null) {
            grafoActual.agregarArista(origen, destino);
        }
    }
    /** 
     * elimina la relacion de dos usuarios dados 
     * @param origen usuario de donde sale la realcion 
     * @param destino usuario a donde llega la relacion 
     */
    public static void eliminarRelacion(String origen, String destino) {
        if (grafoActual != null) {
  
            grafoActual.eliminarArista(origen, destino); 
        }
    }

    /** 
     * Primer paso para algorritmo de kosaraju
     * recorre el grafo y llena la pila 
     * @param u usuario acutal 
     * @param visitados mapa de nodos que se visitaroon
     * @param pila pila que se llena 
     */
    private static void dfsPaso1(Usuario u, Mapa<Usuario, Boolean> visitados, Pila<Usuario> pila) {
        visitados.put(u, true); 
        Lista<Usuario> vecinos = grafoActual.getVecinos(u); 
        for (int i = 0; i < vecinos.Tamaño(); i++) {
            Usuario v = vecinos.ObtenerPorIndice(i);
            if (!visitados.get(v)) {
                dfsPaso1(v, visitados, pila);
            }
        }
        pila.Agregar(u);
    }
    /** 
     * segundo paso del algoritmo de kosaraju 
     * recorre el grafo transpuesto, agrupa nodos 
     * @param u usuario actual en el grafo transpuesto 
     * @param visitados mapa de nodos visitados  
     * @param componente lista que se esta llenando  
     * @param transpuesto grafo transpuesto 
     */
    private static void dfsPaso2(Usuario u, Mapa<Usuario, Boolean> visitados, Lista<Usuario> componente, Grafo transpuesto) {
        visitados.put(u, true);
        componente.Agregar(u);
        Lista<Usuario> vecinos = transpuesto.getVecinos(u); 
        for (int i = 0; i < vecinos.Tamaño(); i++) {
            Usuario v = vecinos.ObtenerPorIndice(i);
            if (!visitados.get(v)) {
                dfsPaso2(v, visitados, componente, transpuesto);
            }
        }
    }      
    /** 
     * encuetra elementos fuertemente conectados, algoritmo Kosaraju
     * @return una lista que contiene una lista de usuarios 
     * @throws Exception si no hay grafo cargado 
     */
    public static Lista<Lista<Usuario>> encontrarComponentes() throws Exception {
        if (grafoActual == null || grafoActual.getListaDeUsuarios().esVacio()) {
         
            throw new Exception("No hay grafo cargado para analizar.");
        }     
        Pila<Usuario> pila = new Pila<>();
        Mapa<Usuario, Boolean> visitados = new Mapa<>();
        Lista<Usuario> usuarios = grafoActual.getListaDeUsuarios();             
        for (int i = 0; i < usuarios.Tamaño(); i++) {
            visitados.put(usuarios.ObtenerPorIndice(i), false);
        }
        for (int i = 0; i < usuarios.Tamaño(); i++) {
            Usuario u = usuarios.ObtenerPorIndice(i);           
            if (!visitados.get(u)) { 
                dfsPaso1(u, visitados, pila);
            }
        }
        Grafo grafoTranspuesto = grafoActual.obtenerGrafoTranspuesto(); 
        Lista<Lista<Usuario>> componentes = new Lista<>();  
        visitados = new Mapa<>(); 
        Lista<Usuario> usuariosTranspuestos = grafoTranspuesto.getListaDeUsuarios();
        for (int i = 0; i < usuariosTranspuestos.Tamaño(); i++) {
            visitados.put(usuariosTranspuestos.ObtenerPorIndice(i), false);
        }
        while (!pila.isEmpty()) {
            Usuario u = pila.Eliminar();
            Usuario uTranspuesto = grafoTranspuesto.getUsuario(u.getNombre()); 
            if (uTranspuesto != null && !visitados.get(uTranspuesto)) {
                Lista<Usuario> nuevoComponente = new Lista<>();
                dfsPaso2(uTranspuesto, visitados, nuevoComponente, grafoTranspuesto);
                componentes.Agregar(nuevoComponente); 
            }
        }     
        return componentes;
    }
}