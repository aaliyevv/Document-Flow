package com.documentflow.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue documentQueue(){
        return new Queue("document.queue", true);
        // durable = true, queue survives RabbitMQ restart
    }
}
