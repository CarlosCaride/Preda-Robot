/*
 * Archivo: Casilla.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot;

/**
 * Representa una casilla del circuito
 */
public class Casilla {
    /**
     * Valor infinito
     */
    public static final int COSTE_INFINITO = 99999;
    
    private TipoCasilla tipo;
    private int valorCasilla;

    /**
     * Retorna el tipo de casilla
     * @return el tipo de casilla
     */
    public TipoCasilla getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de casilla
     * @param tipo el tipo a establecer
     */
    public void setTipo(TipoCasilla tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna el valor de la casilla
     * @return el valor de la casilla
     */
    public int getValorCasilla() {
        return valorCasilla;
    }

    /**
     * Establece el valor de la casilla
     * @param valorCasilla el valor de la casilla
     */
    public void setValorCasilla(int valorCasilla) {
        this.valorCasilla = valorCasilla;
    }
    
    /**
     * Constructor genérico
     */
    public Casilla(){
        this(TipoCasilla.NoAccesible, COSTE_INFINITO);
    }
    
    /**
     * Constructor de una nueva casilla indicando el tipo y coste
     * @param tipo el tipo de casilla
     * @param coste el coste de atravesar la casilla
     */
    public Casilla(TipoCasilla tipo, int coste){
        this.tipo = tipo;
        valorCasilla = coste;
    }
}
