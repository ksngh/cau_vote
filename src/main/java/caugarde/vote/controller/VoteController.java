package caugarde.vote.controller;

import caugarde.vote.service.StudentVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoteController {

    private final StudentVoteService studentVoteService;

//    @MessageMapping("/student/vote/{id}") // 클라이언트가 "/app/vote"로 메시지 전송
//    @SendTo("/topic/attendance") // 브로커가 "/topic/attendance"로 메시지 브로드캐스트
//    public int processVote(@PathVariable UUID uuid) {
//        SecurityContextHolder.getContext().getAuthentication().getName();
//        studentVoteService.countByVote()
//        int updatedAttendance = updateAttendance(request); // 투표 요청 처리 로직
//        return updatedAttendance;
//    }

    @GetMapping("/mypage")
    public String myVotes(){
        return "mypage";
    }
}
