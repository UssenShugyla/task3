package task_aggregator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClientService {
    private static final Logger log = Logger.getLogger(HttpClientService.class.getName());
    private final HttpClient client;

    public HttpClientService() {
        this(HttpClient.newHttpClient());
    }

    public HttpClientService(HttpClient client) {
        this.client = client;
    }

    public RequestResult get(String url) {
        long start = System.nanoTime();
        log.info(() -> "START request: " + url);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            long timeMs = (System.nanoTime() - start) / 1_000_000;
            int code = response.statusCode();

            if (code == 200) {
                log.info(() -> "DONE request: " + url);
                return new RequestResult(url, true, code, timeMs, null);
            } else {
                log.warning(() -> "ERROR code: " + code);
                return new RequestResult(url, false, code, timeMs, "Non-200 status");
            }

        } catch (IOException | InterruptedException e) {
            long timeMs = (System.nanoTime() - start) / 1_000_000;
            log.log(Level.SEVERE, "REQUEST FAILED", e);
            return new RequestResult(url, false, -1, timeMs, e.getMessage());
        }
    }
}
