package caugarde.vote.controller.v2.message;

import caugarde.vote.model.dto.vote.VoteCreate;
import caugarde.vote.model.dto.vote.VoteResult;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class VoteMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final StudentService studentService;
    private final VoteService voteService;

    @MessageMapping("/board/{boardId}/vote")
    public void vote(@DestinationVariable Long boardId,
                     @Payload @Valid VoteCreate.Request request,
                     Principal principal) {
        String email = principal.getName();
        Student student = studentService.getByEmail(email);
        voteService.create(request, student.getEmail());

        sendVoteResult(email);
        sendVoteCountUpdate(boardId);
    }

    @MessageMapping("/board/{boardId}/vote/cancel")
    public void cancelVote(@DestinationVariable Long boardId,
                           Principal principal) {
        String email = principal.getName();
        Vote vote = voteService.getByBoardAndStudent(boardId, email);
        voteService.delete(vote);

        sendCancelResult(email);
        sendVoteCountUpdate(boardId);
    }


    private void sendVoteResult(String email) {
        messagingTemplate.convertAndSendToUser(email, "/topic/vote/result", getVoteResultMessage());
    }

    private void sendCancelResult(String email) {
        messagingTemplate.convertAndSendToUser(email, "/topic/vote/result", getCancelResultMessage());
    }

    private void sendVoteCountUpdate(Long boardId) {
        messagingTemplate.convertAndSend("/topic/vote/count", getVoteCount(boardId));
    }

    private VoteResult.SuccessMessage getVoteResultMessage() {
        return VoteResult.SuccessMessage.vote();
    }

    private VoteResult.SuccessMessage getCancelResultMessage() {
        return VoteResult.SuccessMessage.cancel();
    }

    private VoteResult.Count getVoteCount(Long boardId) {
        return VoteResult.Count.of(voteService.getVoteCount(boardId));
    }


}
