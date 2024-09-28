public class TimerApp {

    public static void main(String[] args) {
        // Перший потік: відображає час, що минув від запуску програми
        Thread timeThread = new Thread(() -> {
            int seconds = 0;
            while (true) {
                try {
                    // Виводимо кількість секунд, що минули
                    System.out.println("Пройшло " + seconds + " секунд(и).");
                    seconds++;
                    // Засинаємо на 1 секунду
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Другий потік: виводить повідомлення кожні 5 секунд
        Thread fiveSecondsThread = new Thread(() -> {
            while (true) {
                try {
                    // Засинаємо на 5 секунд
                    Thread.sleep(5000);
                    // Виводимо повідомлення кожні 5 секунд
                    System.out.println("Минуло 5 секунд.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Запускаємо обидва потоки
        timeThread.start();
        fiveSecondsThread.start();
    }
}
