package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerstudent.repository.ReviewRepository;
import ua.kpi.edutrackerstudent.service.ReviewService;
import ua.kpi.edutrackerstudent.service.StudentService;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final StudentService studentService;
    @Override
    public Long countAllVisitedLessonByCourseId(Long courseId) {
        log.info("ReviewServiceImpl countAllVisitedLessonByCourseId start");
        Long result = reviewRepository.countByStudentIdAndCourseId(studentService.getAuthStudent().getId(), courseId);
        log.info("ReviewServiceImpl countAllVisitedLessonByCourseId finish");
        return result;
    }
}