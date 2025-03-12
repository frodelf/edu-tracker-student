package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.service.StudentService;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalController {
    @Value("${server.host}")
    private String host;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${server.port}")
    private String port;
    private final StudentService studentService;

    @ModelAttribute("student")
    public StudentResponseForPersonalData globalTeamLeadAttribute() {
        return studentService.getAuthStudentForGlobal();
    }
    @ModelAttribute("host")
    public String globalHostAttribute() {return host;}
    @ModelAttribute("contextPath")
    public String globalContextPathAttribute() {return contextPath;}
    @ModelAttribute("port")
    public String globalPortAttribute() {return port;}
}