package com.example.btl_oop.service.email;

import com.example.btl_oop.model.dto.EmailDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class EmailProducer {
    //lớp EmailProducer để gửi thông điệp email tới Kafka topic.
    private final KafkaTemplate<String, EmailDTO> kafkaTemplate;
    private final static String topic = "email-topic";

    public EmailProducer(KafkaTemplate<String, EmailDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailDTO emailDTO) {
        kafkaTemplate.send(topic, emailDTO);
    }
}
