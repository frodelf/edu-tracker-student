package ua.kpi.edutrackerstudent.service;

import ua.kpi.edutrackerentity.entity.Student;

public interface StudentService {
    Student getAuthStudentForGlobal();
    Student getByEmail(String email);
    Student getByEmailForAuth(String username);
    boolean isAuthenticated();
}