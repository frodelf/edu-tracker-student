package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EnumControllerTest {

    private final EnumController enumController = new EnumController();

    @Test
    void getTaskStatus() {
        ResponseEntity<Map<String, String>> result = enumController.getTaskStatus();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        Map<String, String> body = result.getBody();
        assertThat(body).isNotNull();

        assertThat(body).containsEntry("IN_PROCESS", "IN_PROCESS");
        assertThat(body).containsEntry("EVALUATED", "EVALUATED");
        assertThat(body).containsEntry("GRANTED", "GRANTED");
        assertThat(body).containsEntry("OVERDUE", "OVERDUE");
    }
}
