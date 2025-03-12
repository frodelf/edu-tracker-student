package ua.kpi.edutrackerstudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.edutrackerentity.entity.Literature;

import java.util.List;

public interface LiteratureRepository extends JpaRepository<Literature, Long> {
    List<Literature> findAllByCourseId(Long courseId);
}