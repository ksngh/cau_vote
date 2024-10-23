package caugarde.vote.controller;

import caugarde.vote.model.dto.request.StudentVoteRequestDTO;
import caugarde.vote.model.entity.Category;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.CategoryService;
import caugarde.vote.service.StudentService;
import caugarde.vote.service.StudentVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class StudentVoteController {

    private final StudentVoteService studentVoteService;
    private final StudentService studentService;
    private final CategoryService categoryService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/vote/{id}")
    public void processVote(@DestinationVariable UUID id,
                            @RequestBody StudentVoteRequestDTO.Create studentVoteRequestDTO,
                            Principal principal) {

        Student student = studentService.findByEmail(principal.getName());
        Category category = categoryService.getCategoryByString(studentVoteRequestDTO.category());

        studentVoteService.processVote(id, student, category,
                resultMessageDTO -> messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/vote/result", resultMessageDTO),
                voteCountDTO -> messagingTemplate.convertAndSend("/topic/vote/count", voteCountDTO)
        );

    }

    @MessageMapping("/vote/cancel/{id}")
    public void cancelVote(@DestinationVariable UUID id,
                           Principal principal) {
        Student student = studentService.findByEmail(principal.getName());

        studentVoteService.cancelVote(id, student,
                resultMessageDTO -> messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/vote/result", resultMessageDTO),
                voteCountDTO -> messagingTemplate.convertAndSend("/topic/vote/count", voteCountDTO)
        );
    }
}
