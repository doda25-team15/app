package nl.tudelft.app.frontend.metrics;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static nl.tudelft.app.frontend.metrics.SmsMetrics.*;

@RestController
public class MetricsController {

    private static final String RESPONSE_FORMAT = """
        # HELP sms_requests_total Total number of SMS classification requests
        # TYPE sms_requests_total counter
        sms_requests_total{result="ham",} %s
        sms_requests_total{result="spam",} %s
        # HELP sms_requests_inflight Currently active SMS classification requests
        # TYPE sms_requests_inflight gauge
        sms_requests_inflight %s
        # HELP sms_request_latency_seconds Latency for SMS classification requests
        # TYPE sms_request_latency_seconds histogram
        %s
        sms_request_latency_seconds_bucket{endpoint="/sms",le="+Inf",} %s
        sms_request_latency_seconds_count{endpoint="/sms",} %s
        sms_request_latency_seconds_sum{endpoint="/sms",} %s
        """;

    @GetMapping(value = "/metrics", produces = MediaType.TEXT_PLAIN_VALUE)
    public String metrics() {
        String smsLatencySecondsStr = IntStream.range(0, BUCKETS.size())
            .mapToObj(i -> {
                double bucket = BUCKETS.get(i);
                double value = smsLatencySeconds[i];
                return String.format("sms_request_latency_seconds_bucket{endpoint=\"/sms\",le=\"%s\",} %s", bucket, value);
            })
            .collect(Collectors.joining("\n"));
        return RESPONSE_FORMAT.formatted(smsRequestsTotalHam, smsRequestsTotalSpam, inflightRequests, smsLatencySecondsStr, smsLatencySecondsCount, smsLatencySecondsCount, smsLatencySecondsSum);
    }
}
