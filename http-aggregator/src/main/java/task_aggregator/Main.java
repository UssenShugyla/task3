package task_aggregator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://example.com",
                "https://httpstat.us/200",
                "https://httpstat.us/500"
        );

        HttpClientService httpClientService = new HttpClientService();
        AggregatorService aggregator = new AggregatorService(httpClientService);

        AggregationResult result = aggregator.aggregate(urls, 4);

        System.out.println("==== Aggregation Result ====");
        System.out.println("Success: " + result.successCount());
        System.out.println("Errors : " + result.errorCount());
        System.out.println("Avg ms : " + result.avgResponseTimeMs());
    }
}
