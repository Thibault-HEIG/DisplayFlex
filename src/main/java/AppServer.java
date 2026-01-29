package main.java;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class AppServer {

    public static void main(String[] args) throws IOException {
        // 1. On lance la BDD avant le serveur Web
        DatabaseManager.initialiser();
        
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Route pour servir les fichiers statiques (HTML, CSS)
        server.createContext("/", new StaticFileHandler());

        // Route pour traiter l'input (API)
        server.createContext("/api/process", new ApiHandler());

        server.setExecutor(null); // Default executor
        System.out.println("Serveur demarre sur http://localhost:" + port);
        server.start();
    }

    // Gère l'affichage du site
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().toString();
            if (uri.equals("/"))
                uri = "/index.html"; // Page par défaut

            // Attention: Chemin relatif simple pour l'exercice.
            // À adapter selon ton dossier de lancement.
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

    // Gère la logique Java (C'est ici que tu dois coder ta logique !)
    static class ApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                InputStream is = t.getRequestBody();
                String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // --- LOGIQUE MÉTIER ---
                // TODO: Remplacer cette ligne par ta propre logique Java
                String reponseJava = "Java a reçu : " + input.toUpperCase();
                // ----------------------

                byte[] bytes = reponseJava.getBytes(StandardCharsets.UTF_8);
                t.sendResponseHeaders(200, bytes.length);
                OutputStream os = t.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }
    }
}