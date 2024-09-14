package caugarde.vote.service;

import caugarde.vote.model.dto.response.VoteResponseDTO;
import caugarde.vote.model.entity.Vote;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    @Transactional
    void getAllVotes() {
        List<Vote> allVotes = voteService.getAllVotes();
        System.out.println(allVotes.toString());
    }

    @Test
    @Transactional
    void votesToDTO() {
        List<Vote> allVotes = voteService.getAllVotes();
        List<VoteResponseDTO> voteResponseDTOS = voteService.votesToDTO(allVotes);
        System.out.println(voteResponseDTOS.toString());
    }

}