package caugarde.vote.controller.api;

//import caugarde.vote.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingRestController {

//    private final RankingService rankingService;

    @GetMapping("/")
    public ResponseEntity<?> getRankings(){

        return ResponseEntity.ok("");
    }
}
