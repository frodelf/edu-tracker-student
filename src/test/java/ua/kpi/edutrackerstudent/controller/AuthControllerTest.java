package ua.kpi.edutrackerstudent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.edutrackerstudent.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login() {
        when(studentService.isAuthenticated()).thenReturn(true);

        ModelAndView result = authController.login();

        assertThat(result.getViewName()).isEqualTo("redirect:/");
    }

    @Test
    void login_whenNotAuthenticated() {
        when(studentService.isAuthenticated()).thenReturn(false);

        ModelAndView result = authController.login();

        assertThat(result.getViewName()).isEqualTo("auth/login");
    }

    @Test
    void logout() {
        String result = authController.logoutPage(mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        assertThat(result).isEqualTo("redirect:/login");
    }

    @Test
    void checkAuthentication() {
        when(studentService.isAuthenticated()).thenReturn(true);

        ResponseEntity<Boolean> result = authController.checkAuthentication();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(true);
    }

    @Test
    void checkAuthentication_whenNotAuthenticated() {
        when(studentService.isAuthenticated()).thenReturn(false);

        ResponseEntity<Boolean> result = authController.checkAuthentication();

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(false);
    }
}
