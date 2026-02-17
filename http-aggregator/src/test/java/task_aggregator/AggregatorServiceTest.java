package task_aggregator;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AggregatorServiceTest {

    @Test
    void aggregationTest() {
        HttpClientService mockService = mock(HttpClientService.class);

        when(mockService.get("u1")).thenReturn(new RequestResult("u1", true, 200, 100, null));
        when(mockService.get("u2")).thenReturn(new RequestResult("u2", false, 500, 300, "error"));

        AggregatorService aggregator = new AggregatorService(mockService);
        AggregationResult result = aggregator.aggregate(List.of("u1","u2"), 2);

        assertEquals(1, result.successCount());
        assertEquals(1, result.errorCount());
        assertEquals(200.0, result.avgResponseTimeMs());
    }
}
