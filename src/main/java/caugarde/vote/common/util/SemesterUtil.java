package caugarde.vote.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class SemesterUtil {

    public static String getSemester(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();

        if (month >= 3 && month <= 8) {
            return year + "년도 1학기";
        } else {
            if(month >8){
                return year + "년도 2학기";
            }
            else{
                return year-1 +"년도 2학기";
            }
        }
    }

    public static LocalDateTime getSemesterStartDate(LocalDateTime now) {
        int year = now.getYear();
        Month month = now.getMonth();

        int startYear = year;
        int startMonth = (month.getValue() >= 3 && month.getValue() <= 8) ? 3 : 9;

        if (month.getValue() <= 2) {
            startYear = year - 1;
        }

        return LocalDateTime.of(startYear, startMonth, 1, 0, 0, 0);
    }

    public static int compare(String semester1, String semester2) {
        String[] parts1 = semester1.split("년도 ");
        String[] parts2 = semester2.split("년도 ");

        int year1 = Integer.parseInt(parts1[0]);
        int year2 = Integer.parseInt(parts2[0]);

        // 학기 부분 ("1학기" 또는 "2학기")에서 숫자 추출
        int term1 = Integer.parseInt(parts1[1].substring(0, 1));
        int term2 = Integer.parseInt(parts2[1].substring(0, 1));

        // 연도 기준으로 먼저 비교, 같으면 학기(1, 2학기)로 비교
        if (year1 != year2) {
            return Integer.compare(year1, year2);
        } else {
            return Integer.compare(term1, term2);
        }
    }
}