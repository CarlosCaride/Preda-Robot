/*
 * Archivo: Circuito.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import robot.grafos.Grafo;
import robot.grafos.voraces.Dijkstra;

/**
 * Representa el circuito que se tiene que resolver
 */
public class Circuito {

    private int numeroFilas;
    private int numeroColumnas;
    private int posicionRobot;
    private int posicionRobotColumna;
    private int posicionRobotFila;
    private int posicionSalida;
    private int posicionSalidaColumna;
    private int posicionSalidaFila;
    private boolean traza;
    private String ficheroEntrada;
    private String ficheroSalida;
    private Casilla[][] circuito;
    private Grafo grafo;

    /**
     * Constructor genérico
     */
    public Circuito() {
        this(false, null, null);
    }

    /**
     * Constructor de circuito con ficheros de entrada y salida
     * @param ficheroEntrada Fichero que tiene descrito el circuito
     * @param ficheroSalida  Fichero donde se almacenará la solución
     */
    public Circuito(String ficheroEntrada, String ficheroSalida) {
        this(false, ficheroEntrada, ficheroSalida);
    }

    /**
     * Constructor de circuito con ficheros de entrada y salida con traza
     * @param traza Verdadero si se desea imprimir la traza
     * @param ficheroEntrada Fichero que tiene descrito el circuito
     * @param ficheroSalida  Fichero donde se almacenará la solución
     */
    public Circuito(boolean traza, String ficheroEntrada,
            String ficheroSalida) {
        this.traza = traza;
        this.ficheroEntrada = ficheroEntrada;
        this.ficheroSalida = ficheroSalida;
        posicionRobot = -1;
        posicionRobotFila = -1;
        posicionRobotColumna = -1;
        posicionSalida = -1;
        posicionSalidaColumna = -1;
        posicionSalidaFila = -1;
    }

    /**
     * Carga los datos correspondientes del circuito contenidos en el fichero
     * @return Verdadero en caso de que esté todo correcto
     */
    public boolean cargarDatos() {
        // Comprobamos que se indicara el nombre del fichero
        if (ficheroEntrada.isEmpty()) {
            System.out.println("ERROR: No se indicó fichero de entrada");
            return false;
        }
        try {
            FileReader f = new FileReader(ficheroEntrada);
            BufferedReader b = new BufferedReader(f);
            String cadena;
            // Leemos la definición del cirucito (num. filas y columnas)
            cadena = b.readLine();
            numeroFilas = Integer.parseInt(cadena);
            cadena = b.readLine();
            numeroColumnas = Integer.parseInt(cadena);
            circuito = new Casilla[numeroFilas][numeroColumnas];

            // Comenzamos la lectura del fichero
            for (int i = 0; i < numeroFilas; i++) {
                for (int j = 0; j < numeroColumnas; j++) {
                    cadena = b.readLine();

                    if (cadena.equalsIgnoreCase("R")) {
                        if (posicionRobotFila != -1) {
                            System.out.println("ERROR: Se encontró más de una "
                                    + "posición inicial del robot");
                            b.close();
                            return false;
                        }
                        posicionRobotFila = i;
                        posicionRobotColumna = j;
                        circuito[i][j] = new Casilla(TipoCasilla.Robot, 0);
                    } else if (cadena.equalsIgnoreCase("O")) {
                        circuito[i][j] = new Casilla(TipoCasilla.Obstaculo, 
                                Casilla.COSTE_INFINITO);
                    } else if (cadena.equalsIgnoreCase("S")) {
                        if (posicionSalidaFila != -1) {
                            System.out.println("ERROR: Se encontró más de una "
                                    + "salida");
                            b.close();
                            return false;
                        }

                        if (i != 0
                                && i != (numeroFilas - 1)
                                && j != 0
                                && j != (numeroColumnas - 1)) {
                            System.out.println("ERROR: La salida no se "
                                    + "encuentra en una casilla periférica");
                            b.close();
                            return false;
                        }
                        posicionSalidaFila = i;
                        posicionSalidaColumna = j;

                        circuito[i][j] = new Casilla(TipoCasilla.Salida, 0);
                    } else {
                        circuito[i][j] = new Casilla(TipoCasilla.Franqueable,
                                Integer.parseInt(cadena));
                    }
                }
            }
            b.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: No se encontró el fichero de entrada");
            return false;
        } catch (IOException | NumberFormatException ex) {
            System.out.println("ERROR: El formato del fichero es erróneo");
            return false;
        }
        
        // Comprobamos que haya un robot en el circuito y calculamos su nodo
        if (posicionRobotFila == -1) {
            System.out.println("ERROR: No se encontró posición inicial del"
                    + " robot");
            return false;
        } else {
            posicionRobot = posicionRobotFila * numeroFilas + 
                    posicionRobotColumna;
        }
        
        // Comprobamos que haya una salida en el circuito y calculamos su nodo
        if (posicionSalidaFila == -1) {
            System.out.println("ERROR: No se encontró ninguna salida");
            return false;
        } else {
            posicionSalida = posicionSalidaFila * numeroFilas + 
                    posicionSalidaColumna;
        }

        // Creamos el grafo del circuito
        crearGrafo();

        return true;
    }

