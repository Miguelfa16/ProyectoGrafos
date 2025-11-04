/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;

/**
 *
 * @author malej
 */
public class Mapa<X,Y>{
    private static final int CAPACIDAD_INICIAL = 16;
    private Lista<Entrada<X, Y>>[] tabla;
    private int iN;

    private static class Entrada<X, Y> {
        X clave;
        Y valor;

        Entrada(X clave, Y valor) {
            this.clave = clave;
            this.valor = valor;
        }
        
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj){
                return true;
            }
            if (obj == null){
                return false;
            }
            
            if (!(obj instanceof Entrada)){
                return false;
            } 
            Entrada<?, ?> otra = (Entrada<?, ?>) obj;
            return clave.equals(otra.clave);
        }

        @Override
        public int hashCode() {
            return clave.hashCode();
        }
    }

    @SuppressWarnings("unchecked")
    public Mapa() {
        this.tabla = new Lista[CAPACIDAD_INICIAL];
        for (int i = 0; i < CAPACIDAD_INICIAL; i++) {
            this.tabla[i] = new Lista<>();
        }
        this.iN = 0;
    }

    private int getIndice(X clave) {
        return Math.abs(clave.hashCode() % CAPACIDAD_INICIAL);
    }

    public void put(X clave, Y valor) {
        int indice = getIndice(clave);
        Lista<Entrada<X, Y>> lista = tabla[indice];


        for (int i = 0; i < lista.Tamaño(); i++) {
            Entrada<X, Y> entrada = lista.ObtenerPorIndice(i);
            if (entrada.clave.equals(clave)) {
                entrada.valor = valor;
                return;
            }
        }
        
        lista.Agregar(new Entrada<>(clave, valor));
        iN++;
    }

    public Y get(X clave) {
        int indice = getIndice(clave);
        Lista<Entrada<X, Y>> lista = tabla[indice];
        
        for (int i = 0; i < lista.Tamaño(); i++) {
            Entrada<X, Y> entrada = lista.ObtenerPorIndice(i);
            if (entrada.clave.equals(clave)) {
                return entrada.valor;
            }
        }
        return null;
    }

    public boolean containsKey(X clave) {
        return get(clave) != null;
    }

    public void remove(X clave) {
        int indice = getIndice(clave);
        Lista<Entrada<X, Y>> lista = tabla[indice];
        Entrada<X, Y> entradaARemover = new Entrada<>(clave, null); 
        lista.Remover(entradaARemover);
    }

    public Lista<X> keySet() {
        Lista<X> claves = new Lista<>();
        for (int i = 0; i < CAPACIDAD_INICIAL; i++) {
            // ➡️ CAMBIO: Bucle for-each reemplazado por bucle for-i
            Lista<Entrada<X, Y>> lista = tabla[i];
            for (int j = 0; j < lista.Tamaño(); j++) {
                claves.Agregar(lista.ObtenerPorIndice(j).clave);
            }
        }
        return claves;
    }
    
    public Lista<Y> values() {
        Lista<Y> valores = new Lista<>();
        for (int i = 0; i < CAPACIDAD_INICIAL; i++) {

            Lista<Entrada<X, Y>> lista = tabla[i];
            for (int j = 0; j < lista.Tamaño(); j++) {
                valores.Agregar(lista.ObtenerPorIndice(j).valor);
            }
        }
        return valores;
    }
    
    
}
