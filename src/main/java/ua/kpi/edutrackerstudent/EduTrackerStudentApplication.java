package ua.kpi.edutrackerstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.kpi.edutrackerentity", "ua.kpi.edutrackerstudent"})
public class EduTrackerStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduTrackerStudentApplication.class, args);
    }

}
