package caugarde.vote.service;

import caugarde.vote.model.entity.Semester;
import caugarde.vote.repository.jpa.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public Semester save(Semester semester) {
        if (semesterRepository.findBySemester(semester.getSemester()) == null) {

            return semesterRepository.save(semester);
        }else {
            return semesterRepository.findBySemester(semester.getSemester());
        }
    }

    public Semester getBySemester(String semester) {
        return semesterRepository.findBySemester(semester);
    }
}
