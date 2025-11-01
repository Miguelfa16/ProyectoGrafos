package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList; // 🚨 ¡Necesitas esta importación! 🚨
import java.util.List;


/**
 *
 * @author samir
 */public class Usuario {
    private final String nombre;
    
    

    // Constructor
    public Usuario(String nombre) {
        this.nombre = nombre;
        
    
    }


//funciones para comparar dos objetos usuario 
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Usuario usuario = (Usuario) o;
        return nombre.equals(usuario.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
    
   //revisar si se utiliza finalmente!!
    @Override
    public String toString() {
        return nombre;
    }
    
   
}
