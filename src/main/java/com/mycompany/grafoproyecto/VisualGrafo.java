 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.grafoproyecto;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import java.awt.BorderLayout;


/**
 *
 * @author samir
 */
public class VisualGrafo extends javax.swing.JFrame {
      
    private Grafo grafo;

    
    public VisualGrafo(Grafo grafo) {
        this.grafo = grafo; 
        initComponents();
        IniciarVisualizacion(); 
    }
    
    // lógica grafico
    private void IniciarVisualizacion() {
    System.setProperty("org.graphstream.ui", "swing");
    Graph Grafo = new SingleGraph("Mi Grafo"); 
    Lista<Usuario> todosLosUsuarios = this.grafo.getTodosLosUsuarios();
    for (int i = 0; i < todosLosUsuarios.Tamaño(); i++) { 
       Usuario u = todosLosUsuarios.ObtenerPorIndice(i);
       String nombreUsuario = u.toString();
       Grafo.addNode(nombreUsuario);
       Grafo.getNode(nombreUsuario).setAttribute("ui.label", nombreUsuario);
    } 
    for (int i = 0; i < todosLosUsuarios.Tamaño(); i++) { 
        Usuario origen = todosLosUsuarios.ObtenerPorIndice(i);
        String nombreOrigen = origen.toString();  
        Lista<Usuario> vecinos = this.grafo.getVecinos(origen);
        for (int j = 0; j < vecinos.Tamaño(); j++) {  
            Usuario destino = vecinos.ObtenerPorIndice(j);
            String nombreDestino = destino.toString();
            String idArista = nombreOrigen + "->" + nombreDestino;
            Grafo.addEdge(idArista, nombreOrigen, nombreDestino, true);                
        }
    }    
    SwingViewer viewer = new SwingViewer(Grafo, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
    viewer.enableAutoLayout();
    org.graphstream.ui.swing_viewer.ViewPanel viewPanel = (org.graphstream.ui.swing_viewer.ViewPanel) viewer.addDefaultView(false);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(viewPanel, BorderLayout.CENTER); 
    this.setTitle("Visualizacion Grafo");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
    this.pack();
    this.setLocationRelativeTo(null);
   
    
    }

    

    /**
    
    
     */
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
