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
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
            Despliegue despliegue = new Despliegue(clientSocket);
            despliegue.proceso(clientSocket);
        }

    }
}
