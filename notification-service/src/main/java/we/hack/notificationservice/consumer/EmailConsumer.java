package we.hack.notificationservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import we.hack.notificationservice.dto.EmailMessageDto;
import we.hack.notificationservice.enums.MailMessageType;
import we.hack.notificationservice.service.MailService;

/**
 * This class is responsible for consuming messages from Kafka topics.
 * It uses the MailService to send emails based on the consumed messages.
 */
@Component
public class EmailConsumer {

    private final MailService mailService;

    @Autowired
    public EmailConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * This method consumes messages from the Kafka topic defined in the application properties.
     * It logs the consumed message and uses the MailService to send an email.
     * The type of the email is defined as GREETING.
     *
     * @param message the consumed message from the Kafka topic.
     */
    @KafkaListener(topics = {"${spring.kafka.queues.greeting}"}, groupId = "mail-service")
    public void consumeNewsLetter(EmailMessageDto message) {
        mailService.send(message, MailMessageType.GREETING);
    }
}