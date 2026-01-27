package nl.tudelft.app.frontend.metrics;

import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;

// Prometheus uses float64 internally, so use Java doubles
public class SmsMetrics {
    // counter
    public static DoubleAdder smsRequestsTotalHam = new DoubleAdder();
    public static DoubleAdder smsRequestsTotalSpam = new DoubleAdder();

    public static void addSmsRequest(String label) {
        switch (label) {
            case "ham" -> smsRequestsTotalHam.add(1.0);
            case "spam" -> smsRequestsTotalSpam.add(1.0);
            default -> throw new IllegalArgumentException("Unknown label '%s'".formatted(label));
        }
    }

    // gauge
    public static DoubleAdder inflightRequests = new DoubleAdder();

    // histogram
    // this excludes the +Inf bucket because it always equals smsLatencySecondsCount
    public static final List<Double> BUCKETS = List.of(0.05d, 0.1d, 0.25d, 0.5d, 1d, 2d, 5d);

    public static final DoubleAdder[] smsLatencySeconds = new DoubleAdder[BUCKETS.size()];
    public static DoubleAdder smsLatencySecondsCount = new DoubleAdder();
    public static DoubleAdder smsLatencySecondsSum = new DoubleAdder();

    public static void addLatencyValue(double latencySeconds) {
        smsLatencySecondsCount.add(1);
        smsLatencySecondsSum.add(latencySeconds);
        for (int i = 0; i < BUCKETS.size(); i++) {
            double bucket = BUCKETS.get(i);
            if (latencySeconds < bucket) {
                smsLatencySeconds[i].add(1);
            }
        }
    }

    private SmsMetrics() {}
}

