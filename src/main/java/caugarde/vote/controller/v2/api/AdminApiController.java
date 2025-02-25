package caugarde.vote.controller.v2.api;


import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.student.StudentDetails;
import caugarde.vote.model.dto.student.StudentDetailsUpdate;
import caugarde.vote.model.dto.student.StudentInfo;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.v2.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/api/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final StudentService studentService;

    @PatchMapping("/student/{studentId}")
    public CustomApiResponse<Void> updateStudent(@PathVariable Long studentId,
                                                 @RequestBody StudentDetailsUpdate.Request request) {
        studentService.adminUpdate(studentId, request);
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

    @PatchMapping("/student/{studentId}/late-fee")
    public CustomApiResponse<Void> paidLateFee(@PathVariable Long studentId) {
        studentService.paidLateFee(studentId);
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

    @GetMapping("/student")
    public CustomApiResponse<Slice<StudentInfo.Response>> getStudents(@RequestParam(required = false) Long cursorId,
                                                                      @RequestParam(defaultValue = "15") int size){
        Slice<StudentInfo.Response> response = studentService.pageStudents(cursorId, size);
        return CustomApiResponse.OK(ResSuccessCode.READ,response);
    }

    @GetMapping("/student/{studentId}")
    public CustomApiResponse<StudentDetails.Response> getStudentDetails(@PathVariable Long studentId) {
        Student student = studentService.getById(studentId);
        return CustomApiResponse.OK(ResSuccessCode.READ,StudentDetails.Response.of(student));
    }

    @DeleteMapping("/student/{studentId}")
    public CustomApiResponse<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return CustomApiResponse.OK(ResSuccessCode.DELETED);
    }
}
