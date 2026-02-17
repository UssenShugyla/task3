package task_aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AggregatorService {

    private final HttpClientService httpClientService;

    public AggregatorService(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    public AggregationResult aggregate(List<String> urls, int threads) {
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger errors = new AtomicInteger();
        AtomicLong totalTime = new AtomicLong();

        List<Future<RequestResult>> futures = new ArrayList<>();

        try {
            for (String url : urls) {
                futures.add(pool.submit(() -> httpClientService.get(url)));
            }

            for (Future<RequestResult> f : futures) {
                try {
                    RequestResult r = f.get();
                    totalTime.addAndGet(r.timeMs());
                    if (r.success()) success.incrementAndGet();
                    else errors.incrementAndGet();
                } catch (ExecutionException e) {
                    errors.incrementAndGet();
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            pool.shutdown();
        }

        double avg = urls.isEmpty() ? 0 : totalTime.get() / (double) urls.size();
        return new AggregationResult(success.get(), errors.get(), avg);
    }
}
