package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.board.BoardUpdate;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.service.v2.interfaces.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/board")
    public CustomApiResponse<Void> createBoard(@RequestBody @Valid BoardCreate.Request request) {
        boardService.create(request);
        return CustomApiResponse.OK(ResSuccessCode.CREATED);
    }

    @GetMapping("/board")
    public CustomApiResponse<List<BoardInfo.Response>> getBoard(@RequestParam String boardStatus) {
        Set<BoardStatus> statusSet = parseStatuses(boardStatus);
        List<BoardInfo.Response> response = boardService.search(statusSet);
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

    private Set<BoardStatus> parseStatuses(String status) {
        if ("ALL".equalsIgnoreCase(status)) {
            return Set.of(BoardStatus.ACTIVE, BoardStatus.PENDING, BoardStatus.INACTIVE);
        }
        return Arrays.stream(status.split(","))
                .map(String::toUpperCase)
                .map(BoardStatus::valueOf)
                .collect(Collectors.toSet());
    }

}
