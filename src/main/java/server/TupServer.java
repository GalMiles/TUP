package server;


import engine.Engine;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TupServer {
    private UsersManager usersManager;
    private Engine engine = new Engine(); // need the engine for the Thread - with it we can run the functions

    public TupServer() {
        //usersManager = new UsersManager(engine);
    }


    void startServer(int port){

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Starting server on port " + port);

            while(true){
                Socket socket = serverSocket.accept(); //waiting for client
                new TupServerThread(this, socket).start(); //create thread so the client and server communicate
                }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Engine getEngine() {
        return engine;
    }






}
