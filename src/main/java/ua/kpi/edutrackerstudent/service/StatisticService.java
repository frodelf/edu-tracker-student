package ua.kpi.edutrackerstudent.service;

import java.util.Map;

public interface StatisticService {
    Map<String, String> statisticForCourse(Long id);
}
