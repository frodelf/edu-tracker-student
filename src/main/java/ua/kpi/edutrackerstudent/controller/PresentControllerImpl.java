package ua.kpi.edutrackerstudent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kpi.edutrackerstudent.dto.MessageStudentClick;

@Controller
@RequiredArgsConstructor
@RequestMapping("/present")
public class PresentControllerImpl {
    private final AmqpTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "exchange";
    private static final String ROUTING_KEY = "key";

    @PostMapping("/send")
    public ResponseEntity<String> sendMessageToQueue(@ModelAttribute MessageStudentClick message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, objectMapper.writeValueAsString(message));
        return ResponseEntity.ok("Message sent successfully");
    }
}