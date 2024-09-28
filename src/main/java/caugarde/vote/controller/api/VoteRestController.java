package caugarde.vote.controller.api;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.dto.request.VoteRequestDTO;
import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.dto.response.VoteResponseDTO;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.SuccessMessage;
import caugarde.vote.service.StudentService;
import caugarde.vote.service.StudentVoteService;
import caugarde.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote")
public class VoteRestController {

    private final VoteService voteService;
    private final StudentVoteService studentVoteService;
    private final StudentService studentService;

    @GetMapping()
    public ResponseEntity<List<VoteResponseDTO>> getAllVotes() {
        List<Vote> votes = voteService.getAllVotes();
        List<VoteResponseDTO> voteResponseDTOS = voteService.votesToDTO(votes);
        return ResponseEntity.ok(voteResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteResponseDTO> showVote(@PathVariable UUID id) {
        VoteResponseDTO voteResponseDTO = voteService.getVote(id);
        return ResponseEntity.ok(voteResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<?> createVote(@RequestBody VoteRequestDTO voteRequestDTO) {
        voteService.save(voteRequestDTO);
        return ResponseEntity.ok(new MessageResponseDTO("vote" + SuccessMessage.CREATE.getMessage()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVote(@PathVariable UUID id, @RequestBody VoteRequestDTO voteRequestDTO) {
        voteService.setVote(id, voteRequestDTO);
        return ResponseEntity.ok(new MessageResponseDTO("vote" + SuccessMessage.UPDATE.getMessage()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable UUID id) {
        voteService.deleteById(id);
        return ResponseEntity.ok(new MessageResponseDTO(SuccessMessage.DELETE.getMessage()));
    }

    @GetMapping("/mypage")
    public ResponseEntity<List<VoteResponseDTO>> getMyVotes(@AuthenticationPrincipal CustomOAuthUser user) {
        Student student = studentService.findById(user.getId());
        List<StudentVote> studentVotes = studentVoteService.getByStudent(student);
        List<VoteResponseDTO> voteResponseDTOS = voteService.getVotesByStudentVotes(studentVotes);
        return ResponseEntity.ok(voteResponseDTOS);
    }

}
