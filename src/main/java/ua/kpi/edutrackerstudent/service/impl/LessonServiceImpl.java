package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.enums.StatusLesson;
import ua.kpi.edutrackerstudent.dto.lesson.BeginningLessonResponse;
import ua.kpi.edutrackerstudent.mapper.LessonMapper;
import ua.kpi.edutrackerstudent.repository.LessonRepository;
import ua.kpi.edutrackerstudent.service.LessonService;
import ua.kpi.edutrackerstudent.service.StudentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final StudentService studentService;
    private final LessonMapper lessonMapper = new LessonMapper();
    @Override
    public List<BeginningLessonResponse> getAllBeginningLessons() {
        return lessonMapper.toBeginningLessonResponseList(lessonRepository.findAllByCourseInAndStatus(studentService.getAuthStudentForGlobal().getCourses(), StatusLesson.IN_PROGRESS));
    }
}