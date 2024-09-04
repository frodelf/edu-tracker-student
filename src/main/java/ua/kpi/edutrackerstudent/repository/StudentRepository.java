package ua.kpi.edutrackerstudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.edutrackerentity.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}