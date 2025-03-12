package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.enums.StatusLesson;
import ua.kpi.edutrackerstudent.dto.lesson.BeginningLessonResponse;
import ua.kpi.edutrackerstudent.mapper.LessonMapper;
import ua.kpi.edutrackerstudent.repository.LessonRepository;
import ua.kpi.edutrackerstudent.service.LessonService;
import ua.kpi.edutrackerstudent.service.StudentService;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final StudentService studentService;
    private final LessonMapper lessonMapper = new LessonMapper();
    @Override
    public List<BeginningLessonResponse> getAllBeginningLessons() {
        log.info("LessonServiceImpl getAllBeginningLessons start");
        List<BeginningLessonResponse> result = lessonMapper.toBeginningLessonResponseList(lessonRepository.findAllByCourseInAndStatus(studentService.getAuthStudent().getCourses(), StatusLesson.IN_PROGRESS));
        log.info("LessonServiceImpl getAllBeginningLessons finish");
        return result;
    }
}