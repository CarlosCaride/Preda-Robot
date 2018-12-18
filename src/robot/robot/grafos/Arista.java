/*
 * Archivo: Arista.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot.grafos;

/**
 * Representa una arista de un grado
 */
public class Arista {
    private int peso;
    
    public Arista() {
        this(0);
    }
    
    /**
     * Constructor genérico
     * @param peso 
     */
    public Arista(int peso) {
        this.peso = peso;
    }

    /**
     * Obtiene el peso de la arista
     * @return el peso de la arista
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Establece el peso de la arista
     * @param peso el peso de la arista
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }
}
