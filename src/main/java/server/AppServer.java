package main.java.server;

import com.sun.net.httpserver.HttpServer;

import main.java.database.DatabaseManager; // importe la connection avec la DB
import main.java.handler.*; // importe les logiques de nos fichiers

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppServer {

    public static void main(String[] args) throws IOException {
        // 1. On initialise la database avant le serveur Web
        DatabaseManager.initialize();
        
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Route pour servir les fichiers statiques (HTML, CSS) [INFRASTRUCTURE]
        server.createContext("/", new StaticFileHandler());

        // Route pour traiter les inputs (API) [LIEN VERS LA LOGIQUE]
        // C'est ici qu'on branche les fichiers handler/
        server.createContext("/api/student", new StudentsHandler());

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