package task_aggregator;

import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class HttpClientServiceTest {

    @Test
    void success200() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResp = mock(HttpResponse.class);

        when(mockResp.statusCode()).thenReturn(200);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResp);

        HttpClientService service = new HttpClientService(mockClient);
        RequestResult result = service.get("https://test");

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
    }
}
