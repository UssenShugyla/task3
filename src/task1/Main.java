package task1;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Integer> numbers =List.of(2,4,6,8,10,12,14,16);

        int n =Runtime.getRuntime().availableProcessors();
        n =Math.min(n, numbers.size());

        ExecutorService ex =Executors.newFixedThreadPool(n);

        int chunk = (numbers.size() + n - 1) / n;
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int part = 0; part < n; part++) {
            int from =part * chunk;
            int to =Math.min(from + chunk, numbers.size());
            if (from >= to) break;

            tasks.add(() -> {
                int sum = 0;
                for (int i = from; i < to; i++) sum += numbers.get(i);
                return sum;
            });
        }

        int total = 0;
        for (Future<Integer> f : ex.invokeAll(tasks)) {
            total += f.get();
        }

        ex.shutdown();
        System.out.println("Общая сумма =" + total);
    }
}
