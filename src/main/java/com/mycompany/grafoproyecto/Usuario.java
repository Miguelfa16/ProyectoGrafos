package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Representacion de cada usuario 
 * Esta clase representa los nodos del grafo 
 * @author Samir Nassar, Miguel Figueroa 
 */public class Usuario {

    private final String nombre;
    
        /**
         * Constructor de objeto usuario 
         * @param nombre 
         */
    public Usuario(String nombre) {    
        this.nombre = nombre;
    }
        /**
         * Compara un objeto usuario con otro
         * @param x objeto a comparar
         * @return true si sus nombres son iguales, false si no 
         */
    @Override
    public boolean equals(Object x) {
        if (this == x){
            return true;
        }
        if (x == null || getClass() != x.getClass()){
            return false;
        }
        Usuario usuario = (Usuario) x;
        return nombre.equals(usuario.nombre);
    }
        /** 
         * Genera un hasCode para un usuario 
         * @return hashCode del usuario
         */
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
    
   /** 
    * Devuelve el usuario en string 
    * @return nombre de usuario 
    */
    @Override
    public String toString() {
        return nombre;
    }
    /**
     * Devuelve el nombre de usuario 
     * @return Nombre de usuario 
     */
public String getNombre() {
        return this.nombre;
    }    
   
}
