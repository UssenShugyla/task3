package task4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        int port = 8080;

        try (ServerSocket server = new ServerSocket(port)) {

            System.out.println("Server started at http://localhost:" + port);

            while (true) {
                Socket socket = server.accept();
                System.out.println("New client connected");

                HttpServer.handle(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}