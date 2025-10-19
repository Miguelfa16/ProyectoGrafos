package com.mycompany.grafoproyecto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author samir
 */
public class Usuario {
    private final String nombre; // Ejemplo: "Samir"

    // Constructor
    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    // Getter
    public String getNombre() {
        return nombre;
    }

    // Sobrescribir equals() y hashCode() es crucial para que las colecciones 
    // de Java (como HashMap y HashSet) puedan identificar correctamente a dos 
    // usuarios con el mismo nombre como el mismo objeto, lo cual es vital para el grafo.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return nombre.equals(usuario.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
    
    // Opcional, pero útil para imprimir la información del usuario
    @Override
    public String toString() {
        return nombre;
    }
    
}
