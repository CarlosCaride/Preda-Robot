/*
 * Archivo: Dijkstra.java
 * Autor: Carlos Caride Santeiro
 * DNI: 44446239G
 * Email: ccaride5@alumno.uned.es
 * Fecha: 01/12/2017
 * Asignatura: Programación y Estructuras de Datos Avanzadas.
 * Trabajo: Práctica 1 (2017-2018)
 */

package robot.grafos.voraces;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import robot.Casilla;
import robot.grafos.Grafo;

/**
 * Implementación del algoritmo de Dijkstra de caminos mínimos
 */
public class Dijkstra {
    int especial[], predecesor[];
    
    /**
     * Resuelve el camino mínimo del grafo indicado
     * @param g El grafo a resolver
     * @param inicio Nodo de inicio
     * @param fin Nodo final
     */
    public Dijkstra(Grafo g, int inicio, int fin) {
        this(g, inicio, fin, null);
    }
    
    /**
     * Resuelve el camino mínimo del grafo indicado trazando la solución
     * @param g El grafo a resolver
     * @param inicio Nodo de inicio
     * @param fin Nodo final
     * @param ps Stream de salida
     */
    public Dijkstra(Grafo g, int inicio, int fin, PrintStream ps) {
        int paso = 0;
        ArrayList<Integer> C = new ArrayList<>();
        especial = new int[g.getNumeroNodos()];
        predecesor = new int[g.getNumeroNodos()];
        Integer v, w, distancia;
        
        if (ps != null) {
            ps.println("Paso\tv\tC\tespecial\tpredecesor");
        }
        
        for (int i = 0; i < g.getNumeroNodos(); i++) {
            if (i == inicio) {
                especial[i] = Casilla.COSTE_INFINITO;
                continue;
            }
            C.add(i);
            
            if (g.esAdyacente(inicio, i)) {
                especial[i] = g.peso(inicio, i);
            } else {
                especial[i] = Casilla.COSTE_INFINITO;
            }
            predecesor[i] = inicio;
        }
        
        if (ps != null) {
            ps.print("Ini.\t\t");
            ps.print(C.get(0) + 1);
            for (int i = 1; i < C.size(); i++) {
                ps.print(",");
                ps.print(C.get(i) + 1);
            }
            ps.print("\t");
            imprimirEspecial(ps, inicio);
            ps.print("\t");
            imprimirPredecesor(ps, inicio);
            ps.println();
        }
        
        while (C.contains(fin)) {
            paso++;
            v = minimoNodo(C);
            C.remove(Integer.valueOf(v));
            if (v == -1) {
                throw new Error("El circuito no se puede resolver");
            }
            if (v != fin) {
                for (Iterator<Integer> iter = C.iterator(); iter.hasNext();) {
                    w = iter.next();
                    
                    if (g.esAdyacente(v, w)) {
                        distancia = g.peso(v, w);
                    } else {
                        distancia = Casilla.COSTE_INFINITO;
                    }
                    
                    if(especial[w] > (especial[v] + distancia)) {
                        especial[w] = especial[v] + distancia;
                        predecesor[w] = v;
                    }
                }
            }
            if (ps != null) {
                ps.print(paso);
                ps.print("\t");
                ps.print(v + 1);
                ps.print("\tC \\{");
                ps.print(v + 1);
                ps.print("}\t");
                imprimirEspecial(ps, inicio);
                ps.print("\t");
                imprimirPredecesor(ps, inicio);
                ps.println();
            }
        }
    }

    /**
     * Calcula el nodo que minimiza especial
     * @param C Lista de nodos a comprobar
     * @return El nodo mínimo
     */
    private int minimoNodo(ArrayList<Integer> C) {
        int minimo = Casilla.COSTE_INFINITO - 1;
        int vertice = -1;
        for (Iterator<Integer> iter = C.iterator(); iter.hasNext();) {
            int i = iter.next();
            if (minimo > especial[i]) {
                minimo = especial[i];
                vertice = i;
            }
        }       
        return vertice;
    }
    
    /**
     * Devuelve una matriz de los valores especial
     * @return Matriz de los valores especial
     */
    public int[] getEspecial() {
        return especial.clone();
    }
    
    /**
     * Devuelve una matriz de los nodos predecesores
     * @return Matriz de los nodos predecesores
     */
    public int[] getPredecesor() {
        return predecesor.clone();
    }
    
    /**
     * Imprime la matriz especial
     * @param ps Stream de salida
     * @param inicio Nodo de inicio del algoritmo
     */
    private void imprimirEspecial(PrintStream ps, int inicio) {
        for (int i = 0; i < especial.length; i++) {
            ps.print(" ");
            if (i != inicio) {
                if (especial[i] == Casilla.COSTE_INFINITO) {
                    ps.print("i");
                } else {
                    ps.print(especial[i]);
                }
            } else {
                ps.print("-");
            }
        }
    }

    /**
     * Imprime la matriz predecesor
     * @param ps Stream de salida
     * @param inicio Nodo de inicio del algoritmo
     */
    private void imprimirPredecesor(PrintStream ps, int inicio) {
        for (int i = 0; i < predecesor.length; i++) {
            ps.print(" ");
            if (i != inicio) {
                ps.print(predecesor[i] + 1);
            } else {
                ps.print("-");
            }
        }
    }
}
