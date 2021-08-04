package server;

import engine.Engine;
import netWork.Request;
import netWork.Response;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class TupServerThread extends Thread {
    private Socket socket;       // get and send massage to the user with it(socket)
    private TupServer server;   //reference to the main server
    private Engine engine;
    private int clientId;

    public TupServerThread(TupServer server, Socket socket){
        this.socket = socket;
        this.server = server;
        //this.engine = server.getEngine();
        //this.clientId = clientId;

    }
    @Override
    public void run() {
        Object result;
        Method method;
        Object objectForInvoke = engine;

        System.out.println("\nStart handle request (Thread ID " + Thread.currentThread().getId() + ")");

        //in - get massage from the client
        //out - send massage to the client

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
             
             ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {


            Request request = (Request) in.readObject();
            method = objectForInvoke.getClass().getMethod(request.getMethodName(), request.getTypes()); // the method to run on App
            Response.Status status = Response.Status.OK;
            try {
                result = method.invoke(objectForInvoke, request.getArgs());
            } catch (InvocationTargetException e) {
                result = e.getCause();
                status = Response.Status.FAILED;
            } catch (Exception e) {
                result = e;
                status = Response.Status.FAILED;
            }

            System.out.println("Request method: " + request.getMethodName() + ", Status: " + status +
                    " (Session " + request.getSessionID() + ")");

            out.writeObject(new Response(result, status));
            out.flush();


        } catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();

            System.out.println("Finish handle request (Thread ID " + Thread.currentThread().getId() + ")" + "\n");

        }
    }
}
