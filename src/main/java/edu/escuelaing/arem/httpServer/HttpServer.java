package edu.escuelaing.arem.httpServer;

import java.net.*;
import java.io.*;

/**
 * @author Carlos Andrés Castaneda Lozano
 * 
 * Clase HttpServer crea un servidor web capaz de responder peticiones
 * html y .png
 *
 */
public class HttpServer {

    /**
     * Metodo main, metodo principal de la clase HttpServer.
     * Crea un servidor y un cliente Socket, y asigna un puerto
     * para el servidor web.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Integer port;
        try { 
            port = new Integer(System.getenv("PORT"));
        } catch (Exception e) {
            port = 35000;
        }
        
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        

        Socket clientSocket = null;
        while (true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            Multisolicitud multi = new Multisolicitud(clientSocket);
            multi.start();
        }

    }
}
