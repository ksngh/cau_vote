package caugarde.vote.service;

import caugarde.vote.model.entity.*;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.jpa.AuthorityRepository;
import caugarde.vote.repository.jpa.StudentVoteRepository;
import caugarde.vote.repository.jpa.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentVoteServiceTest {

    @InjectMocks
    private StudentVoteService studentVoteService;

    @Mock
    private StudentVoteRepository studentVoteRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private VoteRepository voteRepository;


    private UUID voteId;
    private Student student;
    private Vote vote;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Mock 객체 초기화

        voteId = UUID.fromString("bba06759-1402-4b8d-9585-a1d1991f802c");  // 테스트용 UUID
        UUID studentId = UUID.fromString("aba06759-1402-4b8d-9585-a1d1991f802c");

        // 학생 객체 생성
        Authority authority = authorityRepository.findByRole(Role.USER).orElse(null);
        student = new Student(studentId, "20241014", "test@test.com", "수학과", "김성호", authority, "기존 회원");

        // 투표 객체 생성
        vote = new Vote(voteId, "title", "content", new Timestamp(System.currentTimeMillis() - 3600), new Timestamp(System.currentTimeMillis() + 3600), 10);

        // 카테고리 객체 생성
        category = new Category();
    }

    @Test
    void testSave_whenVoteNotFound_shouldThrowEntityNotFoundException() {
        // given: voteRepository.findById는 빈 Optional을 반환
        when(voteRepository.findById(voteId)).thenReturn(Optional.empty());

        // when & then: save 호출 시 EntityNotFoundException이 발생하는지 검증
        assertThrows(EntityNotFoundException.class, () -> studentVoteService.save(voteId, student, category));
    }

    @Test
    void testSave_whenAlreadyVoted_shouldReturnFalse() {
        // given: 투표가 이미 존재하는 상황을 설정
        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        when(studentVoteRepository.findByVoteAndStudent(vote, student)).thenReturn(Optional.of(new StudentVote()));

        // when: save 메서드 호출
        boolean result = studentVoteService.save(voteId, student, category);

        // then: false가 반환되는지 검증
        assertFalse(result);
        verify(studentVoteRepository, never()).save(any());
    }

    @Test
    void testSave_whenVoteLimitExceeded_shouldThrowIllegalArgumentException() {
        // given: 현재 투표 인원이 최대치에 도달한 상황 설정
        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        when(studentVoteRepository.findByVoteAndStudent(vote, student)).thenReturn(Optional.empty());
        when(studentVoteRepository.countByVote(vote)).thenReturn(10L);  // 현재 투표 인원 10명

        // when & then: 투표 인원이 초과되면 IllegalArgumentException이 발생하는지 검증
        assertThrows(IllegalArgumentException.class, () -> studentVoteService.save(voteId, student, category));
    }

    @Test
    void testSave_whenValid_shouldReturnTrue() {
        // given: 정상적인 상황을 설정
        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        when(studentVoteRepository.findByVoteAndStudent(vote, student)).thenReturn(Optional.empty());
        when(studentVoteRepository.countByVote(vote)).thenReturn(5L);  // 현재 투표 인원 5명

        // when: save 메서드 호출
        boolean result = studentVoteService.save(voteId, student, category);

        // then: true가 반환되는지 검증
        assertTrue(result);
        verify(studentVoteRepository, times(1)).save(any(StudentVote.class));
    }
}
