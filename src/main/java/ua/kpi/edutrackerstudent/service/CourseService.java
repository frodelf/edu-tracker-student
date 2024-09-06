package ua.kpi.edutrackerstudent.service;

import org.springframework.data.domain.Page;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseForView;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;

import java.util.Map;

public interface CourseService {
    Page<CourseResponseViewAll> getAll(int page, int pageSize);
    CourseResponseForView getByIdForView(Long id);
    Course getById(Long id);
    Boolean isOnCourse(Course course);
}
