package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.edutrackerstudent.dto.ForSelect2Dto;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskRequestForFilter;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskResponseForViewAll;
import ua.kpi.edutrackerstudent.service.StudentTaskService;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StudentTaskControllerTest {

    @Mock
    private StudentTaskService studentTaskService;

    @InjectMocks
    private StudentTaskController studentTaskController;

    @Test
    void getAllOpenTasks() {
        StudentsTaskRequestForFilter filter = new StudentsTaskRequestForFilter();
        Page<StudentsTaskResponseForViewAll> mockPage = org.mockito.Mockito.mock(Page.class);
        when(studentTaskService.getAll(filter)).thenReturn(mockPage);

        ResponseEntity<Page<StudentsTaskResponseForViewAll>> result = studentTaskController.getAllOpenTasks(filter);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mockPage);
    }

    @Test
    void getAllTaskForSelect() {
        ForSelect2Dto forSelect2Dto = new ForSelect2Dto();
        Page<Map<String, String>> mockPage = org.mockito.Mockito.mock(Page.class);
        when(studentTaskService.getAllForTaskForSelect(forSelect2Dto)).thenReturn(mockPage);

        ResponseEntity<Page<Map<String, String>>> result = studentTaskController.getAllTaskForSelect(forSelect2Dto);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mockPage);
    }

    @Test
    void cancelWork() {
        Long taskId = 1L;

        ResponseEntity<Long> result = studentTaskController.cancelWork(taskId);

        verify(studentTaskService).cancelWork(taskId);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(taskId);
    }

    @Test
    void getAllCourseForSelect() {
        ForSelect2Dto forSelect2Dto = new ForSelect2Dto();
        Page<Map<String, String>> mockPage = org.mockito.Mockito.mock(Page.class);
        when(studentTaskService.getAllForCourseForSelect(forSelect2Dto)).thenReturn(mockPage);

        ResponseEntity<Page<Map<String, String>>> result = studentTaskController.getAllCourseForSelect(forSelect2Dto);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(mockPage);
    }

    @Test
    void sendWork() {
        Long taskId = 1L;
        MultipartFile file = org.mockito.Mockito.mock(MultipartFile.class);

        ResponseEntity<Long> result = studentTaskController.sendWork(file, taskId);

        verify(studentTaskService).sendWork(taskId, file);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(taskId);
    }
}
