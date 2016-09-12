import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class cc_msc00j {
  public static void main(String[] args) throws IOException {
    SSLServerSocket sslServerSocket = null;
    try {
      SSLServerSocketFactory sslServerSocketFactory =
          (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      sslServerSocket = (SSLServerSocket) sslServerSocketFactory.
                        createServerSocket(9999);
      SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
      PrintWriter out = new PrintWriter(sslSocket.getOutputStream(),true);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(sslSocket.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        System.out.println(inputLine);
        out.println(inputLine);
      }
    } finally {
      if (sslServerSocket != null) {
        try {
          sslServerSocket.close();
        } catch (IOException x) {
          // Handle error
        }
      }
    }
  }
}
 
class EchoClient {
  public static void main(String[] args) throws IOException {
    SSLSocket sslSocket = null;
    try {
      SSLSocketFactory sslSocketFactory =
          (SSLSocketFactory) SSLSocketFactory.getDefault();
      sslSocket =
          (SSLSocket) sslSocketFactory.createSocket("localhost", 9999);
      PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(sslSocket.getInputStream()));
      BufferedReader stdIn = new BufferedReader(
          new InputStreamReader(System.in));
      String userInput;
      while ((userInput = stdIn.readLine()) != null) {
        out.println(userInput);
        System.out.println(in.readLine());
      }
    } finally {
      if (sslSocket != null) {
        try {
          sslSocket.close();
        } catch (IOException x) {
          // Handle error
        }
      }
    }
  }
}

