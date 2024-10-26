package caugarde.vote.service;

import caugarde.vote.model.constant.VoteTask;
import caugarde.vote.model.dto.response.StudentVoteResponseDTO;
import caugarde.vote.model.entity.Category;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.repository.jpa.StudentVoteRepository;
import caugarde.vote.repository.jpa.VoteRepository;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentVoteService {

    private final StudentVoteRepository studentVoteRepository;
    private final VoteRepository voteRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final ConcurrentLinkedQueue<VoteTask> voteSaveQueue = new ConcurrentLinkedQueue<>();

    private final ConcurrentHashMap<UUID, VoteTask> cancelTaskMap = new ConcurrentHashMap<>();

    // 투표 요청 처리 메서드
    public void processVote(UUID voteId, Student student, Category category, Consumer<String> messageHandler, Consumer<StudentVoteResponseDTO.CreateAttendanceNumber> countHandler) {

        voteSaveQueue.add(new VoteTask(voteId, student,category));

        // 비동기적으로 큐에서 작업을 처리
        executorService.submit(() -> processVoteQueue(messageHandler, countHandler));

    }

    private void processVoteQueue(Consumer<String> messageHandler, Consumer<StudentVoteResponseDTO.CreateAttendanceNumber> countHandler) {

        VoteTask task = voteSaveQueue.poll();

        try {
            // 예외가 발생할 수 있는 부분
            boolean isSaved = save(task.getVotePk(), task.getStudent(), task.getCategory());

            // 메시지와 카운트 업데이트
            StudentVoteResponseDTO.CreateMessage resultMessageDTO = new StudentVoteResponseDTO.CreateMessage(isSaved ? "투표가 완료되었습니다." : "이미 투표하셨습니다.");
            messageHandler.accept(resultMessageDTO.message());

            StudentVoteResponseDTO.CreateAttendanceNumber voteCountDTO = new StudentVoteResponseDTO.CreateAttendanceNumber(
                    task.getVotePk(), countAllByVotePk(task.getVotePk()), voteRepository.findById(task.getVotePk()).get().getLimitPeople()
            );
            countHandler.accept(voteCountDTO);

        } catch (IllegalArgumentException e) {
            // IllegalArgumentException 발생 시 클라이언트로 전달
            messageHandler.accept("투표 인원이 초과되었습니다.");
        }

    }


    public void cancelVote(UUID voteId, Student student, Consumer<String> messageHandler, Consumer<StudentVoteResponseDTO.CreateAttendanceNumber> countHandler) {
        // 작업을 Map에 추가

        UUID studentVoteId = studentVoteRepository.findByVoteAndStudent(voteRepository.findById(voteId).get(),student).get().getStudentVotePk();
        cancelTaskMap.put(studentVoteId, new VoteTask(voteId, student));

        // 비동기적으로 Map에서 작업을 처리
        executorService.submit(() -> cancelVoteTasks(messageHandler, countHandler));
    }

    private void cancelVoteTasks(Consumer<String> messageHandler, Consumer<StudentVoteResponseDTO.CreateAttendanceNumber> countHandler) {
        // 맵에서 작업을 처리할 수 있을 때까지 반복
        List<UUID> tasksToProcess = new ArrayList<>(cancelTaskMap.keySet());

        for (UUID studentVotePk : tasksToProcess) {
            VoteTask task = cancelTaskMap.get(studentVotePk);
            if (task != null) {
                // 처리할 작업 수행
                Boolean isDeleted = deleteById(studentVotePk);

                // 메시지와 카운트 업데이트
                StudentVoteResponseDTO.DeleteMessage resultMessageDTO = new StudentVoteResponseDTO.DeleteMessage(isDeleted ? "투표가 취소되었습니다." : "투표 내역이 없습니다.");
                messageHandler.accept(resultMessageDTO.message());

                StudentVoteResponseDTO.CreateAttendanceNumber voteCountDTO = new StudentVoteResponseDTO.CreateAttendanceNumber(
                        task.getVotePk(), countAllByVotePk(task.getVotePk()), voteRepository.findById(task.getVotePk()).get().getLimitPeople()
                );
                countHandler.accept(voteCountDTO);

                // 처리된 작업을 맵에서 제거
                cancelTaskMap.remove(studentVotePk);
            }
        }
    }


    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }

    public Boolean save(UUID id, Student student,Category category) {

        Vote vote = voteRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(studentVoteRepository.findByVoteAndStudent(vote, student).isPresent()) {
            return false;
        }

        else if (vote.getLimitPeople() == countByVote(vote)){
            throw new IllegalArgumentException("투표 인원이 초과되었습니다.");
        }

        else{
            StudentVote studentVote = StudentVote.builder()
                    .studentVotePk(UUID.randomUUID())
                    .student(student)
                    .vote(vote)
                    .category(category)
                    .build();

            studentVoteRepository.save(studentVote);
            return true;
        }
    }


    public List<StudentVoteResponseDTO> getStudentsByVoteId(UUID id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vote not found with id: " + id));

        return studentVoteRepository.findByVoteOrderByCreatedAtAsc(vote)
                .stream()
                .map(StudentVoteResponseDTO::new)
                .collect(Collectors.toList());
    }

    public boolean deleteById(UUID id) {
        if (studentVoteRepository.findById(id).isPresent()){
            studentVoteRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

    public void deleteByVote(UUID id) {
        Vote vote = voteRepository.findById(id).orElse(null);
        studentVoteRepository.deleteByVote(vote);
    }

    public List<StudentVote> getByStudent(Student student) {
        return studentVoteRepository.findByStudent(student);
    }

    public int countByVote(Vote vote){
        return (int) studentVoteRepository.countByVote(vote);
    }

    public int countAllByVotePk(UUID id){
        return countByVote(voteRepository.findById(id).get());
    }

}
