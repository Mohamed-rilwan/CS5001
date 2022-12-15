/**
* This is a practical 0 - Hello World Program.
* @author Mohamed Rilwan Shaik Dawood 220032472
*/
public class HelloWorld {
/**
* This is the main methods to used to display hello world.
* @param args This is the input argument passed to the main method.
*/
    public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("Hello World");
         }
      else {
          System.out.println("Hello " + args[0]);
          }
    }
 }
