public class PrimeNumbers {

    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);

        System.out.println("Finding all primes up to " + max + "...");
        
        for (int n = 1; n <= max; n++) {
            if (isPrime(n)) {
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
