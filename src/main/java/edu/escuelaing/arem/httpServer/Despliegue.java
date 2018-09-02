/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arem.httpServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author CARLOS
 */
public class Despliegue {

    public void proceso(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        String resultado;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            try {
                inputLine = inputLine.split(" ")[1];
                if (inputLine.endsWith(".html")) {
                    File pagina = new File("./" + inputLine);
                    resultado = " ";
                    try {
                        FileReader fReader = new FileReader(pagina);
                        BufferedReader bReader = new BufferedReader(fReader);
                        String line;
                        while ((line = bReader.readLine()) != null) {
                            resultado += line;
                        }
                        bReader.close();
                    } catch (IOException ex) {
                        System.err.println("Error en la lectura del Buffer");
                        ex.printStackTrace();
                    }
                    if (!in.ready()) {
                        break;
                    }

                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + resultado;
                    out.println(outputLine);
                }

            } catch (java.lang.ArrayIndexOutOfBoundsException e) {

            }

        }
    }
}
