package caugarde.vote.model.constant;

import caugarde.vote.model.entity.Category;
import caugarde.vote.model.entity.Student;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoteTask {

    private UUID votePk;
    private Student student;
    private Category category;

    public VoteTask(UUID votePk, Student student, Category category) {
        this.votePk = votePk;
        this.student = student;
        this.category = category;
    }

    public VoteTask(UUID votePk, Student student) {
        this.votePk = votePk;
        this.student = student;
    }
}
