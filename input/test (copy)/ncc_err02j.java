public class DivideException {
  public static void division(int totalSum, int totalNumber)
    throws ArithmeticException, IOException  {
    int average  = totalSum / totalNumber;
    // Additional operations that may throw IOException...
    System.out.println("Average: " + average);
  }
  public static void main(String[] args) {
    try {
      division(200, 5);
      division(200, 0); // Divide by zero
    } catch (Throwable e) {
       e.printStackTrace();
    }
  }
}
