package com.hexplosif.OptimodFrontEnd;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.hexplosif.optimodfrontend")
public class CustomProperties {

    private String apiUrl;


}
