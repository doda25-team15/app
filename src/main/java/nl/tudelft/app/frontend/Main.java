package nl.tudelft.app.frontend;

import nl.tudelft.libversion.util.LoggerUtil;
import nl.tudelft.libversion.util.VersionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class Main {

    private static final Logger LOGGER = LoggerUtil.getLogger();

    public static void main(String[] args) {
        LOGGER.info("Using lib-version %s".formatted(VersionUtil.VERSION));
        SpringApplication.run(Main.class, args);
    }
}
