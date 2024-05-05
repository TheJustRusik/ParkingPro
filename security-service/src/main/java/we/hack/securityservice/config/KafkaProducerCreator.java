package we.hack.securityservice.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import we.hack.securityservice.model.dto.EmailMessageDto;

import java.util.Properties;

/**
 * KafkaProducerCreator is a configuration class for setting up a Kafka producer.
 * It uses properties defined in a properties file to configure the Kafka producer.
 */
@Configuration
public class KafkaProducerCreator {

    @Value("${spring.producer.bootstrap-servers}")
    private String KAFKA_SERVER;
    @Value("${spring.producer.key-serializer}")
    private String KAFKA_SERIALIZATION_KEY;
    @Value("${spring.producer.value-serializer}")
    private String KAFKA_SERIALIZATION_VALUE;

    /**
     * Creates a Kafka producer with properties defined in a properties file.
     * The properties file should contain the Kafka server address, key serializer, and value serializer.
     *
     * @return a Kafka producer configured with the properties defined in the properties file
     */
    public Producer<String, EmailMessageDto> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_SERVER);
        props.put("key.serializer", KAFKA_SERIALIZATION_KEY);
        props.put("value.serializer", KAFKA_SERIALIZATION_VALUE);
        return new KafkaProducer<>(props);
    }
}