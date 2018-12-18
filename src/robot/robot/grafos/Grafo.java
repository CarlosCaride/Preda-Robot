/*
 * Archivo: Grafo.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot.grafos;

import java.util.*;

/**
 * Representa un grafo
 */
public class Grafo {

    int numeroNodos;
    Arista[][] matrizAdyacencia;

    /**
     * Constructor genérico
     */
    public Grafo() {

    }

    /**
     * Constructor de un nuevo grafo con en número de nodos indicado
     * @param nNodos Número de nodos del grafo
     */
    public Grafo(int nNodos) {
        numeroNodos = nNodos;
        matrizAdyacencia = new Arista[nNodos][nNodos];
    }

    /**
     * Añade una nueva arista al grafo
     * @param nInicio Nodo de inicio
     * @param nDestino Nodo de destino
     * @param peso Peso de la arista
     */
    public void aniadirArista(int nInicio, int nDestino, int peso) {
        if (nInicio >= numeroNodos || nDestino >= numeroNodos) {
            throw new IndexOutOfBoundsException("Nodo de inicio/destino"
                    + " no existe");
        }
        matrizAdyacencia[nInicio][nDestino] = new Arista(peso);
    }

    /**
     * Borra la arista indicada
     * @param nInicio Nodo de inicio de la arista
     * @param nDestino Nodo de destino de la arista
     */
    public void borrarArista(int nInicio, int nDestino) {
        if (nInicio >= numeroNodos || nDestino >= numeroNodos) {
            throw new IndexOutOfBoundsException("Nodo de inicio/destino"
                    + " no existe");
        }
        matrizAdyacencia[nInicio][nDestino] = null;
    }

    /**
     * Indica si dos nodos son adyacentes
     * @param n1 Primer nodo
     * @param n2 Segundo nodo
     * @return Devuelve verdadero en caso de ser adyacentes
     */
    public boolean esAdyacente(int n1, int n2) {
        if (n1 >= numeroNodos || n2 >= numeroNodos) {
            throw new IndexOutOfBoundsException("Nodo de inicio/destino"
                    + " no existe");
        }
        return !(matrizAdyacencia[n1][n2] == null &&
                matrizAdyacencia[n2][n1] == null);
    }

    /**
     * Devuelve una lista con los nodos adyacentes a uno dado
     * @param nodo Nodo del que se quieren obtener los adyacentes
     * @return Lista con los 
     */
    public ArrayList<Integer> adyacentes(int nodo) {
        if (nodo >= numeroNodos) {
            throw new IndexOutOfBoundsException("Nodo de inicio/destino"
                    + " no existe");
        }
        ArrayList<Integer> lista = new ArrayList<Integer>();
        for (int i = 0; i < numeroNodos; i++) {
            if (matrizAdyacencia[nodo][i] != null) {
                lista.add(i);
            }
        }
        return lista;
    }

    /**
     * Devuelve el peso de la arista de dos nodos adyacentes
     * @param nInicio Nodo inicial
     * @param nDestino Nodo destino
     * @return El peso de la arista
     */
    public int peso(int nInicio, int nDestino) {
        if (nInicio >= numeroNodos || nDestino >= numeroNodos) {
            throw new IndexOutOfBoundsException("Nodo de inicio/destino"
                    + " no existe");
        }
        if (matrizAdyacencia[nInicio][nDestino] != null) {
            return matrizAdyacencia[nInicio][nDestino].getPeso();
        } else {
            throw new NoSuchElementException("No existe adyacente.");
        }
    }

    /**
     * Imprime la matriz de adyacencia del grafo
     */
    public void imprimir() {
        for (int i = 0; i < numeroNodos; i++) {
            if (i < 10) {
                System.out.print(" ");
            }
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < numeroNodos; i++) {
            for (int j = 0; j < numeroNodos; j++) {
                if (matrizAdyacencia[i][j] == null) {
                    System.out.print(" \u221E ");
                    continue;
                }
                System.out.print(" ");
                System.out.print(matrizAdyacencia[i][j].getPeso());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Obtiene el número de nodos del grafo
     * @return Número de nodos
     */
    public int getNumeroNodos() {
        return this.numeroNodos;
    }
}
