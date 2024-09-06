package ua.kpi.edutrackerstudent.dto.student;

import lombok.Data;

@Data
public class StudentResponseForPersonalData {
    private Long id;
    private String lastName;
    private String name;
    private String middleName;
    private String groupName;
    private String phone;
    private String telegram;
    private String email;
    private String image;
}