package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.edutrackerstudent.dto.student.StudentRequestForPersonalData;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.service.StudentService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void personalData() {
        ModelAndView result = studentController.personalData();
        assertThat(result.getViewName()).isEqualTo("student/personal-data");
    }

    @Test
    void getPersonalData() {
        StudentResponseForPersonalData studentResponse = new StudentResponseForPersonalData();
        when(studentService.getAuthStudentForGlobal()).thenReturn(studentResponse);

        ResponseEntity<StudentResponseForPersonalData> result = studentController.getPersonalData();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(studentResponse);
    }

    @Test
    void updatePersonalData() {
        StudentRequestForPersonalData studentRequest = new StudentRequestForPersonalData();
        studentRequest.setId(1L);

        ResponseEntity<Long> result = studentController.updatePersonalData(studentRequest);

        verify(studentService).updatePersonalData(studentRequest);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(1L);
    }
}
