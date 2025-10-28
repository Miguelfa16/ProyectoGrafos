/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;


import java.util.List;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class Pila<T> {

    
    
    private final List<T> elementos; 
    
    
    public Pila() {
        this.elementos = new ArrayList<>();
    }
    
    // -----------------------------------------------------------
    
    /**
     * Inserta un elemento en la cima de la pila (LIFO: Last-In, First-Out).
     * @param item Elemento a insertar.
     */
    public void push(T item) {
        this.elementos.add(item);
    }

    /**
     * Elimina y devuelve el elemento en la cima de la pila.
     * @return El elemento que estaba en la cima.
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        // El último elemento añadido está al final de la lista.
        return this.elementos.remove(this.elementos.size() - 1);
    }

    /**
     * Devuelve (sin eliminar) el elemento en la cima de la pila.
     * @return El elemento en la cima.
     
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        // El último elemento añadido está al final de la lista.
        return this.elementos.get(this.elementos.size() - 1);
    }
    
    /**
     * Verifica si la pila está vacía.
     * @return true si la pila no contiene elementos, false en caso contrario.
     */
    public boolean isEmpty() {
        return this.elementos.isEmpty();
    }
    
    /**
     * Devuelve el tamaño actual de la pila.
     * @return El número de elementos en la pila.
     */
    public int size() {
        return this.elementos.size();
    }
}

