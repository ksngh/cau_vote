package caugarde.vote.common.handler;

import caugarde.vote.model.dto.request.SocketStudentVoteRequestDTO;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.StudentService;
import caugarde.vote.service.StudentVoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class VoteWebSocketHandler extends TextWebSocketHandler {

    private final BlockingQueue<SocketStudentVoteRequestDTO> voteQueue = new LinkedBlockingQueue<SocketStudentVoteRequestDTO>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final StudentVoteService studentVoteService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StudentService studentService;

    public VoteWebSocketHandler(StudentVoteService studentVoteService, StudentService studentService) {
        this.studentVoteService = studentVoteService;
        this.studentService = studentService;
        this.executorService.submit(this::processVotes);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            session.getAttributes().put("userEmail", auth.getName()); // 인증 정보 저장
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 받은 메시지를 VoteRequestDTO로 변환
        SocketStudentVoteRequestDTO socketStudentVoteRequestDTO = objectMapper.readValue(message.getPayload(), SocketStudentVoteRequestDTO.class);
        socketStudentVoteRequestDTO.setEmailBySession((String) session.getAttributes().get("userEmail"));
        voteQueue.offer(socketStudentVoteRequestDTO); // 큐에 요청을 추가
    }

    private void processVotes() {
        while (true) {
            try {
                SocketStudentVoteRequestDTO socketStudentVoteRequestDTO = voteQueue.take(); // 큐에서 투표 요청을 하나씩 처리
                Student student = studentService.findByEmail(socketStudentVoteRequestDTO.getStudentEmail());
                studentVoteService.save(socketStudentVoteRequestDTO.getVoteId(),student); // 투표 처리 로직
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
