package ua.kpi.edutrackerstudent.dto.student;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import ua.kpi.edutrackerstudent.dto.ContactDataDto;
import ua.kpi.edutrackerstudent.validation.annotation.ImageExtension;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentRequestForPersonalData extends ContactDataDto {
    @Size(max = 100, message = "{error.field.size.max}")
    private String middleName;
    @Size(max = 100, message = "{error.field.size.max}")
    private String name;
    @Size(max = 100, message = "{error.field.size.max}")
    private String lastName;
    @ImageExtension
    private MultipartFile image;
}