package ua.kpi.edutrackerstudent.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Literature;
import ua.kpi.edutrackerstudent.repository.LiteratureRepository;
import ua.kpi.edutrackerstudent.service.LiteratureService;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class LiteratureServiceImpl implements LiteratureService {
    private final LiteratureRepository literatureRepository;

    @Override
    public List<Literature> getLiteratureByCourseId(Long courseId) {
        log.info("LiteratureServiceImpl getLiteratureByCourseId start");
        List<Literature> result = literatureRepository.findAllByCourseId(courseId);
        log.info("LiteratureServiceImpl getLiteratureByCourseId finish");
        return result;
    }
}