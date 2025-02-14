package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResCode;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.student.StudentUpdate;
import caugarde.vote.service.v2.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api/")
public class StudentApiController {

    private final StudentService studentService;

    @PatchMapping("/student/{studentId}")
    public CustomApiResponse<Void> updateStudent(@PathVariable("studentId") Long studentId,
                                                 @RequestBody StudentUpdate.Request request){
        studentService.update(studentId, request);
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

}
