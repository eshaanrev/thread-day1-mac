public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                long total = 0;
                for (int number = 1; number <= 1_000_000; number++) {
                    total += number;
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
