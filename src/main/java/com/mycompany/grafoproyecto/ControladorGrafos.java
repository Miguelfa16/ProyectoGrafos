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


public class ControladorGrafos {

    private static Grafo grafoActual = null; 
    private static File ArchivoCargado = null;
      
   
    public static void GrafoInicial() throws IOException {
        File archivoInicial = new File("grafo_inicial.txt");
        ArchivoCargado = archivoInicial;
        if (archivoInicial.exists() && archivoInicial.canRead()) {
          
            CargarGrafo(archivoInicial);
        } else { 
           
            throw new IOException("No se pudo encontrar el archivo inicial 'grafo_inicial.txt'.");
        }
    }

   
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

    
    public static Grafo getGrafoActual() {
        return grafoActual;
    }
    
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
    public static void agregarUsuario(String nombreUsuario) {
        if (grafoActual != null) {
            grafoActual.agregarUsuario(nombreUsuario);
        }
    }
    
    public static void eliminarUsuario(String nombreUsuario) {
        if (grafoActual != null) {
       
            grafoActual.EliminarUsuario(nombreUsuario);
        }
    }
    
    public static void agregarRelacion(String origen, String destino) {
        if (grafoActual != null) {
            grafoActual.agregarArista(origen, destino);
        }
    }
    
    public static void eliminarRelacion(String origen, String destino) {
        if (grafoActual != null) {
  
            grafoActual.eliminarArista(origen, destino); 
        }
    }


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
}