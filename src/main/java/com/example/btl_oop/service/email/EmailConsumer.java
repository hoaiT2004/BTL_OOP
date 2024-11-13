package com.example.btl_oop.service.email;

import com.example.btl_oop.model.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
@Component
@Slf4j
public class EmailConsumer {
    // lớp EmailConsumer để nhận thông điệp từ Kafka topic và thực hiện việc gửi email.

    @Autowired
    private EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void consume(EmailDTO emailDTO) {
        log.info("Email:"+emailDTO.toString());
        emailService.sendEmail(emailDTO);
    }
}
