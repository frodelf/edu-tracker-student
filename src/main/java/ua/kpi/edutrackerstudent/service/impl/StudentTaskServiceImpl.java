package ua.kpi.edutrackerstudent.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentTaskServiceImpl implements StudentTaskService {
    private final MinioService minioService;
    private final StudentService studentService;
    private final StudentsTaskRepository studentsTaskRepository;
    private final StudentsTaskMapper studentsTaskMapper = new StudentsTaskMapper();

    @Override
    public Page<StudentsTaskResponseForViewAll> getAll(StudentsTaskRequestForFilter studentsTaskRequestForFilter) {
        log.info("StudentTaskServiceImpl getAll start");
        Pageable pageable = PageRequest.of(studentsTaskRequestForFilter.getPage(), studentsTaskRequestForFilter.getPageSize(), Sort.by(Sort.Order.desc("id")));
        Specification<StudentsTask> specification = new StudentsTaskSpecification(studentsTaskRequestForFilter, studentService.getAuthStudent().getId());
        Page<StudentsTaskResponseForViewAll> result = studentsTaskMapper.toResponseForViewList(studentsTaskRepository.findAll(specification, pageable));
        log.info("StudentTaskServiceImpl getAll finish");
        return result;
    }

    @Override
    public Page<Map<String, String>> getAllForTaskForSelect(ForSelect2Dto forSelect2Dto) {
        log.info("StudentTaskServiceImpl getAllForTaskForSelect start");
        Pageable pageable = PageRequest.of(forSelect2Dto.getPage(), forSelect2Dto.getSize(), Sort.by(Sort.Order.desc("id")));
        Page<Task> tasks = studentsTaskRepository.findAllTaskByTaskNameLikeAndStudentId(forSelect2Dto.getQuery(), studentService.getAuthStudent().getId(), pageable);
        List<Map<String, String>> list = new ArrayList<>();
        for (Task task : tasks.getContent()) {
            Map<String, String> map = new HashMap<>();
            map.put(task.getId().toString(), task.getName());
            list.add(map);
        }
        log.info("StudentTaskServiceImpl getAllForTaskForSelect finish");
        return new PageImpl<>(list, pageable, tasks.getTotalElements());
    }

    @Override
    public Page<Map<String, String>> getAllForCourseForSelect(ForSelect2Dto forSelect2Dto) {
        log.info("StudentTaskServiceImpl getAllForCourseForSelect start");
        Pageable pageable = PageRequest.of(forSelect2Dto.getPage(), forSelect2Dto.getSize(), Sort.by(Sort.Order.desc("id")));
        Page<Course> courses = studentsTaskRepository.findAllCourseByCourseNameLikeAndStudentId(forSelect2Dto.getQuery(), studentService.getAuthStudent().getId(), pageable);
        List<Map<String, String>> list = new ArrayList<>();
        for (Course course : courses.getContent()) {
            Map<String, String> map = new HashMap<>();
            map.put(course.getId().toString(), course.getName());
            list.add(map);
        }
        log.info("StudentTaskServiceImpl getAllForCourseForSelect finish");
        return new PageImpl<>(list, pageable, courses.getTotalElements());
    }

    @Override
    public StudentsTask getById(Long id) {
        log.info("StudentTaskServiceImpl getById start");
        StudentsTask studentsTask = studentsTaskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("StudentTask with id = "+id+" not found")
        );
        log.info("StudentTaskServiceImpl getById finish");
        return studentsTask;
    }

    @Override
    @Transactional
    public StudentsTask save(StudentsTask studentsTask) {
        log.info("StudentTaskServiceImpl save start");
        StudentsTask result = studentsTaskRepository.save(studentsTask);
        log.info("StudentTaskServiceImpl save finish");
        return result;
    }

    @Override
    @Transactional
    public void cancelWork(Long studentTaskId){
        log.info("StudentTaskServiceImpl cancelWork start");
        StudentsTask studentsTask = getById(studentTaskId);
        studentsTask.setStatus(StatusStudentsTask.IN_PROCESS);
        studentsTask.setMark(null);
        //TODO дорабити видалення з мініо
        studentsTask.setMyWork(null);
        save(studentsTask);
        log.info("StudentTaskServiceImpl cancelWork finish");
    }

    @Override
    @Transactional
    @SneakyThrows
    public void sendWork(Long id, MultipartFile file) {
        log.info("StudentTaskServiceImpl sendWork start");
        StudentsTask studentsTask = getById(id);
        studentsTask.setStatus(StatusStudentsTask.GRANTED);
        studentsTask.setMyWork(minioService.putMultipartFile(file));
        save(studentsTask);
        log.info("StudentTaskServiceImpl sendWork finish");
    }

    @Override
    public Long countStudentMarkByCourseId(Long courseId) {
        log.info("StudentTaskServiceImpl countStudentMarkByCourseId start");
        Long count = studentsTaskRepository.countMarkByStudentIdAndCourseId(studentService.getAuthStudent().getId(), courseId);
        log.info("StudentTaskServiceImpl countStudentMarkByCourseId finish");
        return count;
    }

    @Override
    public Long countAllByCourseId(Long courseId) {
        log.info("StudentTaskServiceImpl countAllByCourseId start");
        Long count = studentsTaskRepository.countByStudentIdAndCourseId(studentService.getAuthStudent().getId(), courseId);
        log.info("StudentTaskServiceImpl countAllByCourseId finish");
        return count;
    }

    @Override
    public Long countAllDoneTaskByCourseId(Long courseId) {
        log.info("StudentTaskServiceImpl countAllDoneTaskByCourseId start");
        Long count = studentsTaskRepository.countByStatusesAndStudentIdAndCourseId(studentService.getAuthStudent().getId(), courseId, List.of(StatusStudentsTask.EVALUATED));
        log.info("StudentTaskServiceImpl countAllDoneTaskByCourseId finish");
        return count;
    }

    @Override
    public Long countAllNotDoneTaskByCourseId(Long courseId) {
        log.info("StudentTaskServiceImpl countAllNotDoneTaskByCourseId start");
        Long count = studentsTaskRepository.countByStatusesAndStudentIdAndCourseId(studentService.getAuthStudent().getId(), courseId, List.of(StatusStudentsTask.IN_PROCESS));
        log.info("StudentTaskServiceImpl countAllNotDoneTaskByCourseId finish");
        return count;
    }
}