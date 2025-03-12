package ua.kpi.edutrackerstudent.service;

import ua.kpi.edutrackerentity.entity.Literature;

import java.util.List;

public interface LiteratureService {
    List<Literature> getLiteratureByCourseId(Long courseId);
}