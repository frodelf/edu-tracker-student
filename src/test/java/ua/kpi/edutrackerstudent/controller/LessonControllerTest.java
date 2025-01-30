package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ua.kpi.edutrackerstudent.dto.lesson.BeginningLessonResponse;
import ua.kpi.edutrackerstudent.service.LessonService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonControllerTest {

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private LessonController lessonController;

    @Test
    void getBeginningLessons() {
        List<BeginningLessonResponse> beginningLessonResponses = List.of(new BeginningLessonResponse(), new BeginningLessonResponse());
        when(lessonService.getAllBeginningLessons()).thenReturn(beginningLessonResponses);

        ResponseEntity<List<BeginningLessonResponse>> result = lessonController.getBeginningLessons();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(beginningLessonResponses);
    }
}
