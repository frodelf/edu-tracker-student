package ua.kpi.edutrackerstudent.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerstudent.dto.student.StudentRequestForPersonalData;
import ua.kpi.edutrackerstudent.dto.student.StudentResponseForPersonalData;
import ua.kpi.edutrackerstudent.mapper.StudentMapper;
import ua.kpi.edutrackerstudent.repository.StudentRepository;
import ua.kpi.edutrackerstudent.service.MinioService;
import ua.kpi.edutrackerstudent.service.StudentService;

import static java.util.Objects.nonNull;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final MinioService minioService;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = new StudentMapper();

    @Override
    public Student getAuthStudent() {
        log.info("StudentServiceImpl getAuthStudent start");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = null;
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            String currentUserName = authentication.getName();
            student = getByEmail(currentUserName);
        }
        log.info("StudentServiceImpl getAuthStudent finish");
        return student;
    }

    @Override
    public StudentResponseForPersonalData getAuthStudentForGlobal() {
        log.info("StudentServiceImpl getAuthStudentForGlobal start");
        StudentResponseForPersonalData studentResponse = studentMapper.toResponseForPersonalData(getAuthStudent(), minioService);
        log.info("StudentServiceImpl getAuthStudentForGlobal finish");
        return studentResponse;
    }
    @Override
    public Student getByEmail(String email) {
        log.info("StudentServiceImpl getByEmail start");
        Student student = studentRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Student with email = "+email+" not found")
        );
        log.info("StudentServiceImpl getByEmail finish");
        return student;
    }

    @Override
    public Student getByEmailForAuth(String username) {
        log.info("StudentServiceImpl getByEmailForAuth start");
        Student student = studentRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Credential isn't correct")
        );
        log.info("StudentServiceImpl getByEmailForAuth finish");
        return student;
    }
    @Override
    public boolean isAuthenticated() {
        log.info("StudentServiceImpl isAuthenticated start");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        boolean result = !(authentication instanceof AnonymousAuthenticationToken);
        log.info("StudentServiceImpl isAuthenticated finish");
        return result;
    }
    @Override
    @SneakyThrows
    @Transactional
    public void updatePersonalData(StudentRequestForPersonalData studentRequestForPersonalData) {
        log.info("StudentServiceImpl updatePersonalData start");
        Student student = studentMapper.toEntity(studentRequestForPersonalData, this);
        if(nonNull(studentRequestForPersonalData.getImage())){
            student.setImage(minioService.putMultipartFile(studentRequestForPersonalData.getImage()));
        }
        save(student);
        log.info("StudentServiceImpl updatePersonalData finish");
    }

    @Override
    @Transactional
    public Student save(Student student) {
        log.info("StudentServiceImpl save start");
        Student result = studentRepository.save(student);
        log.info("StudentServiceImpl save finish");
        return result;
    }

    @Override
    public Student getById(Long id) {
        log.info("StudentServiceImpl getById start");
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student with id = "+id+" not found")
        );
        log.info("StudentServiceImpl getById finish");
        return student;
    }

    @Override
    @Transactional
    public void changePassword(String password) {
        log.info("StudentServiceImpl changePassword start");
        Student student = getAuthStudent();
        student.setPassword(new BCryptPasswordEncoder().encode(password));
        save(student);
        log.info("StudentServiceImpl changePassword finish");
    }
}
