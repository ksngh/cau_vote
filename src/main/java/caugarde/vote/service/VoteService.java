package caugarde.vote.service;

import caugarde.vote.model.dto.request.VoteRequestDTO;
import caugarde.vote.model.dto.response.VoteResponseDTO;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.repository.StudentRepository;
import caugarde.vote.repository.StudentVoteRepository;
import caugarde.vote.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final StudentRepository studentRepository;
    private final StudentVoteRepository studentVoteRepository;

    public void save(VoteRequestDTO voteRequestDTO) {

        Vote vote = Vote.builder()
                .votePk(UUID.randomUUID())
                .title(voteRequestDTO.getTitle())
                .content(voteRequestDTO.getContent())
                .limitPeople(voteRequestDTO.getLimitPeople())
                .startDate(voteRequestDTO.getStartDate())
                .submitDate(voteRequestDTO.getSubmitDate())
                .build();

        voteRepository.save(vote);
    }



    public VoteResponseDTO getVote(UUID id){
        final Vote vote = voteRepository.findById(id).orElse(null);
        VoteResponseDTO voteResponseDTO = VoteResponseDTO.builder()
                .title(vote.getTitle())
                .content(vote.getContent())
                .limitPeople(vote.getLimitPeople())
                .startDate(vote.getStartDate())
                .submitDate(vote.getSubmitDate())
                .build();
        return voteResponseDTO;
    }

    public void setVote(UUID id, VoteRequestDTO voteRequestDTO) {

        Vote vote = Vote.builder()
                .votePk(id)
                .title(voteRequestDTO.getTitle())
                .content(voteRequestDTO.getContent())
                .limitPeople(voteRequestDTO.getLimitPeople())
                .startDate(voteRequestDTO.getStartDate())
                .submitDate(voteRequestDTO.getSubmitDate())
                .build();

        voteRepository.save(vote);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public List<VoteResponseDTO> votesToDTO(List<Vote> votes) {
        return votes.stream().map(VoteResponseDTO::new).toList();
    }

    public Vote findById(UUID id) {
        return voteRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        voteRepository.deleteById(id);
    }

    public List<VoteResponseDTO> getVotesByStudentVotes(List<StudentVote> studentVotes){
        List<Vote> votes = voteRepository.findAllByStudentVotes(studentVotes);
        return votesToDTO(votes);
    }
}
