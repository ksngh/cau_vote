package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.attendance.AttendanceInfo;
import caugarde.vote.service.v2.interfaces.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    @GetMapping("/attendance/most-active")
    public CustomApiResponse<List<AttendanceInfo.Response>> getAttendanceTop(){
        List<AttendanceInfo.Response> responses = attendanceService.getTopParticipants();
        return CustomApiResponse.OK(ResSuccessCode.READ, responses);
    }

    @GetMapping("/attendance/top-ten")
    public CustomApiResponse<AttendanceInfo.Response> getAttendanceTopTen(){

    }

}
