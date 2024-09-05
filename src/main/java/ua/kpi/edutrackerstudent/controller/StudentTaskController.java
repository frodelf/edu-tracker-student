package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.edutrackerstudent.dto.ForSelect2Dto;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskRequestForFilter;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskResponseForViewAll;
import ua.kpi.edutrackerstudent.service.StudentTaskService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("student-task")
public class StudentTaskController {
    private final StudentTaskService studentTaskService;
    @GetMapping("/get-all")
    public ResponseEntity<Page<StudentsTaskResponseForViewAll>> getAllOpenTasks(StudentsTaskRequestForFilter studentsTaskRequestForFilter) {
        return ResponseEntity.ok(studentTaskService.getAll(studentsTaskRequestForFilter));
    }
    @GetMapping("/get-all-task-for-select")
    public ResponseEntity<Page<Map<String, String>>> getAllTaskForSelect(ForSelect2Dto forSelect2Dto) {
        return ResponseEntity.ok(studentTaskService.getAllForTaskForSelect(forSelect2Dto));
    }
    @PutMapping("/cancel-work")
    public ResponseEntity<Long> cancelWork(@RequestParam Long id) {
        studentTaskService.cancelWork(id);
        return ResponseEntity.ok(id);
    }
    @GetMapping("/get-all-course-for-select")
    public ResponseEntity<Page<Map<String, String>>> getAllCourseForSelect(ForSelect2Dto forSelect2Dto) {
        return ResponseEntity.ok(studentTaskService.getAllForCourseForSelect(forSelect2Dto));
    }
    @PutMapping("/send-work")
    public ResponseEntity<Long> sendWork(@RequestParam MultipartFile file, @RequestParam Long id){
        studentTaskService.sendWork(id, file);
        return ResponseEntity.ok(id);
    }
}