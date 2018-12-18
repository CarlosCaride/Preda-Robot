/*
 * Archivo: Robot.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal de la aplicación
 */
public class Robot {

    /**
     * Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        
        boolean traza = false;
        String ficheroIn;
        String ficheroOut = "";
        int desfaseArgumentos = 0;
        
        if (args.length == 0) {
            System.out.println("ERROR: Sintaxis incorrecta");
            ImprimirAyuda();
            return;
        }
        if (args[0].equalsIgnoreCase("-h")){
            ImprimirAyuda();
            return;
        }
        
        if (args[0].equalsIgnoreCase("-t")){
            traza = true;
            desfaseArgumentos++;
        }
        
        if (args.length <= desfaseArgumentos) {
            System.out.println("ERROR: Pocos argumentos");
            ImprimirAyuda();
            return;
        }
        
        ficheroIn = args[desfaseArgumentos++];
        
        if (args.length > desfaseArgumentos) {
            ficheroOut = args[desfaseArgumentos];
        }
        
        Circuito c = new Circuito(traza, ficheroIn, ficheroOut);
        if (!c.cargarDatos()) {
            return;
        }
        
        try {
            c.resolver();
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Imprime la ayuda que se le mostrará al usuario
     */
    private static void ImprimirAyuda() {
        System.out.println("SINTAXIS:");
        System.out.println("robot [-t][-h][fichero_entrada] [fichero_salida]");
        System.out.println("-t               Traza la selección de clientes");
        System.out.println("-h               Muestra esta ayuda");
        System.out.println("fichero_entrada  Nombre del fichero de entrada");
        System.out.println("fichero_salida   Nombre del fichero de salida");
    }
    
}
