public class nc_obj09j {
  private final int idNumber;
  private static final Hi r= new Hi();
  private static final int seed = (int) (Math.random() * 100); // Random deposit
 
  public Hi() {
	  idNumber = seed - 10; // Subtract processing fee
  }
 
  public static void main(String[] args) {
	  if (r.getClass().getName().equals(
		      "com.application.auth.DefaultAuthenticationHandler")) {
		   // ...
		}
  }
}
