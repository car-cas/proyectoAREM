package edu.escuelaing.arem.httpServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Andres Castaneda Lozano
 */
public class Despliegue {
    
    /**
     * Metodo proceso, Permite desplegar el servidor web con un cliente socket,
     * ademas busca el archivo .html y el .png.
     * @param clientSocket
     * @throws IOException 
     */
    
    public void proceso(Socket clientSocket) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = in.readLine();
            String outputLine,formato,resultado;
            byte[] bytes = null;
            if (inputLine != null) {
                inputLine = inputLine.split(" ")[1];
                if (inputLine.endsWith(".html")) {
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    resultado = "" + bytes.length;
                    formato = "text/html";
                } else if (inputLine.endsWith(".png")) {
                    bytes = Files.readAllBytes(new File("./" + inputLine).toPath());
                    resultado = "" + bytes.length;
                    formato = "image/png";
                } else {
                    bytes = Files.readAllBytes(new File("./index.html").toPath());
                    resultado = "" + bytes.length;
                    formato = "text/html";
                }
            } else {
                bytes = Files.readAllBytes(new File("./index.html").toPath());
                resultado = "" + bytes.length;
                formato = "text/html";
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: "
                    + formato
                    + "\r\n"
                    + resultado
                    + "\r\n\r\n";
            
            byte[] hByte = outputLine.getBytes();
            byte[] rta = new byte[bytes.length + hByte.length];
            for (int i = 0; i < hByte.length; i++) {
                rta[i] = hByte[i];
            }
            for (int i = hByte.length; i < hByte.length + bytes.length; i++) {
                rta[i] = bytes[i - hByte.length];
            }
            clientSocket.getOutputStream().write(rta);
            clientSocket.close();
        } catch (IOException e) {
            Logger.getLogger(Despliegue.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
