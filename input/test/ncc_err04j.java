import java.io.File;
 

class ncc_err04j {
	public static void main(String[] args) {
		
		  try {
		    doSomethingWhichThrowsException();
		    System.out.println("OK");   // incorrect "OK" message is printed
		  } catch (RuntimeException e) {
		    System.out.println("ERROR");  // this message is not shown
		  }
		}

		public static void doSomethingWhichThrowsException() {
			int q=1;
			try {
		    throw new RuntimeException();
		  } finally {
		    for (int i = 0; i < 10; i ++) {
		      //...
		      if (q == i) {
		        break; // ignored
		      }
		    }

		    /* ... */
		    return;      // Noncompliant - prevents the RuntimeException from being propagated
		  }
		}
	}
	
