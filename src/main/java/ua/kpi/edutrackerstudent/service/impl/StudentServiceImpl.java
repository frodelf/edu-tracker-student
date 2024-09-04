package ua.kpi.edutrackerstudent.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.kpi.edutrackerentity.entity.Student;
import ua.kpi.edutrackerstudent.repository.StudentRepository;
import ua.kpi.edutrackerstudent.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    @Override
    public Student getAuthStudentForGlobal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            String currentUserName = authentication.getName();
            return getByEmail(currentUserName);
        }
        else return null;
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
}
