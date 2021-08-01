package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TupServerMain {
    public static void main(String[] args) {
        int port = 1989;
        new TupServer().startServer(port);
    }

//    public static void main(String[] args) throws Exception {
//        int port = 2323; //telnet protocol port
//        ServerSocket serverSocket = new ServerSocket(port);
//        System.out.println("Server is listening on port " + port);
//        while (true) {
//            //wait until a client connects to this server at the listening port
//            Socket socket = serverSocket.accept();
//
//            //this code is run only AFTER a client has successfully initialized a connection
//            //open a stream to/from the client and start communicating with it
//
//            //in order to make the server available again, meaning, listen to the port
//            //we do all the communication WITH EACH CLIENT IN A DIFFERENT THREAD
//            new Thread(() -> {
//                try (BufferedReader in =
//                             new BufferedReader(
//                                     new InputStreamReader(
//                                             socket.getInputStream()));
//                     PrintWriter out =
//                             new PrintWriter(
//                                     socket.getOutputStream(),
//                                     true)  ) {
//
//                out.println("HTTP/1.0 200 OK");
//                out.println("");
//                out.println("<html><head><title>Fake Socket Website</title></head><body><h1>this is a very stupid web page!</h1></body></html>");
//                out.println("");
//                    out.println("Welcome to our server!");
//                    String line;
////                    try {
////                        int lineCounter = 0;
////                        while ((line = in.readLine()) != null) {
////                            System.out.println(++lineCounter + " " + line);
////                            out.println("Server Says: No, " + line.toUpperCase());
////                        }
////                        System.out.println("Session has ended");
////                    } catch (IOException e) {
////                        // we handle the exception by ending the thread
////                        // which happens automatically by being thrown
////                        // from the loop
////                    }
//                } catch (Exception exception) {
//                }
//            }).start();
//        }
//    }
}
