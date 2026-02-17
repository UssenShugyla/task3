package task_aggregator;

public record AggregationResult(
        int successCount,
        int errorCount,
        double avgResponseTimeMs
) {}
