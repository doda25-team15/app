package nl.tudelft.app.frontend.metrics;

import java.util.List;

// Prometheus uses float64 internally, so use Java doubles
public class SmsMetrics {
    // counter
    public static double smsRequestsTotalHam = 0;
    public static double smsRequestsTotalSpam = 0;

    public static void addSmsRequest(String label) {
        switch (label) {
            case "ham" -> smsRequestsTotalHam++;
            case "spam" -> smsRequestsTotalSpam++;
            default -> throw new IllegalArgumentException("Unknown label '%s'".formatted(label));
        }
    }

    // gauge
    public static double inflightRequests = 0;

    // histogram
    // this excludes the +Inf bucket because it always equals smsLatencySecondsCount
    public static final List<Double> BUCKETS = List.of(0.05d, 0.1d, 0.25d, 0.5d, 1d, 2d, 5d);

    public static final double[] smsLatencySeconds = new double[BUCKETS.size()];
    public static double smsLatencySecondsCount = 0;
    public static double smsLatencySecondsSum = 0;

    public static void addLatencyValue(double latencySeconds) {
        smsLatencySecondsCount++;
        smsLatencySecondsSum += latencySeconds;
        for (int i = 0; i < BUCKETS.size(); i++) {
            double bucket = BUCKETS.get(i);
            if (latencySeconds < bucket) {
                smsLatencySeconds[i]++;
            }
        }
    }

    private SmsMetrics() {}
}

