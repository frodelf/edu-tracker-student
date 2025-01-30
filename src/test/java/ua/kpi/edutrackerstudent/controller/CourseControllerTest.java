package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseForView;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;
import ua.kpi.edutrackerstudent.service.CourseService;
import ua.kpi.edutrackerstudent.service.StatisticService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private StatisticService statisticService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void getByIdModel() {
        Long id = 1L;
        CourseResponseForView courseResponse = mock(CourseResponseForView.class);
        when(courseService.getByIdForView(id)).thenReturn(courseResponse);

        ModelAndView result = courseController.getByIdModel(id);

        assertThat(result.getViewName()).isEqualTo("course/index");
        assertThat(result.getModel().get("course")).isEqualTo(courseResponse);
    }

    @Test
    void getStatistic() {
        Long id = 1L;
        Map<String, String> statistics = Map.of("key", "value");
        when(statisticService.statisticForCourse(id)).thenReturn(statistics);

        ResponseEntity<Map<String, String>> result = courseController.getStatistic(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(statistics);
    }

    @Test
    void getAll() {
        int page = 1;
        int pageSize = 10;
        Page<CourseResponseViewAll> pageResult = mock(Page.class);
        when(courseService.getAll(page, pageSize)).thenReturn(pageResult);

        ResponseEntity<Page<CourseResponseViewAll>> result = courseController.getAll(page, pageSize);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(pageResult);
    }
}
