package ua.kpi.edutrackerstudent.mapper;

import lombok.SneakyThrows;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerstudent.dto.student.StudentRequestForPersonalData;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.service.MinioService;
import ua.kpi.edutrackerstudent.service.impl.StudentServiceImpl;

import static ua.kpi.edutrackerstudent.validation.ValidUtil.notNullAndBlank;

public class StudentMapper {
    @SneakyThrows
    public StudentResponseForPersonalData toResponseForPersonalData(Student student, MinioService minioService) {
        if (student == null) return null;
        StudentResponseForPersonalData dto = new StudentResponseForPersonalData();
        dto.setId(student.getId());
        dto.setLastName(student.getLastName());
        dto.setName(student.getName());
        dto.setMiddleName(student.getMiddleName());
        dto.setPhone(student.getPhone());
        dto.setTelegram(student.getTelegram());
        dto.setEmail(student.getEmail());
        dto.setImage(minioService.getUrl(student.getImage()));
        dto.setGroupName(student.getGroupName());
        return dto;
    }
    public Student toEntity(StudentRequestForPersonalData dto, StudentServiceImpl studentService) {
        Student student = studentService.getAuthStudent();
        if(notNullAndBlank(dto.getLastName()))student.setLastName(dto.getLastName());
        if(notNullAndBlank(dto.getName()))student.setName(dto.getName());
        if(notNullAndBlank(dto.getMiddleName()))student.setMiddleName(dto.getMiddleName());
        if(notNullAndBlank(dto.getPhone()))student.setPhone(dto.getPhone());
        if(notNullAndBlank(dto.getTelegram()))student.setTelegram(dto.getTelegram());
        if(notNullAndBlank(dto.getEmail()))student.setEmail(dto.getEmail());
        return student;
    }
}