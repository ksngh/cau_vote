package caugarde.vote.common.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class SemesterUtilTest {

    @Test
    @DisplayName("학기")
    void getSemester() {
        //given
        LocalDate date = LocalDate.of(2020, 1, 1);

        //when
        String semester = SemesterUtil.getSemester(date);

        //then
        System.out.println(semester);
        Assertions.assertThat(semester).isEqualTo("2019년 2학기");
    }

}