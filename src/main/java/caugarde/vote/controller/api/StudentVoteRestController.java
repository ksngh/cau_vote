package caugarde.vote.controller.api;

import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.dto.response.StudentVoteCountResponseDTO;
import caugarde.vote.model.dto.response.StudentVoteResponseDTO;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.SuccessMessage;
import caugarde.vote.service.StudentVoteService;
import caugarde.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student/vote")
public class StudentVoteRestController {

    private final StudentVoteService studentVoteService;
    private final VoteService voteService;

    @GetMapping("/{id}")
    public ResponseEntity<List<StudentVoteResponseDTO>> getStudentsByVoteId(@PathVariable UUID id) {
        List<StudentVoteResponseDTO> studentVoteResponseDTOS = studentVoteService.getStudentsByVoteId(id);
        return ResponseEntity.ok().body(studentVoteResponseDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        studentVoteService.deleteByVote(id);
        return ResponseEntity.ok().body(new MessageResponseDTO("StudentVote" + SuccessMessage.DELETE.getMessage()));
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<?> count(@PathVariable UUID id) {
        Vote vote = voteService.findById(id);
        StudentVoteCountResponseDTO studentVoteCountResponseDTO = new StudentVoteCountResponseDTO(studentVoteService.countByVote(vote));
        return ResponseEntity.ok().body(studentVoteCountResponseDTO);
    }

}
