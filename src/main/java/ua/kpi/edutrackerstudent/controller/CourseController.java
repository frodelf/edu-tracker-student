package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;
import ua.kpi.edutrackerstudent.service.CourseService;
import ua.kpi.edutrackerstudent.service.StatisticService;

import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final StatisticService statisticService;
    @GetMapping("/{id}")
    public ModelAndView getByIdModel(@PathVariable Long id){
        return new ModelAndView("course/index", "course", courseService.getByIdForView(id));
    }
    @GetMapping("/statistic")
    public ResponseEntity<Map<String, String>> getStatistic(@RequestParam Long id){
        return ResponseEntity.ok(statisticService.statisticForCourse(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<CourseResponseViewAll>> getAll(@RequestParam int page, @RequestParam int pageSize){
        return ResponseEntity.ok(courseService.getAll(page, pageSize));
    }
}