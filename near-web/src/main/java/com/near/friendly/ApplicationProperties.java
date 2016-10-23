package com.near.friendly;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to near application.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "near", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Metrics metrics = new Metrics();

    @Getter
    @Setter
    public static class Metrics {

        private final Jmx jmx = new Jmx();

        private final Logs logs = new Logs();

        @Getter
        @Setter
        public static class Jmx {
            private boolean enabled = true;
        }

        @Getter
        @Setter
        public static class Logs {

            private boolean enabled = false;

            private long reportFrequency = 60;

        }
    }
}