    /**
     * Crea el grafo correspondiente al circuito leído
     */
    private void crearGrafo() {
        grafo = new Grafo(numeroColumnas * numeroFilas);

        // Se calcula el valor de cada casilla, así como los costes de las
        // casillas adyacentes.
        for (int i = 0; i < numeroFilas; i++) {
            for (int j = 0; j < numeroColumnas; j++) {
                if (circuito[i][j].getTipo() == TipoCasilla.Obstaculo) {
                    continue;
                }

                int verticeActual = i * numeroFilas + j;
                int valor = circuito[i][j].getValorCasilla();

                if (i != 0) {
                    if (j != 0) {
                        if (circuito[i - 1][j - 1].getTipo() != 
                                TipoCasilla.Obstaculo) {
                            grafo.aniadirArista(verticeActual - numeroColumnas 
                                    - 1, verticeActual, valor);
                        }
                    }
                    if (circuito[i - 1][j].getTipo() != TipoCasilla.Obstaculo) {
                        grafo.aniadirArista(verticeActual - numeroColumnas,
                                verticeActual, valor);
                    }
                    if (j != (numeroColumnas - 1)) {
                        if (circuito[i - 1][j + 1].getTipo() != 
                                TipoCasilla.Obstaculo) {
                            grafo.aniadirArista(verticeActual - numeroColumnas 
                                    + 1, verticeActual, valor);
                        }
                    }
                }

                if (j != 0) {
                    if (circuito[i][j - 1].getTipo() != TipoCasilla.Obstaculo) {
                        grafo.aniadirArista(verticeActual - 1, verticeActual,
                                valor);
                    }
                }
                if (j != (numeroColumnas - 1)) {
                    if (circuito[i][j + 1].getTipo() != TipoCasilla.Obstaculo) {
                        grafo.aniadirArista(verticeActual + 1, verticeActual,
                                valor);
                    }
                }

                if (i < (numeroFilas - 1)) {
                    if (j != 0) {
                        if (circuito[i + 1][j - 1].getTipo() != 
                                TipoCasilla.Obstaculo) {
                            grafo.aniadirArista(verticeActual + numeroColumnas
                                    - 1, verticeActual, valor);
                        }
                    }
                    if (circuito[i + 1][j].getTipo() != TipoCasilla.Obstaculo) {
                        grafo.aniadirArista(verticeActual + numeroColumnas, 
                                verticeActual, valor);
                    }
                    if (j != (numeroColumnas - 1)) {
                        if (circuito[i + 1][j + 1].getTipo() != 
                                TipoCasilla.Obstaculo) {
                            grafo.aniadirArista(verticeActual + numeroColumnas
                                    + 1, verticeActual, valor);
                        }
                    }
                }
            }
        }
    }

    /**
     * Resuelve el circuito mediante el algoritmo de Dijkstra
     */
    public void resolver() throws FileNotFoundException, IOException {
        Dijkstra d;
        PrintStream ps;
        if (ficheroSalida.isEmpty()) {
            ps = System.out;
        } else {
            File f = new File(ficheroSalida);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            ps = new PrintStream(f);
        }
        if (!traza) {
            d = new Dijkstra(grafo, posicionRobot, posicionSalida);
        } else {
            d = new Dijkstra(grafo, posicionRobot, posicionSalida, ps);
            ps.println();
        }
        imprimirSolucion(d, ps);
    }

    /**
     * Imprime la solución que se ha obtenido
     */
    private void imprimirSolucion(Dijkstra d, PrintStream ps) {
        int[] predecesor = d.getPredecesor();
        int coste = d.getEspecial()[posicionSalida];
        int posicion = posicionSalida;
        ArrayList<Integer> nodos = new ArrayList<>();
        
        nodos.add(posicion);
        
        while(!nodos.contains(posicionRobot)) {
            nodos.add(predecesor[posicion]);
            posicion = predecesor[posicion];
        }
        
        ps.print("R");
        for (int i = nodos.size() - 1; i >= 0; i--) {
            if (i== 0) {
                ps.print("S");
            }
            posicion = nodos.get(i);
            ps.print("[");
            ps.print((posicion / numeroColumnas) + 1);
            ps.print(",");
            ps.print((posicion % numeroColumnas) + 1);
            ps.print("]");
            if (i != 0) {
                ps.print(",");
            } else {
                ps.println();
            }
        }
        ps.print("Energía total consumida: ");
        ps.println(coste);
    }
}
