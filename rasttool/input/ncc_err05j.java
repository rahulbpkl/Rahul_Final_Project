import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
 

class ncc_err05j {
	 public static void doOperation(String some_file) {
		    // ... Code to check or set character encoding ...
		    try {
		      BufferedReader reader =  new BufferedReader(new FileReader(some_file));
		      try {
		        // Do operations
		      } finally {
		        reader.close();
		        // ... Other cleanup code ...
		      }
		    } catch (IOException x) {
		      // Forward to handler
		    }
		  }
	}
	
