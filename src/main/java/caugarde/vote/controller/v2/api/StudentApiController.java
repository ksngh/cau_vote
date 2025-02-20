package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.rentalgear.RentalGearLateFee;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.dto.student.StudentUpdate;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.v2.interfaces.BoardService;
import caugarde.vote.service.v2.interfaces.GearService;
import caugarde.vote.service.v2.interfaces.RentalGearService;
import caugarde.vote.service.v2.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class StudentApiController {

    private final StudentService studentService;
    private final BoardService boardService;
    private final RentalGearService rentalGearService;

    @PatchMapping("/student")
    public CustomApiResponse<Void> initialUpdateStudent(@AuthenticationPrincipal CustomOAuthUser user,
                                                        @RequestBody StudentUpdate.Request request) {
        studentService.update(user.getName(), request);
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

    @GetMapping("/student/board")
    public CustomApiResponse<Slice<BoardInfo.Response>> getStudentBoard(@RequestParam(required = false) Long cursorId,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @AuthenticationPrincipal CustomOAuthUser user) {
        Slice<BoardInfo.Response> boardInfos = boardService.getUserPages(user.getName(), cursorId, size);
        return CustomApiResponse.OK(ResSuccessCode.READ, boardInfos);
    }

    @GetMapping("/student/gear")
    public CustomApiResponse<Slice<GearInfo.Response>> getStudentGear(@RequestParam(required = false) Long cursorId,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @AuthenticationPrincipal CustomOAuthUser user) {
        Slice<GearInfo.Response> gearInfos = rentalGearService.getUserPages(user.getName(),cursorId,size);
        return CustomApiResponse.OK(ResSuccessCode.READ,gearInfos);
    }

    @GetMapping("/student/late-fee")
    public CustomApiResponse<RentalGearLateFee.Response> getStudentLateFee(@AuthenticationPrincipal CustomOAuthUser user) {
        Student student = studentService.getByEmail(user.getName());
        RentalGearLateFee.Response response = RentalGearLateFee.Response.of(student.getOverdueFine());
        return CustomApiResponse.OK(ResSuccessCode.READ,response);
    }

    @PatchMapping("/student/gear/return-all")
    public CustomApiResponse<Void> returnAllGear(@AuthenticationPrincipal CustomOAuthUser user) {
        rentalGearService.returnAllGear(user.getName());
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

}
