package ua.kpi.edutrackerstudent.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.edutrackerstudent.dto.student.StudentRequestForPersonalData;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.service.StudentService;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @GetMapping("/personal-data")
    public ModelAndView personalData() {
        return new ModelAndView("student/personal-data");
    }
    @GetMapping("/get-personal-data")
    public ResponseEntity<StudentResponseForPersonalData> getPersonalData() {
        return ResponseEntity.ok(studentService.getAuthStudentForGlobal());
    }
    @PutMapping("/update-personal-data")
    public ResponseEntity<Long> updatePersonalData(@ModelAttribute @Valid StudentRequestForPersonalData studentRequestForPersonalData){
        studentService.updatePersonalData(studentRequestForPersonalData);
        return ResponseEntity.ok(studentRequestForPersonalData.getId());
    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String password){
        if(password.length() < 8){
            return ResponseEntity.badRequest().body("Password must be at least 8 characters");
        }
        studentService.changePassword(password);
        return ResponseEntity.ok("Password changed successfully");
    }
}