package at.fhtw.swkom.paperless.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String OCR_QUEUE_NAME = "OCR_QUEUE";
    public static final String RESULT_QUEUE_NAME = "RESULT_QUEUE";

    public static final String ECHO_MESSAGE_COUNT_PROPERTY_NAME = "MessageCount";

    @Bean
    public Queue echoInQueue() {
        return new Queue(OCR_QUEUE_NAME, false);
    }

    @Bean
    public Queue echoOutQueue() { return new Queue(RESULT_QUEUE_NAME, false); }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("queue");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setDefaultReceiveQueue(OCR_QUEUE_NAME);
        return rabbitTemplate;
    }

}
