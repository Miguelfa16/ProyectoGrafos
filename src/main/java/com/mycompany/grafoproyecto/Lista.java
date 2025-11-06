/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;
/** 
 * Implementacion de lista 
 * @author Samir Nassar, Miguel Figueroa 
 * @param <T> 
 */

public class Lista<T> {
    /** 
     * Primer nodo de la lista
     */
    private Nodo<T> pFirst;
    /** 
     * ultimo nodo de la lista
     */
    private Nodo<T> pLast;
    /** 
     * tamaño de la lista
     */
    private int iN;
        /** 
         * representacion de un nodo en la lista 
         * @param <T> tipo de dato que se almacena  
         */
    private static class Nodo<T> {
        T data;
        Nodo<T> pNext;
            /**
             * constructor del nodo 
             * @param dato el dato que se almacena 
             */
        Nodo(T dato) {
            this.data = dato;
            this.pNext = null;
        }
    }
        /** 
         * constructor de la lista
         */
    public Lista() {
        this.pFirst = null;
        this.pLast = null;
        this.iN = 0;
    }
        /**
         * agrega un elemento al incio de la lista
         * @param dato lo que se agrega 
         */
    public void AgregarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (esVacio()) {
            pFirst = nuevoNodo;
            pLast = nuevoNodo;
        } else {
            nuevoNodo.pNext = pFirst;
            pFirst = nuevoNodo;
        }
        iN++;
    }
        /** 
         * elimina el primer elemento y lo devuelve 
         * @return dato del inicio o null si esta vacia 
         */
    public T RemoverDelInicio() {
        if (esVacio()) {
            return null;  
        }
        T dato = pFirst.data; 
        pFirst = pFirst.pNext; 
        iN--; 
        if (esVacio()) {
            pLast = null; 
        }
        return dato; 
    }
        /** 
         * agrega un elemento al final de la lista 
         * @param dato dato que se quiere agregar 
         */
    public void Agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (esVacio()) {
            pFirst = nuevoNodo;
            pLast = nuevoNodo;
        } else {
            pLast.pNext = nuevoNodo;
            pLast = nuevoNodo;
        }
        iN++;
    }
        /** 
         * vergica si la lista tiene cierto elemento
         * @param dato elemto que quieres buscar 
         * @return true si lo encuentra, false si no 
         */
    public boolean ContieneA(T dato) {
        Nodo<T> actual = pFirst;
        while (actual != null) {
            if (actual.data.equals(dato)) {
                return true;
            }
            actual = actual.pNext;
        }
        return false;
    }
    
        /** 
         * Elimina un elemeto especifico 
         * @param dato elemento a eliminar 
         */
    public void Remover(T dato) {
        if (esVacio()){
            return;
        }      
        if (pFirst.data.equals(dato)) {
            pFirst = pFirst.pNext;
            iN--;
            if (esVacio()) {
                pLast = null;
            }
            return;
        }
        Nodo<T> actual = pFirst;    
        while (actual.pNext != null && !actual.pNext.data.equals(dato)) {
            actual = actual.pNext;
        }    
        if (actual.pNext != null) {
            if (actual.pNext == pLast) { 
                pLast = actual; 
            }
            actual.pNext = actual.pNext.pNext; 
            iN--; 
        }
    }
        /**
         * Verifica si la lista esta vacia 
         * @return treu si esta vacia, false si no 
         */
    public boolean esVacio() {
        return iN == 0;
    }
        /** 
         * devueelve el tamaño da la lista 
         * @return tamaño de la lista en int 
         */
    public int Tamaño() {
        return iN;
    }
        /** 
         * obtienes un elemento segun su posicion en la lista 
         * @param indice posicion dentro de una lista 
         * @return El dato en la poscicion, null si no hay dato en la posicion 
         */
    public T ObtenerPorIndice(int indice) {
        if (indice < 0 || indice >= iN) {
           return null; 
        }      
        Nodo<T> actual = pFirst;
        for (int i = 0; i < indice; i++) {          
            if (actual.pNext == null) {
                return null; 
            }
            actual = actual.pNext;
        }               
        return actual.data;
    }
}