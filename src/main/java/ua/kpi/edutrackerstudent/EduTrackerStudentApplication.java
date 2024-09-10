package ua.kpi.edutrackerstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "ua.kpi.edutrackerentity.entity")
public class EduTrackerStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduTrackerStudentApplication.class, args);
    }

}
