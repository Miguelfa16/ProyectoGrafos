/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafoproyecto;

/**
 * Implementacion de hashmap 
 * @author Samir Nassar, Miguel Figueroa 
 * @param <X> representa clave 
 * @param <Y> represetna valor 
 * 
 */
public class Mapa<X,Y>{
    /** 
     * capacidad incial de array 
     */
    private static final int CAPACIDAD_INICIAL = 16;
    /** 
     * array donde se almacenan entradas 
     */
    private Lista<Entrada<X, Y>>[] tabla;
    /** 
     * cantidad de conjuntos de clave y valor 
     */
    private int iN;
    
        /** 
         * represetnacion de conjunto clave-valor
         * @param <X> clave 
         * @param <Y> valor 
         */
    private static class Entrada<X, Y> {
        X clave;
        Y valor;
        /** 
         * constructor del conjunto
         * @param clave clave 
         * @param valor valor 
         */
        Entrada(X clave, Y valor) {
            this.clave = clave;
            this.valor = valor;
        }
        
        /** 
         * compara claves con otro objeto
         * @param obj objeto a comparar
         * @return true si es igual , false si no 
         */
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
        /**
         * genera hascode solo segun clave
         * @return 
         */
        @Override
        public int hashCode() {
            return clave.hashCode();
        }
    }
    /** 
     * constructor del mapa 
     */
    @SuppressWarnings("unchecked")
    public Mapa() {
        this.tabla = new Lista[CAPACIDAD_INICIAL];
        for (int i = 0; i < CAPACIDAD_INICIAL; i++) {
            this.tabla[i] = new Lista<>();
        }
        this.iN = 0;
    }
    /** 
     * calcula el indice de una clave dada 
     * @param clave clave 
     * @return el indice de dicha clave en el array
     */
    private int getIndice(X clave) {
        return Math.abs(clave.hashCode() % CAPACIDAD_INICIAL);
    }
    /** 
     * Inserta un nuevo conjunto clave y valor 
     * si ya existe la clave actualiza el valor 
     * @param clave clave 
     * @param valor el nuevo valor 
     */
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
    /** 
     * obtiene el valor de una clave 
     * @param clave clave a buscar
     * @return  el valor asociado a la clave
     */
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
    /** 
     * verifica si existe una clave especifica 
     * @param clave la clave a verificar
     * @return true si existe, false si no 
     */
    public boolean containsKey(X clave) {
        return get(clave) != null;
    }
    /** 
     * Elimina un conjunto clave valor segun la clave 
     * @param clave La clave de lo que se quiere eliminar 
     */
    public void remove(X clave) {
        int indice = getIndice(clave);
        Lista<Entrada<X, Y>> lista = tabla[indice];
        Entrada<X, Y> entradaARemover = new Entrada<>(clave, null); 
        lista.Remover(entradaARemover);
    }
    /**
     * devuelve lista de todas las claves
     * @return lista con todas las claves 
     */
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
    /** 
     * Devuelve lista de todos los valores 
     * @return lista con todos los valores  
     */
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
