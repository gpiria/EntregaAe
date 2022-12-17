Instrucciones
==========================
En un nuevo workspace de Eclipse, importar el proyecto como un proyecto Maven.

Se debe agregar en le classpath de los archivos a ejecutar el jar: json-simple-1.1.1.jar para poder leer los archivos json que contienen las instancias.
El Runner de NSGA se encuentra en jmetal-example, en el package org.uma.jmetal.example.multiobjective.nsgaii, con el nombre AeNSGAIIRunner.java. El primer argumento es la instancia que se tomará, el segundo el frente de Pareto con el que se comparará. Puede ejecutarse sin argumentos, tomando los defaults.

En jmetal-problem, en el package AeProblem, se encuentran la implementación de la clase problema (AeProblem.java), la implementación del algoritmo Greedy (Greedy.java) y la implementación del cálculo de métricas dados dos csv, usado para el caso del algoritmo Greedy (GreedyMetrics.java).

El algoritmo Greedy toma como argumento la instancia a ser ejecutada.

GreedyMetrics toma como primer argumento el frente de Pareto y como segundo el resultado del algoritmo Greedy.

Archivos:
    Instancias:
        * distancias_mayor_5 (Default) - Instancia utilizada en la configuración paramétrica, tomando los barrios con INSE mayor a 5
        * distancias_menor_5 - Instancia tomando los barrios con INSE menor a 5
        * distancias_combinado - Instancia tomando barrios tanto con INSE mayor a 5 como con INSE menor a 5
        * distancias_rand - Instancia tomando distancias aleatorias entre plazas y estudiantes
    Frentes:
        * pareto_mayor_5 (Default) - Frente aproximado de la instancia distancias_mayor_5
        * pareto_menor_5 - Frente aproximado de la instancia distancias_menor_5
        * pareto_combinado - Frente aproximado de la instancia distancias_combinado
        * pareto_rand - Frente aproximado de la instancia distancias_rand
    Soluciones de Greedy:
        * solución_greedy_mayor_5 - Solución del greedy para la instancia distancias_mayor_5
        * solución_greedy_menor_5 - Solución del greedy para la instancia distancias_menor_5
        * solución_greedy_combinado - Solución del greedy para la instancia distancias_combinado
        * solución_greedy_rand - Solución del greedy para la instancia distancias_rand
        
Nota:
    El trabajo fue realizado en Windows 10, por lo que para que funcione en Linux, puede que haya que cambiar el direccionamiento en el filesystem de folder\file a folder/file en las variables filename de los archivos.
