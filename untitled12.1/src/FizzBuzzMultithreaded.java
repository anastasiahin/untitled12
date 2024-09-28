import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzzMultithreaded {
    private final int n;  // максимальне число
    private int current = 1;  // поточне число
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();  // черга для виведення

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public synchronized void fizz() throws InterruptedException {
        while (current <= n) {
            if (current % 3 == 0 && current % 5 != 0) {
                queue.put("fizz");
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void buzz() throws InterruptedException {
        while (current <= n) {
            if (current % 5 == 0 && current % 3 != 0) {
                queue.put("buzz");
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void fizzbuzz() throws InterruptedException {
        while (current <= n) {
            if (current % 3 == 0 && current % 5 == 0) {
                queue.put("fizzbuzz");
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void number() throws InterruptedException {
        while (current <= n) {
            if (current % 3 != 0 && current % 5 != 0) {
                queue.put(String.valueOf(current));
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    // Потік, який виводить всі елементи з черги
    public void printQueue() throws InterruptedException {
        while (current <= n || !queue.isEmpty()) {
            String value = queue.take();
            System.out.print(value + " ");
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        // Створення потоків
        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread printThread = new Thread(() -> {
            try {
                fizzBuzz.printQueue();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Запуск потоків
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        printThread.start();

        // Очікування завершення потоків
        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
            printThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
