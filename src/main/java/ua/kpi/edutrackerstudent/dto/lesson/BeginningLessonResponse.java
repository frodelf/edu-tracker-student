package ua.kpi.edutrackerstudent.dto.lesson;

import lombok.Data;

import java.util.Map;

@Data
public class BeginningLessonResponse {
    private Long id;
    private String professorName;
    private Map<String, String> course;
    private String link;
}