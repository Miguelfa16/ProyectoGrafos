/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;

/**
 *
 * @author malej
 */
public class Lista<T> {
    private Nodo<T> pFirst;
    private Nodo<T> pLast;
    private int iN;

    private static class Nodo<T> {
        T data;
        Nodo<T> pNext;

        Nodo(T dato) {
            this.data = dato;
            this.pNext = null;
        }
    }

    public Lista() {
        this.pFirst = null;
        this.pLast = null;
        this.iN = 0;
    }
    
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
    
    public void Remover(T dato) {
        if (esVacio()){
            return;
        }

        if (pFirst.data.equals(dato)) {
            pFirst = pFirst.pNext;
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

    public boolean esVacio() {
        return iN == 0;
    }
    
    public int Tamaño() {
        return iN;
    }
    
    public T ObtenerPorIndice(int indice) {
        if (indice < 0 || indice >= iN) {
           return null;
        }
        
        Nodo<T> actual = pFirst;
        for (int i = 0; i < indice; i++) {
            actual = actual.pNext;
        }
        return actual.data;
    }
    
}
