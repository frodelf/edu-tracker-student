package ua.kpi.edutrackerstudent.mapper;

import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseForView;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;
import ua.kpi.edutrackerstudent.service.MinioService;

import java.util.stream.Collectors;

public class CourseMapper {
    @SneakyThrows
    public CourseResponseViewAll toDtoForView(Course course, MinioService minioService) {
        CourseResponseViewAll courseResponseViewAll = new CourseResponseViewAll();
        courseResponseViewAll.setId(course.getId());
        courseResponseViewAll.setName(course.getName());
        courseResponseViewAll.setGoal(course.getGoal());
        if(course.getStudents()!=null)
            courseResponseViewAll.setNumberOfStudents(course.getStudents().size());
        if(course.getImage()!=null)
            courseResponseViewAll.setImage(minioService.getUrl(course.getImage()));
        return courseResponseViewAll;
    }
    public Page<CourseResponseViewAll> toDtoListForView(Page<Course> courses, MinioService minioService) {
        return new PageImpl<>(
                courses.getContent().stream()
                        .map(course -> toDtoForView(course, minioService))
                        .collect(Collectors.toList()),
                courses.getPageable(),
                courses.getTotalElements()
        );
    }
    public CourseResponseForView toResponseForView(Course course) {
        CourseResponseForView courseResponseForView = new CourseResponseForView();
        courseResponseForView.setId(course.getId());
        courseResponseForView.setGoal(course.getGoal());
        courseResponseForView.setName(course.getName());
        courseResponseForView.setImage(course.getImage());
        return courseResponseForView;
    }
}