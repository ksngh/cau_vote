package caugarde.vote.common.util;

import java.time.LocalDate;

public class SemesterUtil {

    public static String getSemester(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();

        if (month >= 3 && month <= 8) {
            return year + "년 1학기";
        } else {
            if(month >8 && month <= 12){
                return year + "년 2학기";
            }
            else{
                return year-1 +"년 2학기";
            }
        }
    }
}