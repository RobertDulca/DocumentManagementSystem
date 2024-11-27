package at.fhtw.swkom.paperless.services.echo;

import at.fhtw.swkom.paperless.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EchoService {
    private final RabbitTemplate rabbit;

    @Autowired
    public EchoService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = RabbitMQConfig.OCR_QUEUE)
    public void processMessage(String message, @Header(RabbitMQConfig.ECHO_MESSAGE_COUNT_PROPERTY_NAME) int messageCount) {
        System.out.println("Received Message #" + messageCount + ": " + message);

        // Simulate processing delay
        try {
            Thread.sleep(message.length() * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Processing interrupted");
        }

        // Forward the processed message to the Echo_Out queue
        rabbit.convertAndSend(RabbitMQConfig.RESULT_QUEUE, "Echo " + message);
    }
}
