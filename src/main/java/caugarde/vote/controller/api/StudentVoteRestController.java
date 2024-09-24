package caugarde.vote.controller.api;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.dto.response.StudentVoteResponseDTO;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.SuccessMessage;
import caugarde.vote.service.StudentVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student/vote")
public class StudentVoteRestController {

    private final StudentVoteService studentVoteService;

    @GetMapping("/{id}")
    public ResponseEntity<List<StudentVoteResponseDTO>> getStudentsByVoteId(@PathVariable UUID id) {
        List<Student> students = studentVoteService.getStudentsByVoteId(id);
        return ResponseEntity.ok().body(studentVoteService.studentsToDTOs(students));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> save(@PathVariable UUID id, @AuthenticationPrincipal CustomOAuthUser user) {
        if(studentVoteService.save(id,user)){
            return ResponseEntity.ok().body(new MessageResponseDTO("투표가 완료되었습니다."));
        }else{
            return ResponseEntity.ok().body(new MessageResponseDTO("이미 참석에 투표하셨습니다."));
        }
    }

    @DeleteMapping("/choice/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, @AuthenticationPrincipal CustomOAuthUser user) {
        studentVoteService.deleteByVoteAndStudent(id, user);
        return ResponseEntity.ok().body(new MessageResponseDTO("StudentVote" + SuccessMessage.DELETE.getMessage()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        studentVoteService.deleteByVote(id);
        return ResponseEntity.ok().body(new MessageResponseDTO("StudentVote" + SuccessMessage.DELETE.getMessage()));
    }

}
