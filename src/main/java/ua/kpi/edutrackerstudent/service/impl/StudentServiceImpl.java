package ua.kpi.edutrackerstudent.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerstudent.dto.student.StudentRequestForPersonalData;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.mapper.StudentMapper;
import ua.kpi.edutrackerstudent.repository.StudentRepository;
import ua.kpi.edutrackerstudent.service.MinioService;
import ua.kpi.edutrackerstudent.service.StudentService;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final MinioService minioService;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = new StudentMapper();
    @Override
    public Student getAuthStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            String currentUserName = authentication.getName();
            return getByEmail(currentUserName);
        }
        else return null;
    }

    @Override
    public StudentResponseForPersonalData getAuthStudentForGlobal() {
        return studentMapper.toResponseForPersonalData(getAuthStudent(), minioService);
    }
    @Override
    public Student getByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Student with email = "+email+" not found")
        );
    }

    @Override
    public Student getByEmailForAuth(String username) {
        return studentRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Credential isn't correct")
        );
    }
    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return !(authentication instanceof AnonymousAuthenticationToken);
    }
    @Override
    @SneakyThrows
    @Transactional
    public void updatePersonalData(StudentRequestForPersonalData studentRequestForPersonalData) {
        Student student = studentMapper.toEntity(studentRequestForPersonalData, this);
        if(nonNull(studentRequestForPersonalData.getImage())){
            student.setImage(minioService.putMultipartFile(studentRequestForPersonalData.getImage()));
        }
        save(student);
    }

    @Override
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student with id = "+id+" not found")
        );
    }
}
