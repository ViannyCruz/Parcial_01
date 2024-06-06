package org.example;


import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelMatrixSearch {


    private static final int MATRIX_SIZE = 1000;
    private static final int THREAD_COUNT = 4;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int TARGET = 256; // Número a buscar

    public static void main(String[] args) throws InterruptedException {
        // Inicializar la matriz con valores aleatorios
        //...
        Random rand = new Random();
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = rand.nextInt(TARGET);
            }
        }


        // printMatriz(matrix);

        // Medir el tiempo de ejecución de la búsqueda secuencial
        //...
        long startTime = System.currentTimeMillis();
        boolean secuentialBool = sequentialSearch(matrix);
        long endTime = System.currentTimeMillis();

        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println("Resultado búsqueda secuencial: " + secuentialBool);
        System.out.println("Tiempo búsqueda secuencial: " + (endTime - startTime) + "ms");

        // Medir el tiempo de ejecución de la búsqueda paralela
        //...
        long startTimeParallel = System.currentTimeMillis();
        AtomicBoolean bool = parallelSearch(matrix);
        long endTimeParallel = System.currentTimeMillis();

        System.out.println("Resultado búsqueda paralela: " + bool);
        System.out.println("Tiempo búsqueda paralela: " + (endTimeParallel - startTimeParallel) + "ms");
    }

    private static boolean sequentialSearch(int[][] matrix) {
        //...
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j] == TARGET) {
                    return true;
                }
            }
        }
        return false;
    }

    private static AtomicBoolean parallelSearch(int[][] matriz) throws InterruptedException {
        //...
        // Basic
        int chunks = MATRIX_SIZE  / THREAD_COUNT;
        System.out.println("chunks" + chunks);
        AtomicBoolean bool = new AtomicBoolean(false);

        //System.out.println("Paralelo");
        Thread thread01 = new Thread(() -> {
            for (int i = 0; i < chunks; i++) {
                for (int j = 0; j < chunks; j++) {
                    //System.out.print(matriz[i][j] + " ");

                    if (matriz[i][j] == TARGET) {
                        bool.set(true);
                    }
                }
                System.out.println();
            }
        });


        Thread thread02 = new Thread(() -> {
            for (int i = chunks; i < 2* chunks; i++) {
                for (int j =  chunks; j < 2* chunks; j++) {
                    //System.out.print(matriz[i][j] + " ");

                    if (matriz[i][j] == TARGET) {
                        bool.set(true);
                    }
                }
                System.out.println();
            }
        });

        System.out.println("thread03");
        Thread thread03 = new Thread(() -> {
            for (int i = chunks * 2; i < 3* chunks; i++) {
                for (int j = 0; j < 3* chunks; j++) {
                    //System.out.print(matriz[i][j] + " ");

                    if (matriz[i][j] == TARGET) {
                        bool.set(true);
                    }
                }
                System.out.println();
            }
        });


        System.out.println("thread04");
        Thread thread04 = new Thread(() -> {
            for (int i = chunks * 3; i < 4 * chunks; i++) {
                for (int j = 0; j < 4 * chunks; j++) {
                    //System.out.print(matriz[i][j] + " ");

                    if (matriz[i][j] == TARGET) {
                        bool.set(true);
                    }
                }
                System.out.println();
            }
        });

        thread01.start();
        thread02.start();
        thread03.start();
        thread04.start();

        thread01.join();
        thread02.join();
        thread03.join();
        thread04.join();

        return bool;

    }

    private static void printMatriz(int [][] matrix){
        System.out.println("Matrix: ");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}















