package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.entity.Student;

public interface StudentService {

    Student getByEmail(String email);
}
