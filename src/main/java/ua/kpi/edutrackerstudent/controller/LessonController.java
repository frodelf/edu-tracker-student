package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kpi.edutrackerstudent.dto.lesson.BeginningLessonResponse;
import ua.kpi.edutrackerstudent.service.LessonService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;
    @GetMapping("/get-beginning")
    public ResponseEntity<List<BeginningLessonResponse>> getBeginningLessons(){
        return ResponseEntity.ok(lessonService.getAllBeginningLessons());
    }
}