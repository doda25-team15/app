package nl.tudelft.app.frontend.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.CollectorRegistry;

public class SmsMetrics {

    public static final CollectorRegistry REGISTRY = new CollectorRegistry();

    public static final Counter smsRequestsTotal = Counter.build()
            .name("sms_requests_total")
            .help("Total number of SMS classification requests")
            .labelNames("result")          // "spam" / "ham"
            .register(REGISTRY);

    public static final Gauge inflightRequests = Gauge.build()
            .name("sms_requests_inflight")
            .help("Currently active SMS classification requests")
            .register(REGISTRY);

    public static final Histogram smsLatencySeconds = Histogram.build()
            .name("sms_request_latency_seconds")
            .help("Latency for SMS classification requests")
            .labelNames("endpoint")        // e.g. "/sms"
            .buckets(0.05, 0.1, 0.25, 0.5, 1, 2, 5)
            .register(REGISTRY);

    private SmsMetrics() {}
}

