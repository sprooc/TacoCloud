package org.example.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class FileWriterIntegrationConfig {
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return text ->text.toUpperCase();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("D:\\codes\\tmp"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

//    @Bean
//    @InboundChannelAdapter(poller = @Poller(fixedRate = "1000"), channel = "numberChannel")
//    public MessageSource<Integer> numberSource(AtomicInteger source) {
//        return () -> {
//            return new GenericMessage<>(source.getAndIncrement());
//        };
//    }
//    @Bean
//    public AtomicInteger atomicInteger() {
//        return new AtomicInteger();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "numberChannel")
//    public MessageHandler numberChannelHandler() {
//        return number -> {
//            System.out.println(number);
//        };
//    }
}
