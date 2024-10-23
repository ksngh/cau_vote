package caugarde.vote.service;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Semester;
import caugarde.vote.repository.jpa.SemesterRepository;
import caugarde.vote.service.SemesterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SemesterServiceTest {

    @Mock
    private SemesterRepository semesterRepository; // 목 객체 생성

    @InjectMocks
    private SemesterService semesterService; // 목 객체를 주입할 서비스

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Mockito 어노테이션 초기화
    }

    @Test
    @DisplayName("학기 저장 단위 테스트")
    void save() {
        // given
        LocalDate date = LocalDate.of(2020, 1, 1);
        String str_semester = SemesterUtil.getSemester(date);
        UUID uuid = UUID.randomUUID();

        Semester semester = new Semester(uuid, str_semester);

        // when
        when(semesterRepository.save(any(Semester.class))).thenReturn(semester); // save 메서드 모킹
        when(semesterRepository.findById(uuid)).thenReturn(Optional.of(semester)); // findById 모킹

        // then
        Semester savedSemester = semesterRepository.findById(uuid).orElse(null);
        Assertions.assertEquals(str_semester, savedSemester.getSemester());
    }
}
