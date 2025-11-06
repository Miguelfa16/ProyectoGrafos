/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;


/** 
 * Implementacion pila 
 * @author malej
 * @param <T> tipo de dato que hay en la pila 
 */
public class Pila<T> {

    /** 
     * lista que almacena los datos de la pila 
     */
    private Lista<T> listaInterna;

    /** 
     * constructor de la pila 
     */
    public Pila() {
        this.listaInterna = new Lista<>();
    }

   /** 
    * verifica si la pila esta vacia 
    * @return rue si lo esta, false si no 
    */
    public boolean isEmpty() {
        return listaInterna.esVacio();
    }

    /** 
     * devuelve el tamaño de la pila 
     * @return tmaño de la pila
     */
    public int size() {
        return listaInterna.Tamaño();
    }

    /** 
     * Agrega un elemento a la cima de la pila 
     * @param elemento elemento a agregar
     */
    public void Agregar(T elemento) { 
        listaInterna.AgregarAlInicio(elemento);
    }

    /** 
     * Obtiene el elemtno de la cima sin eliminarlo 
     * @return Elemento de la cima o null si esta vacia 
     */
    public T ObtenerCima() {
        if (isEmpty()) {
            return null;
        }      
        return listaInterna.ObtenerPorIndice(0);
    }
    /**
     * Remueve y devuelve el elemento de la cima de la pila 
     * @return el elemento de la cima de la pila, null si esta vacia  
     */
    public T Eliminar() {
        if (isEmpty()) {
            return null;
        }     
        return listaInterna.RemoverDelInicio();
    }
}