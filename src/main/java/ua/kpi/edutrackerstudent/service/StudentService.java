package ua.kpi.edutrackerstudent.service;

import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerstudent.dto.student.StudentRequestForPersonalData;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;

public interface StudentService {
    Student getAuthStudent();
    StudentResponseForPersonalData getAuthStudentForGlobal();
    Student getByEmail(String email);
    Student getByEmailForAuth(String username);
    boolean isAuthenticated();
    void updatePersonalData(StudentRequestForPersonalData studentRequestForPersonalData);
    Student save(Student student);
    Student getById(Long id);
}