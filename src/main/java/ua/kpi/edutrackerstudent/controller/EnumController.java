package ua.kpi.edutrackerstudent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kpi.edutrackerentity.entity.enums.StatusStudentsTask;
import ua.kpi.edutrackerentity.entity.enums.StatusTask;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/enum")
@RequiredArgsConstructor
public class EnumController {
    @GetMapping("/get-task-status")
    public ResponseEntity<Map<String, String>> getTaskStatus() {
        Map<String, String> map = new HashMap<>();
        for (StatusStudentsTask status : StatusStudentsTask.values()) {
            map.put(status.name(), status.name());
        }
        return ResponseEntity.ok(map);
    }
}