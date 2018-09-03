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
import java.nio.file.Files;

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
        byte[] bytesSource = null;
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
                } else if (inputLine.contains(".png")) {
                    bytesSource = Files.readAllBytes(new File("./" + inputLine).toPath());
                    resultado = "" + bytesSource.length;

                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: image/jpg\r\n"
                            + "\r\n"
                            + resultado;
                    out.println(outputLine);

                    byte[] hByte = outputLine.getBytes();
                    byte[] rta = new byte[bytesSource.length + hByte.length];
                    for (int i = 0; i < hByte.length; i++) {
                        rta[i] = hByte[i];
                    }
                    for (int i = hByte.length; i < hByte.length + bytesSource.length; i++) {
                        rta[i] = bytesSource[i - hByte.length];
                    }
                }

            } catch (java.lang.ArrayIndexOutOfBoundsException e) {

            }

        }
    }
}
