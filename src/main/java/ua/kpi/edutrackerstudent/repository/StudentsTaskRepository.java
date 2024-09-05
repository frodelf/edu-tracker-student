package ua.kpi.edutrackerstudent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerentity.entity.StudentsTask;
import ua.kpi.edutrackerentity.entity.Task;

public interface StudentsTaskRepository extends JpaRepository<StudentsTask, Long>, JpaSpecificationExecutor<StudentsTask> {
    @Query("select distinct s.task from StudentsTask s where lower(s.task.name) like lower(CONCAT('%', :taskName, '%')) and s.student.id = :studentId")
    Page<Task> findAllTaskByTaskNameLikeAndStudentId(String taskName, Long studentId, Pageable pageable);
    @Query("select distinct s.task.course from StudentsTask s where lower(s.task.course.name) like lower(CONCAT('%', :courseName, '%')) and s.student.id = :studentId")
    Page<Course> findAllCourseByCourseNameLikeAndStudentId(String courseName, Long studentId, Pageable pageable);
}