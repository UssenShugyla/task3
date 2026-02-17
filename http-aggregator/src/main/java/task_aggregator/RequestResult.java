package task_aggregator;

public record RequestResult(
        String url,
        boolean success,
        int statusCode,
        long timeMs,
        String errorMessage
) {}
