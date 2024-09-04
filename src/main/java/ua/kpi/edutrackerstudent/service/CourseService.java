package ua.kpi.edutrackerstudent.service;

import org.springframework.data.domain.Page;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;

public interface CourseService {
    Page<CourseResponseViewAll> getAll(int page, int pageSize);
}
