package ua.kpi.edutrackerstudent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import ua.kpi.edutrackerstudent.dto.MessageStudentClick;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PresentControllerImplTest {

    @Mock
    private AmqpTemplate rabbitTemplate;

    @InjectMocks
    private PresentControllerImpl presentController;

    @Test
    void sendMessageToQueue() throws JsonProcessingException {
        MessageStudentClick message = new MessageStudentClick();
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = objectMapper.writeValueAsString(message);

        ResponseEntity<String> result = presentController.sendMessageToQueue(message);

        verify(rabbitTemplate).convertAndSend("exchange", "key", messageJson);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Message sent successfully");
    }
}