package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.board.BoardCount;
import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.board.BoardUpdate;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.service.v2.interfaces.BoardService;
import caugarde.vote.service.v2.interfaces.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class BoardApiController {

    private final BoardService boardService;
    private final VoteService voteService;

    @PostMapping("/board")
    public CustomApiResponse<Void> createBoard(@RequestBody @Valid BoardCreate.Request request,
                                               @AuthenticationPrincipal CustomOAuthUser user) {
        boardService.create(request,user);
        return CustomApiResponse.OK(ResSuccessCode.CREATED);
    }

    @GetMapping("/board")
    public CustomApiResponse<Slice<BoardInfo.Response>> getBoard(@RequestParam(required = false) Long cursorId,
                                                                @RequestParam(defaultValue = "10") int size) {

        Slice<BoardInfo.Response> response = boardService.getPages(cursorId,size);
        return CustomApiResponse.OK(ResSuccessCode.READ, response);
    }

    @PatchMapping("/board/{boardId}")
    public CustomApiResponse<Void> updateBoard(@RequestBody @Valid BoardUpdate.Request request,
                                               @PathVariable Long boardId,
                                               @AuthenticationPrincipal CustomOAuthUser user) {
        boardService.update(request,boardId,user.getName());
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

    @DeleteMapping("/board/{boardId}")
    public CustomApiResponse<Void> deleteBoard(@PathVariable Long boardId,
                                               @AuthenticationPrincipal CustomOAuthUser user) {
        boardService.delete(boardId,user.getName());
        return CustomApiResponse.OK(ResSuccessCode.DELETED);
    }

    @GetMapping("/board/{boardId}/count")
    public CustomApiResponse<BoardCount.Response> getBoardCount(@PathVariable Long boardId) {
        BoardCount.Response response = BoardCount.Response.of(voteService.getVoteCount(boardId));
        return CustomApiResponse.OK(ResSuccessCode.READ,response);
    }

    @GetMapping("/board/{boardId}")
    public CustomApiResponse<BoardInfo.Response> getBoardInfo(@PathVariable Long boardId) {
        BoardInfo.Response response = BoardInfo.Response.from(boardService.getById(boardId));
        return CustomApiResponse.OK(ResSuccessCode.READ,response);
    }


}
