package ua.kpi.edutrackerstudent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.edutrackerstudent.service.StudentService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final StudentService studentService;
    @GetMapping("/login")
    public ModelAndView login() {
        if(studentService.isAuthenticated()) {return new ModelAndView("redirect:/");}
        return new ModelAndView("auth/login");
    }
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }
    @GetMapping("/checkAuth")
    public ResponseEntity<Boolean> checkAuthentication() {
        return ResponseEntity.ok(studentService.isAuthenticated());
    }
//    @PostMapping("/registration")
//    public ResponseEntity<ProfessorDtoForRegistration> registrationForm(@ModelAttribute @Valid ProfessorDtoForRegistration professor, BindingResult bindingResult) throws NoSuchMethodException, MethodArgumentNotValidException {
//        contactValidator.validate(professor, bindingResult);
//        professorValidator.validate(professor, bindingResult);
//        if (bindingResult.hasErrors()) {
//            MethodParameter methodParameter = new MethodParameter(this.getClass().getDeclaredMethod("registrationForm", ProfessorDtoForRegistration.class, BindingResult.class), 0);
//            throw new MethodArgumentNotValidException(methodParameter, bindingResult);
//        }
//        professorService.registration(professor);
//        return ResponseEntity.ok(professor);
//    }
}