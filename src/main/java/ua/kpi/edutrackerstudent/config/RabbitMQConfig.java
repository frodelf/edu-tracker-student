package ua.kpi.edutrackerstudent.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "exchange";

    public static final String QUEUE_NAME = "queue";

    public static final String ROUTING_KEY = "key";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}