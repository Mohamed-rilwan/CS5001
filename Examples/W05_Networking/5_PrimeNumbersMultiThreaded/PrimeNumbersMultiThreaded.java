import java.lang.Thread;

public class PrimeNumbersMultiThreaded {

    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        int nrThreads = Integer.parseInt(args[1]);
        
        System.out.println("Finding all primes up to " + max + " with " + nrThreads + " threads...");

        for (int threadNo = 0; threadNo < nrThreads; threadNo++) {
            PrimeThread thread = new PrimeThread(threadNo, nrThreads, max);
            thread.start();
        }
    }
}

class PrimeThread extends Thread {

    int offset;
    int step;
    int max;

    public PrimeThread(int offset, int step, int max) {
        this.offset = offset;
        this.step = step;
        this.max = max;
    }
    
    @Override
    public void run() {
        for (int n = 1 + offset; n <= max; n += step) {
            if (isPrime(n)) {
                //System.out.println("Thread " + offset + ": " + n);
                System.out.println(n);
            }
        }
    }
    
    public static boolean isPrime(int n) {
        int sum = 0;
        for (int divisor = 2; divisor < n; divisor++) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
