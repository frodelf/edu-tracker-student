package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kpi.edutrackerentity.entity.Literature;
import ua.kpi.edutrackerstudent.service.LiteratureService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/literature")
public class LiteratureController {
    private final LiteratureService literatureService;

    @GetMapping("/get-all-by-course-id/{courseId}")
    public ResponseEntity<List<Literature>> getAllByCourseId(@PathVariable long courseId) {
        return ResponseEntity.ok(literatureService.getLiteratureByCourseId(courseId));
    }
}