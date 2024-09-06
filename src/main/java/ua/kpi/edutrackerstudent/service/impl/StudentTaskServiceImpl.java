package ua.kpi.edutrackerstudent.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.edutrackerentity.entity.Course;
import ua.kpi.edutrackerentity.entity.StudentsTask;
import ua.kpi.edutrackerentity.entity.Task;
import ua.kpi.edutrackerentity.entity.enums.StatusStudentsTask;
import ua.kpi.edutrackerstudent.dto.ForSelect2Dto;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskRequestForFilter;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskResponseForViewAll;
import ua.kpi.edutrackerstudent.mapper.StudentsTaskMapper;
import ua.kpi.edutrackerstudent.repository.StudentsTaskRepository;
import ua.kpi.edutrackerstudent.service.MinioService;
import ua.kpi.edutrackerstudent.service.StudentService;
import ua.kpi.edutrackerstudent.service.StudentTaskService;
import ua.kpi.edutrackerstudent.specification.StudentsTaskSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentTaskServiceImpl implements StudentTaskService {
    private final MinioService minioService;
    private final StudentService studentService;
    private final StudentsTaskRepository studentsTaskRepository;
    private final StudentsTaskMapper studentsTaskMapper = new StudentsTaskMapper();
    @Override
    public Page<StudentsTaskResponseForViewAll> getAll(StudentsTaskRequestForFilter studentsTaskRequestForFilter) {
        Pageable pageable = PageRequest.of(studentsTaskRequestForFilter.getPage(), studentsTaskRequestForFilter.getPageSize(), Sort.by(Sort.Order.desc("id")));
        Specification<StudentsTask> specification = new StudentsTaskSpecification(studentsTaskRequestForFilter, studentService.getAuthStudentForGlobal().getId());
        return studentsTaskMapper.toResponseForViewList(studentsTaskRepository.findAll(specification, pageable));
    }
    @Override
    public Page<Map<String, String>> getAllForTaskForSelect(ForSelect2Dto forSelect2Dto) {
        Pageable pageable = PageRequest.of(forSelect2Dto.getPage(), forSelect2Dto.getSize(), Sort.by(Sort.Order.desc("id")));
        Page<Task> tasks = studentsTaskRepository.findAllTaskByTaskNameLikeAndStudentId(forSelect2Dto.getQuery(), studentService.getAuthStudentForGlobal().getId(), pageable);
        List<Map<String, String>> list = new ArrayList<>();
        for (Task task : tasks.getContent()) {
            Map<String, String> map = new HashMap<>();
            map.put(task.getId().toString(), task.getName());
            list.add(map);
        }
        return new PageImpl<>(list, pageable, tasks.getTotalElements());
    }

    @Override
    public Page<Map<String, String>> getAllForCourseForSelect(ForSelect2Dto forSelect2Dto) {
        Pageable pageable = PageRequest.of(forSelect2Dto.getPage(), forSelect2Dto.getSize(), Sort.by(Sort.Order.desc("id")));
        Page<Course> courses = studentsTaskRepository.findAllCourseByCourseNameLikeAndStudentId(forSelect2Dto.getQuery(), studentService.getAuthStudentForGlobal().getId(), pageable);
        List<Map<String, String>> list = new ArrayList<>();
        for (Course course : courses.getContent()) {
            Map<String, String> map = new HashMap<>();
            map.put(course.getId().toString(), course.getName());
            list.add(map);
        }
        return new PageImpl<>(list, pageable, courses.getTotalElements());
    }
    @Override
    public StudentsTask getById(Long id) {
        return studentsTaskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("StudentTask with id = "+id+" not found")
        );
    }
    @Override
    @Transactional
    public StudentsTask save(StudentsTask studentsTask) {
        return studentsTaskRepository.save(studentsTask);
    }
    @Override
    @Transactional
    public void cancelWork(Long studentTaskId){
        StudentsTask studentsTask = getById(studentTaskId);
        studentsTask.setStatus(StatusStudentsTask.IN_PROCESS);
        studentsTask.setMark(null);
        //TODO дорабити видалення з мініо
        studentsTask.setMyWork(null);
        save(studentsTask);
    }
    @Override
    @Transactional
    @SneakyThrows
    public void sendWork(Long id, MultipartFile file) {
        StudentsTask studentsTask = getById(id);
        studentsTask.setStatus(StatusStudentsTask.GRANTED);
        studentsTask.setMyWork(minioService.putMultipartFile(file));
        save(studentsTask);
    }
    @Override
    public Long countStudentMarkByCourseId(Long courseId) {
        return studentsTaskRepository.countMarkByStudentIdAndCourseId(studentService.getAuthStudentForGlobal().getId(), courseId);
    }
    @Override
    public Long countAllByCourseId(Long courseId) {
        return studentsTaskRepository.countByStudentIdAndCourseId(studentService.getAuthStudentForGlobal().getId(), courseId);
    }
    @Override
    public Long countAllDoneTaskByCourseId(Long courseId) {
        return studentsTaskRepository.countByStatusesAndStudentIdAndCourseId(studentService.getAuthStudentForGlobal().getId(), courseId, List.of(StatusStudentsTask.EVALUATED));
    }
    @Override
    public Long countAllNotDoneTaskByCourseId(Long courseId) {
        return studentsTaskRepository.countByStatusesAndStudentIdAndCourseId(studentService.getAuthStudentForGlobal().getId(), courseId, List.of(StatusStudentsTask.IN_PROCESS));
    }
}