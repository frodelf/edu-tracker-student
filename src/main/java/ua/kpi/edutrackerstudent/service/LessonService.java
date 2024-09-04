package ua.kpi.edutrackerstudent.service;

import ua.kpi.edutrackerstudent.dto.lesson.BeginningLessonResponse;

import java.util.List;

public interface LessonService {
    List<BeginningLessonResponse> getAllBeginningLessons();
}