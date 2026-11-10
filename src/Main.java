public class Main {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();

        // Each worker sums 1 through 1,000,000 and uses its own result slot.
        Thread[] threads = new Thread[1000];
        long[] totals = new long[1000];
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                long total = 0;
                for (int number = 1; number <= 1_000_000; number++) {
                    total += number;
                }
                totals[index] = total;
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        // Wait until every result is ready before combining them.
        for (Thread thread : threads) {
            thread.join();
        }
        // long avoids integer overflow when adding the sums.
        long grandTotal = 0;
        for (long total : totals) {
            grandTotal += total;
        }
        double seconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
        System.out.println("Total: " + grandTotal);
        System.out.println("Time: " + seconds + " seconds");
    }
}
