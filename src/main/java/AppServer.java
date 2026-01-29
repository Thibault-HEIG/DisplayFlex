package main.java;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppServer {

    public static void main(String[] args) throws IOException {
        // 1. On lance la BDD avant le serveur Web
        DatabaseManager.initialiser();
        
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Route pour servir les fichiers statiques (HTML, CSS) [INFRASTRUCTURE]
        server.createContext("/", new StaticFileHandler());

        // Route pour traiter l'input (API) [LIEN VERS TON CODE]
        // C'est ici qu'on branche le fichier ApiHandler.java
        server.createContext("/api/process", new ApiHandler());

        server.setExecutor(null); // Default executor
        System.out.println("Serveur demarre sur http://localhost:" + port);
        server.start();
    }

    // Gère l'affichage du site (HTML/CSS) - Ne pas toucher
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().toString();
            if (uri.equals("/"))
                uri = "/index.html"; 

            String filePath = "public" + uri;

            if (Files.exists(Paths.get(filePath))) {
                byte[] response = Files.readAllBytes(Paths.get(filePath));
                t.sendResponseHeaders(200, response.length);
                OutputStream os = t.getResponseBody();
                os.write(response);
                os.close();
            } else {
                String response = "404 Not Found";
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}