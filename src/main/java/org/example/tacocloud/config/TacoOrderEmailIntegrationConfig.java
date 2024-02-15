package org.example.tacocloud.config;

import jakarta.mail.PasswordAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.MailReceivingMessageSource;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.messaging.MessageHandler;

@Configuration
public class TacoOrderEmailIntegrationConfig {
//    @Bean
//    public IntegrationFlow tacoOrderEmailFlow(
//            EmailProperties emailProps) {
//
//        return IntegrationFlow
//                .from(Mail.pop3InboundAdapter(emailProps.getImapUrl()),
//                        e -> e.poller(
//                                Pollers.fixedDelay(emailProps.getPollRate())))
//
//                .handle(mail -> {
//                    System.out.println(((Mail)mail.getPayload()).toString());
//                })
//                .get();
//    }


}
