package nl.tudelft.app.frontend.metrics;

import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;

import static nl.tudelft.app.frontend.metrics.SmsMetrics.REGISTRY;

@RestController
public class MetricsController {

    @GetMapping(value = "/metrics", produces = MediaType.TEXT_PLAIN_VALUE)
    public String metrics() throws IOException {
        Writer writer = new StringWriter();
        Enumeration<io.prometheus.client.Collector.MetricFamilySamples> mfs =
                REGISTRY.metricFamilySamples();
        TextFormat.write004(writer, mfs);
        return writer.toString();
    }
}

