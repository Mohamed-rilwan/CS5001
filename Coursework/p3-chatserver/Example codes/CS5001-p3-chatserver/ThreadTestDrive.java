public class ThreadTestDrive {
    public static void main(String[] args) {

        Runnable threadJob = new MyRunnable();
        Thread thread = new Thread(threadJob);

        thread.start();
        System.out.println("Main method being executed");

        
    }
}
