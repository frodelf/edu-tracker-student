package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalControllerTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private GlobalController globalController;

    @Test
    void globalTeamLeadAttribute() {
        StudentResponseForPersonalData studentResponse = new StudentResponseForPersonalData();
        when(studentService.getAuthStudentForGlobal()).thenReturn(studentResponse);

        StudentResponseForPersonalData result = globalController.globalTeamLeadAttribute();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(studentResponse);
    }
}