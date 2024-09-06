package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerstudent.repository.ReviewRepository;
import ua.kpi.edutrackerstudent.service.ReviewService;
import ua.kpi.edutrackerstudent.service.StudentService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final StudentService studentService;
    @Override
    public Long countAllVisitedLessonByCourseId(Long courseId) {
        return reviewRepository.countByStudentIdAndCourseId(studentService.getAuthStudentForGlobal().getId(), courseId);
    }
}