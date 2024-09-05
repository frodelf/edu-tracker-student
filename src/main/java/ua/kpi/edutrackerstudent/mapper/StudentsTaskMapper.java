package ua.kpi.edutrackerstudent.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ua.kpi.edutrackerentity.entity.StudentsTask;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskResponseForViewAll;

import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class StudentsTaskMapper {
    public Page<StudentsTaskResponseForViewAll> toResponseForViewList(Page<StudentsTask> studentsTasks) {
        return new PageImpl<>(studentsTasks.getContent().stream()
                .map(this::toDtoForViewAll)
                .collect(Collectors.toList()), studentsTasks.getPageable(), studentsTasks.getTotalElements());
    }

    private StudentsTaskResponseForViewAll toDtoForViewAll(StudentsTask studentsTask) {
        StudentsTaskResponseForViewAll dto = new StudentsTaskResponseForViewAll();
        dto.setId(studentsTask.getId());
        dto.setStudentsTaskStatus(studentsTask.getStatus());
        dto.setMark(studentsTask.getMark());
        dto.setTaskFile(studentsTask.getMyWork());
        if(nonNull(studentsTask.getTask())){
            dto.setTaskName(studentsTask.getTask().getName());
            if(nonNull(studentsTask.getTask().getCourse())){
                dto.setCourse(Collections.singletonMap(studentsTask.getTask().getCourse().getId().toString(), studentsTask.getTask().getCourse().getName()));
            }
        }
        return dto;
    }
}
