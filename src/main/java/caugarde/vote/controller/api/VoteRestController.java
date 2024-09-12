package caugarde.vote.controller.api;

import caugarde.vote.model.dto.request.VoteRequestDTO;
import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.dto.response.VoteResponseDTO;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.SuccessMessage;
import caugarde.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote")
public class VoteRestController {

    private final VoteService voteService;

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

    // 수정 필요함.. 참조 테이블 먼저 지워야 한다.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable UUID id) {
        voteService.deleteById(id);
        return ResponseEntity.ok(new MessageResponseDTO(SuccessMessage.DELETE.getMessage()));
    }

}
