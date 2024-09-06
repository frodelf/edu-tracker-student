package ua.kpi.edutrackerstudent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerentity.entity.StudentsTask;
import ua.kpi.edutrackerentity.entity.Task;
import ua.kpi.edutrackerentity.entity.enums.StatusStudentsTask;

import java.util.List;

public interface StudentsTaskRepository extends JpaRepository<StudentsTask, Long>, JpaSpecificationExecutor<StudentsTask> {
    @Query("select distinct s.task from StudentsTask s where lower(s.task.name) like lower(CONCAT('%', :taskName, '%')) and s.student.id = :studentId")
    Page<Task> findAllTaskByTaskNameLikeAndStudentId(String taskName, Long studentId, Pageable pageable);
    @Query("select distinct s.task.course from StudentsTask s where lower(s.task.course.name) like lower(CONCAT('%', :courseName, '%')) and s.student.id = :studentId")
    Page<Course> findAllCourseByCourseNameLikeAndStudentId(String courseName, Long studentId, Pageable pageable);
    @Query("SELECT COALESCE(SUM(st.mark), 0) FROM StudentsTask st WHERE st.student.id = :studentId AND st.task.course.id = :courseId")
    Long countMarkByStudentIdAndCourseId(Long studentId, Long courseId);
    @Query("SELECT COALESCE(COUNT(st), 0) FROM StudentsTask st WHERE st.status IN (:statuses) AND st.student.id = :studentId AND st.task.course.id = :courseId")
    Long countByStatusesAndStudentIdAndCourseId(Long studentId, Long courseId, List<StatusStudentsTask> statuses);
    @Query("SELECT COALESCE(COUNT(st), 0) FROM StudentsTask st WHERE st.student.id = :studentId AND st.task.course.id = :courseId")
    Long countByStudentIdAndCourseId(Long studentId, Long courseId);
}