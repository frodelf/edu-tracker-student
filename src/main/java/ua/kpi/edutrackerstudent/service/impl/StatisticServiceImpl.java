package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerstudent.service.CourseService;
import ua.kpi.edutrackerstudent.service.ReviewService;
import ua.kpi.edutrackerstudent.service.StatisticService;
import ua.kpi.edutrackerstudent.service.StudentTaskService;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StudentTaskService studentTaskService;
    private final CourseService courseService;
    private final ReviewService reviewService;
    @Override
    public Map<String, String> statisticForCourse(Long courseId) {
        log.info("StatisticServiceImpl statisticForCourse start");
        Course course = courseService.getById(courseId);
        Map<String, String> map = new HashMap<>();
        map.put("mark", String.valueOf(studentTaskService.countStudentMarkByCourseId(courseId)));
        map.put("lessons", String.valueOf(reviewService.countAllVisitedLessonByCourseId(courseId)));
        map.put("literatures", String.valueOf(course.getLiteratures().size()));
        map.put("allTasks", String.valueOf(studentTaskService.countAllByCourseId(courseId)));
        map.put("doneTasks", String.valueOf(studentTaskService.countAllDoneTaskByCourseId(courseId)));
        map.put("notDoneTasks", String.valueOf(studentTaskService.countAllNotDoneTaskByCourseId(courseId)));
        log.info("StatisticServiceImpl statisticForCourse finish");
        return map;
    }
}