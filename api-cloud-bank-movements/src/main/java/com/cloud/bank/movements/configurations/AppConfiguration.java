package com.cloud.bank.movements.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfiguration {
    String apiMicroserviceClientsUrl;
}
