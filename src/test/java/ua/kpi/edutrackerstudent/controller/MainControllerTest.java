package ua.kpi.edutrackerstudent.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    private MainController mainController = new MainController();

    @Test
    void index() {
        // Виклик методу контролера
        ModelAndView result = mainController.index();

        // Перевірка результату
        assertThat(result.getViewName()).isEqualTo("main/index");
    }
}
