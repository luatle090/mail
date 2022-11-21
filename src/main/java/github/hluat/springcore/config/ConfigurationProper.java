package github.hluat.springcore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "github.mail")
@Data
@NoArgsConstructor
public class ConfigurationProper {

    // sleep follow second
    private Integer sleeping;
    private Integer batchSize;
}
