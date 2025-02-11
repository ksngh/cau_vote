package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.model.dto.vote.VoteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class VoteApiController {

    @GetMapping("/board/{boardId}/vote")
    public CustomApiResponse<List<VoteInfo.Response>> getVoteInfo(@PathVariable Long boardId){
        return null;
    }

}
