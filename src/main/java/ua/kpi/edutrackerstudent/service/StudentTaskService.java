package ua.kpi.edutrackerstudent.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.edutrackerentity.entity.StudentsTask;
import ua.kpi.edutrackerstudent.dto.ForSelect2Dto;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskRequestForFilter;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskResponseForViewAll;

import java.util.Map;

public interface StudentTaskService {
    Page<StudentsTaskResponseForViewAll> getAll(StudentsTaskRequestForFilter studentsTaskRequestForFilter);
    Page<Map<String, String>> getAllForTaskForSelect(ForSelect2Dto forSelect2Dto);
    Page<Map<String, String>> getAllForCourseForSelect(ForSelect2Dto forSelect2Dto);
    StudentsTask getById(Long id);
    StudentsTask save(StudentsTask studentsTask);
    void cancelWork(Long studentTaskId);
    void sendWork(Long id, MultipartFile file);
}
