package org.example.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class UpperCaseIntegrationConfig {
    @Bean
    @Transformer(inputChannel = "inChannel", outputChannel = "outChannel")
    public GenericTransformer<String, String> toUpperCaseTransformer() {
        return text -> text.toUpperCase();
    }
    @Bean
    public MessageChannel outChannel() {
        return new DirectChannel();
    }
}
