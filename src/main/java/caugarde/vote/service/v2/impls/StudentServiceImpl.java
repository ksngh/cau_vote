package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.api.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.dto.student.StudentUpdate;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.v2.interfaces.StudentRepository;
import caugarde.vote.service.v2.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student getByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() -> new CustomApiException(ResErrorCode.BAD_REQUEST, "해당하는 이메일을 찾을 수 없습니다."));
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(()->new CustomApiException(ResErrorCode.NOT_FOUND,"해당하는 유저를 찾을 수 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Long studentId, StudentUpdate.Request request) {
        Student student = getById(studentId);
        student.updateInitialInfo(request);
    }

}
