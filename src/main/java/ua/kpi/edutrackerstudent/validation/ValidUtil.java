package ua.kpi.edutrackerstudent.validation;

public class ValidUtil {
    public static boolean notNullAndBlank(String text) {
        return text != null && !text.isBlank();
    }
}