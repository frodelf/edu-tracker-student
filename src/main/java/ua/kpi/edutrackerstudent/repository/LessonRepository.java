package ua.kpi.edutrackerstudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerentity.entity.Lesson;
import ua.kpi.edutrackerentity.entity.enums.StatusLesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson> {
    List<Lesson> findAllByCourseInAndStatus(List<Course> courses, StatusLesson status);
}