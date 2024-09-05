package ua.kpi.edutrackerstudent.dto.studenttask;

import lombok.Data;
import ua.kpi.edutrackerentity.entity.enums.StatusStudentsTask;

import java.util.Map;

@Data
public class StudentsTaskResponseForViewAll {
    private Long id;
    private Map<String, String> course;
    private StatusStudentsTask studentsTaskStatus;
    private String taskName;
    private Double mark;
    private String taskFile;
}