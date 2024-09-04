package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerstudent.service.StudentService;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalController {
    private final StudentService studentService;

    @ModelAttribute("student")
    public Student globalTeamLeadAttribute() {
        return studentService.getAuthStudentForGlobal();
    }
}