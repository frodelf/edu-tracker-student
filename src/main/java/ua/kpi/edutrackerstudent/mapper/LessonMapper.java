package ua.kpi.edutrackerstudent.mapper;

import ua.kpi.edutrackerentity.entity.Lesson;
import ua.kpi.edutrackerstudent.dto.lesson.BeginningLessonResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

public class LessonMapper {
    public List<BeginningLessonResponse> toBeginningLessonResponseList(List<Lesson> allByCourseInAndStatus) {
        List<BeginningLessonResponse> beginningLessonResponses = new ArrayList<>();
        for (Lesson lesson : allByCourseInAndStatus) {
            beginningLessonResponses.add(toBeginningLessonResponse(lesson));
        }
        return beginningLessonResponses;
    }

    private BeginningLessonResponse toBeginningLessonResponse(Lesson lesson) {
        BeginningLessonResponse beginningLessonResponse = new BeginningLessonResponse();
        beginningLessonResponse.setId(lesson.getId());
        beginningLessonResponse.setLink(lesson.getLink());
        if(nonNull(lesson.getCourse())){
            beginningLessonResponse.setCourse(Collections.singletonMap(lesson.getCourse().getId().toString(), lesson.getCourse().getName()));
            beginningLessonResponse.setProfessorName(lesson.getCourse().getProfessor().getLastName()+" "+lesson.getCourse().getProfessor().getName());
        }
        return beginningLessonResponse;
    }
}
