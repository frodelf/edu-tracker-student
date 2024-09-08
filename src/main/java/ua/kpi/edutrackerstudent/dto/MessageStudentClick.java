package ua.kpi.edutrackerstudent.dto;

import lombok.Data;

@Data
public class MessageStudentClick {
    private Long studentId;
    private Long lessonId;
}