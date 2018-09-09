package edu.escuelaing.arem.httpServer;

import java.net.*;
import java.io.*;

/**
 * @author Carlos Andrés Castaneda Lozano
 *
 */
public class HttpServer {

    /**
     * Metodo main, metodo principal de la clase HttpServer.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
      
        serverSocket = new ServerSocket(35000);
        
        Socket clientSocket = null;
        while (true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            Despliegue despliegue = new Despliegue(clientSocket);
            despliegue.start();
        }

    }
}
