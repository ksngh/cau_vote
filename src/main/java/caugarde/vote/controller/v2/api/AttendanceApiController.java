package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.attendance.AttendanceInfo;
import caugarde.vote.service.v2.interfaces.AttendanceService;
import caugarde.vote.service.v2.interfaces.cached.StudentAttendanceCountService;
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
    private final StudentAttendanceCountService studentAttendanceCountService;

    @GetMapping("/attendance/most-active")
    public CustomApiResponse<List<AttendanceInfo.Response>> getAttendanceTop(){
        List<AttendanceInfo.Response> responses = studentAttendanceCountService.getTop1().stream().map(AttendanceInfo.Response::from).toList();
        return CustomApiResponse.OK(ResSuccessCode.READ, responses);
    }

    @GetMapping("/attendance/top-ten")
    public CustomApiResponse<List<AttendanceInfo.Response>> getAttendanceTopTen(){
        List<AttendanceInfo.Response> responses = studentAttendanceCountService.getTop10().stream().map(AttendanceInfo.Response::from).toList();
        return CustomApiResponse.OK(ResSuccessCode.READ,responses);
    }

}
