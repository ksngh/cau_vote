package caugarde.vote.model.dto.response;

import caugarde.vote.model.entity.Ranking;
import caugarde.vote.model.entity.Student;
import lombok.Getter;

@Getter
public class RankingResponseDTO {

    private final String studentName;
    private final String majorName;
    private final int attendance;
    private final String semester;

    public RankingResponseDTO(String studentName, String majorName, int attendance, String semester) {
        this.studentName = studentName;
        this.majorName = majorName;
        this.attendance = attendance;
        this.semester = semester;
    }

    public RankingResponseDTO(Ranking ranking) {
        this.studentName = ranking.getStudent().getName();
        this.attendance = ranking.getAttendanceCount();
        this.semester = ranking.getSemester().getSemester();
        this.majorName = ranking.getStudent().getMajority();
    }

}
