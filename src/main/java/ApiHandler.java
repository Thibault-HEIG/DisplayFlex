package main.java;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// C'est ICI que tu codes ta logique Java
public class ApiHandler implements HttpHandler {
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        if ("POST".equals(t.getRequestMethod())) {
            // 1. Lire ce que le site web a envoyé
            InputStream is = t.getRequestBody();
            String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // --- LOGIQUE MÉTIER (TA ZONE DE TRAVAIL) ---
            
            String reponseJava = "Java a reçu : " + input.toUpperCase();
            
            // -------------------------------------------

            // 2. Renvoyer la réponse au site web
            byte[] bytes = reponseJava.getBytes(StandardCharsets.UTF_8);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}