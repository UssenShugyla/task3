package task4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static void handle(Socket socket) {
        try (
                Socket s= socket;
                InputStream in= s.getInputStream();
                OutputStream out = s.getOutputStream()
        ) {

            byte[] buffer = new byte[1024];
            int read= in.read(buffer);

            if (read== -1) return;

            String request = new String(buffer, 0, read, StandardCharsets.UTF_8);

            System.out.println("Client connected: "+ s.getInetAddress());
            System.out.println(request);

            if (!request.startsWith("GET")) {
                return;
            }

            String responseBody =
                    "<html><body><h1>Hello!</h1></body></html>";

            String httpResponse =
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html; charset=utf-8\r\n" +
                            "Content-Length: " +
                            responseBody.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n" +
                            responseBody;

            out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}