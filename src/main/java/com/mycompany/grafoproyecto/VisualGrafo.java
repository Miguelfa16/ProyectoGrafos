 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.grafoproyecto;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import java.awt.BorderLayout;


/**
 * Ventan donde se usa GraphStream
 * @author Samir Nassar, Miguel Figueroa 
 */
public class VisualGrafo extends javax.swing.JFrame {
    /** 
     * grafo con sus datos 
     */  
    private Grafo grafo;
    /** 
     * el grafo que se va a visualizar 
     */
    private Graph miGrafoDeStream; 
    /** 
     * lista con grtupo de usuarios 
     */
    private Lista<Lista<Usuario>> componentesEncontrados = null; 
    
    /** 
     * constructor para mostrar el grafo
     * @param grafo 
     */
    public VisualGrafo(Grafo grafo) {
        this.grafo = grafo; 
        initComponents();
        IniciarVisualizacion(); 
    }
    /** 
     * constructor para mostrar el grafo con los componentes fuertemente conectados 
     * @param grafo grafo que se va a visualizar 
     * @param componentes lista de elementos fuertemente conectados
     */
    public VisualGrafo(Grafo grafo, Lista<Lista<Usuario>> componentes) {
    this.grafo = grafo;
    this.componentesEncontrados = componentes; 
    initComponents();     
    IniciarVisualizacion(); 
    aplicarColoresComponentes(); 
}
    /** 
     * Metodo que construye el grafo con grahstream 
     */
    private void IniciarVisualizacion() {
    System.setProperty("org.graphstream.ui", "swing");
    this.miGrafoDeStream = new SingleGraph("Mi Grafo");
    String styleSheet ="node {" + "text-size: 20px;" + "text-style: bold;" + "text-alignment: under;" +    "}" +"edge {" +"   arrow-size: 10px, 5px;" + "}";
    this.miGrafoDeStream.setAttribute("ui.stylesheet", styleSheet);
    Lista<Usuario> todosLosUsuarios = this.grafo.getTodosLosUsuarios();
    for (int i = 0; i < todosLosUsuarios.Tamaño(); i++) { 
       Usuario u = todosLosUsuarios.ObtenerPorIndice(i);
       String nombreUsuario = u.toString();
      this.miGrafoDeStream.addNode(nombreUsuario);
       this.miGrafoDeStream.getNode(nombreUsuario).setAttribute("ui.label", nombreUsuario);
    } 
    for (int i = 0; i < todosLosUsuarios.Tamaño(); i++) { 
        Usuario origen = todosLosUsuarios.ObtenerPorIndice(i);
        String nombreOrigen = origen.toString();  
        Lista<Usuario> vecinos = this.grafo.getVecinos(origen);
        for (int j = 0; j < vecinos.Tamaño(); j++) {  
            Usuario destino = vecinos.ObtenerPorIndice(j);
            String nombreDestino = destino.toString();
            String idArista = nombreOrigen + "->" + nombreDestino;
            this.miGrafoDeStream.addEdge(idArista, nombreOrigen, nombreDestino, true);                
        }
    }    
    SwingViewer viewer = new SwingViewer(this.miGrafoDeStream, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
    viewer.enableAutoLayout();
    org.graphstream.ui.swing_viewer.ViewPanel viewPanel = (org.graphstream.ui.swing_viewer.ViewPanel) viewer.addDefaultView(false);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(viewPanel, BorderLayout.CENTER); 
    this.setTitle("Visualizacion Grafo");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
    this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
    }
    
    /**
     * recorre los componentes fuertemente conectados encontrados y se le asigna un color 
     */
    private void aplicarColoresComponentes() {
        if (this.componentesEncontrados == null || this.miGrafoDeStream == null) {
            return; 
        }
        String[] colores = { "red", "green", "blue", "orange", "cyan", "magenta" };        
        for (int i = 0; i < this.componentesEncontrados.Tamaño(); i++) {
            String colorActual = colores[i % colores.length]; 
            Lista<Usuario> componente = this.componentesEncontrados.ObtenerPorIndice(i);            
            for (int j = 0; j < componente.Tamaño(); j++) {
                Usuario u = componente.ObtenerPorIndice(j);
                org.graphstream.graph.Node nodo = miGrafoDeStream.getNode(u.toString());
                if (nodo != null) {
                    nodo.setAttribute("ui.style", "fill-color: " + colorActual + ";");
                }
            }
        }
    }   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
