package ua.kpi.edutrackerstudent.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerentity.entity.enums.StatusCourse;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseForView;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;
import ua.kpi.edutrackerstudent.mapper.CourseMapper;
import ua.kpi.edutrackerstudent.repository.CourseRepository;
import ua.kpi.edutrackerstudent.service.CourseService;
import ua.kpi.edutrackerstudent.service.MinioService;
import ua.kpi.edutrackerstudent.service.StudentService;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final MinioService minioService;
    private final CourseMapper courseMapper = new CourseMapper();
    @Override
    public Page<CourseResponseViewAll> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return courseMapper.toDtoListForView(courseRepository.findAllByStudentsAndStatusCourse(List.of(studentService.getAuthStudent()), StatusCourse.ACTIVE, pageable), minioService);
    }
    @Override
    public CourseResponseForView getByIdForView(Long id) {
        return courseMapper.toResponseForView(getById(id));
    }
    @Override
    public Course getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Course with id = "+id+" not found")
        );
        isOnCourse(course);
        return course;
    }
    @Override
    public Boolean isOnCourse(Course course) {
        Student student = studentService.getAuthStudent();
        if(!student.getCourses().contains(course)){
            throw new AccessDeniedException("You don't have access to this course");
        }
        return true;
    }
}