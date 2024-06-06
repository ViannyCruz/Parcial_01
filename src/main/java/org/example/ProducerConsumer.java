package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Random;


public class ProducerConsumer {
    private static final int QUEUE_CAPACITY = 10;
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 2;
    private static final int PRODUCE_COUNT = 100;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        for (int i = 0; i < PRODUCER_COUNT; i++) {
            new Thread(new Producer(queue, PRODUCE_COUNT)).start();
        }


        for (int i = 0; i < CONSUMER_COUNT; i++) {
            new Thread(new Consumer(queue)).start();
        }
    }

    static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final int produceCount;

        Producer(BlockingQueue<Integer> queue, int produceCount) {
            this.queue = queue;
            this.produceCount = produceCount;
        }

        @Override
        public void run() {
            try {
                Random random = new Random();
                for (int i = 0; i < produceCount; i++) {
                    int num = random.nextInt();
                    queue.put(num);
                    System.out.println("Producido: " + num);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;

        Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Integer number = queue.take();
                    System.out.println("Consumido: " + number);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
