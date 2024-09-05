package ua.kpi.edutrackerstudent.dto.studenttask;

import jakarta.validation.constraints.Min;
import lombok.Data;
import ua.kpi.edutrackerentity.entity.enums.StatusStudentsTask;

@Data
public class StudentsTaskRequestForFilter {
    @Min(value = 0, message = "{error.field.min-value}")
    private int page;
    @Min(value = 1, message = "{error.field.min-value}")
    private int pageSize;
    private Long taskId;
    private Long courseId;
    private StatusStudentsTask status;
}