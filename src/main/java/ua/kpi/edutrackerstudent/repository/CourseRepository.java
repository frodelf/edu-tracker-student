package ua.kpi.edutrackerstudent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerentity.entity.enums.StatusCourse;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByStudentsAndStatusCourse(List<Student> students, StatusCourse statusCourse, Pageable pageable);
}