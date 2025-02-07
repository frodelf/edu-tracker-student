package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Literature;
import ua.kpi.edutrackerstudent.repository.LiteratureRepository;
import ua.kpi.edutrackerstudent.service.LiteratureService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiteratureServiceImpl implements LiteratureService {
    private final LiteratureRepository literatureRepository;

    @Override
    public List<Literature> getLiteratureByCourseId(Long courseId) {
        return literatureRepository.findAllByCourseId(courseId);
    }
}