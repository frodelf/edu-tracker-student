package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.enums.StatusCourse;
import ua.kpi.edutrackerstudent.dto.course.CourseResponseViewAll;
import ua.kpi.edutrackerstudent.mapper.CourseMapper;
import ua.kpi.edutrackerstudent.repository.CourseRepository;
import ua.kpi.edutrackerstudent.service.CourseService;
import ua.kpi.edutrackerstudent.service.MinioService;
import ua.kpi.edutrackerstudent.service.StudentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final MinioService minioService;
    private final CourseMapper courseMapper = new CourseMapper();
    @Override
    public Page<CourseResponseViewAll> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return courseMapper.toDtoListForView(courseRepository.findAllByStudentsAndStatusCourse(List.of(studentService.getAuthStudentForGlobal()), StatusCourse.ACTIVE, pageable), minioService);
    }
}
