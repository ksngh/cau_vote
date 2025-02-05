package caugarde.vote.controller.api.v2;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.service.v2.interfaces.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
