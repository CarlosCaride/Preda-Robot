/*
 * Archivo: TipoCasilla.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot;

/**
 * Tipo de casillas que pueden existir en el mapa
 */
public enum TipoCasilla {
    /**
     * Casilla no accesible
     */
    NoAccesible,
    /**
     * Posición del robot
     */
    Robot,
    /**
     * Salida del mapa
     */
    Salida,
    /**
     * Obstáculo, casilla no franqueable
     */
    Obstaculo,
    /**
     * Casilla por la cual el robot puede pasar
     */
    Franqueable
}
