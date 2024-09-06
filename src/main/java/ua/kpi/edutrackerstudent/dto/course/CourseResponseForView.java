package ua.kpi.edutrackerstudent.dto.course;

import lombok.Data;

@Data
public class CourseResponseForView {
    private Long id;
    private String image;
    private String name;
    private String goal;
}