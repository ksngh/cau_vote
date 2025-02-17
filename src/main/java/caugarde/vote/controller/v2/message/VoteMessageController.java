package caugarde.vote.controller.v2.message;

import caugarde.vote.model.dto.vote.VoteCreate;
import caugarde.vote.model.dto.vote.VoteResult;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.service.v2.interfaces.BoardService;
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
    private final BoardService boardService;
    private final VoteService voteService;

    @MessageMapping("/board/{boardId}/vote")
    public void vote(@DestinationVariable Long boardId,
                     @Payload @Valid VoteCreate.Request request,
                     Principal principal) {
        String email = principal.getName();
        voteService.create(boardId, request, email);
        messagingTemplate.convertAndSendToUser(email, "/topic/vote/result", VoteResult.SuccessMessage.vote());
        sendVoteCountUpdate(boardId);
    }

    @MessageMapping("/board/{boardId}/vote/cancel")
    public void cancelVote(@DestinationVariable Long boardId,
                           Principal principal) {
        String email = principal.getName();
        Vote vote = voteService.getByBoardAndStudent(boardId, email);
        voteService.delete(vote);
        messagingTemplate.convertAndSendToUser(email, "/topic/vote/result", VoteResult.SuccessMessage.cancel());
        sendVoteCountUpdate(boardId);
    }

    private void sendVoteCountUpdate(Long boardId) {
        Board board = boardService.getById(boardId);
        messagingTemplate.convertAndSend("/topic/vote/count", VoteResult.Count.of(boardId, voteService.getVoteCount(boardId), board.getLimitPeople()));
    }

}
