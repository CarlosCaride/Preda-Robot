# UNED - Ingeniería Informática - Programación y Estructuras Avanzadas

## PED 1 - Robot desplazándose en un circuito

### Introducción
En ciencias de la computación, un **algoritmo voraz** (también conocido como **ávido**, **devorador** o **greedy**) es una estrategia de búsqueda por la cual se sigue una heurística consistente en elegir la opción óptima en cada paso local con la esperanza de llegar a una solución general óptima. Este esquema algorítmico es el que menos dificultades plantea a la hora de diseñar y comprobar su funcionamiento. Normalmente se aplica a los problemas de optimización. [Wikipedia](https://es.wikipedia.org/wiki/Algoritmo_voraz)

Este proyecto es la PED 1 realizada durante el curso 2017-2018 para la asignatura de **Programación y Estructuras Avanzadas**, del grado de **Ingenieria informática**

## ENUNCIADO DE LA PRÁCTICA: Robot desplazándose en un circuito

Sea un robot *R* que dispone de una batería de *N* unidades de energía y se encuentra en un circuito por el que puede desplazarse. El objetivo es que *R* se desplace desde el punto en el que se encuentra hasta el punto de salida *S* del circuito, contando con que en el camino se puede encontrar con obstáculos, *O*, que son infranqueables. El paso por una casilla franqueable supone un gasto de energía igual al valor que indica la casilla, que deberá ser mayor que 0. Se busca un algoritmo que permita al robot llegar al punto *S* gastando el mínimo de energía.

El circuito se puede representar mediante una matriz de dimensiones nxm en la que desde cada elemento se puede acceder a un elemento adyacente con el consumo de energía que indique la casilla. En el apartado 3.8 del texto base se puede ver un ejemplo detallado de un circuito concreto. El esquema que se utilizará para su resolución será el indicado en el texto base para este problema: **esquema voraz**, en particular la solución basada en el algoritmo de Dijkstra.

## Descripción del esquema algorítmico utilizado y como se aplica al problema

### Descripción algoritmo de Dijkstra

Como se indica en el enunciado de la práctica, la solución se basa en el algoritmo de Dijkstra. El algoritmo de Dijkstra, también llamado algoritmo de caminos mínimos, es un algoritmo para la determinación del camino más corto dado un vértice origen al resto de los vértices en un grafo con pesos en cada arista. Su nombre se refiere a *Edsger Dijkstra*, quien lo describió por primera vez en 1959.

La idea subyacente en este algoritmo consiste en ir explorando todos los caminos más cortos que parten del vértice origen y que llevan a todos los demás vértices; cuando se obtiene el camino más corto desde el vértice origen, al resto de vértices que componen el grafo, el algoritmo se detiene. El algoritmo es una especialización de la búsqueda de costo uniforme, y como tal, no funciona en grafos con aristas de coste negativo (al elegir siempre el nodo con distancia menor, pueden quedar excluidos de la búsqueda nodos que en próximas iteraciones bajarían el costo general del camino al pasar por una arista con costo negativo).
