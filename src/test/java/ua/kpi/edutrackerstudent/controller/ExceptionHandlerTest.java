package ua.kpi.edutrackerstudent.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExceptionHandlerTest {

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();

    @Test
    void handleMethodArgumentNotValid() {
        BindingResult bindingResult = mock(BindingResult.class);

        org.springframework.validation.FieldError fieldError = mock(org.springframework.validation.FieldError.class);
        when(fieldError.getField()).thenReturn("fieldName");
        when(fieldError.getDefaultMessage()).thenReturn("Field is invalid");

        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleMethodArgumentNotValid(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Field is invalid", Objects.requireNonNull(response.getBody()).get("fieldName"));
    }

    @Test
    void handleAccessDeniedException() {
        AccessDeniedException exception = new AccessDeniedException("Access Denied");

        ModelAndView result = exceptionHandler.handleAccessDeniedException(exception);

        assertThat(result.getViewName()).isEqualTo("error/access_denied");
        assertThat(result.getModel().get("message")).isEqualTo("Access Denied");
    }

    @Test
    void handleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity Not Found");

        ModelAndView result = exceptionHandler.handleEntityNotFoundException(exception);

        assertThat(result.getViewName()).isEqualTo("error/not_found");
        assertThat(result.getModel().get("message")).isEqualTo("Entity Not Found");
    }
}
