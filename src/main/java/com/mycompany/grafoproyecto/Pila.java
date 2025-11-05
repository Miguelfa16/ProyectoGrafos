/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;



public class Pila<T> {

    
    private Lista<T> listaInterna;

   
    public Pila() {
        this.listaInterna = new Lista<>();
    }

   
    public boolean isEmpty() {
     
        return listaInterna.esVacio();
    }

    
    public int size() {

        return listaInterna.Tamaño();
    }

    
    public void push(T elemento) {
    
        listaInterna.AgregarAlInicio(elemento);
    }

   
    public T peek() {
        if (isEmpty()) {
            return null;
        }
       
        return listaInterna.ObtenerPorIndice(0);
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
     
        return listaInterna.RemoverDelInicio();
    }
}