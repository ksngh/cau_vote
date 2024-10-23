package caugarde.vote.controller.api;

import caugarde.vote.model.dto.response.RankingResponseDTO;
import caugarde.vote.model.entity.Ranking;
import caugarde.vote.service.RankingService;
import caugarde.vote.service.redis.RankingRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingRestController {

    private final RankingRedisService rankingRedisService;
    private final RankingService rankingService;

    @GetMapping("/")
    public ResponseEntity<?> getRankings(){
        List<Ranking> rankings =rankingRedisService.getWholeRankings();
        List<RankingResponseDTO> rankingResponseDTOS = rankingService.rankingsToDTOs(rankings);
        Map<String, List<RankingResponseDTO>> rankingResponseDTOMap = rankingService.groupAndSortBySemester(rankingResponseDTOS);
        return ResponseEntity.ok(rankingResponseDTOMap);
    }

    @GetMapping("/first")
    public ResponseEntity<?> getRankingsFirst(){
//        rankingRedisService.deleteAllByKey(rankingRedisService.getCurrentKey());
//        rankingRedisService.generateRankings();
        List<RankingResponseDTO> rankingResponseDTOS = rankingService.rankingsToDTOs(rankingRedisService.getFirstGrade());
        return ResponseEntity.ok(rankingResponseDTOS);
    }
}
