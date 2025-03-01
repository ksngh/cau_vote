package caugarde.vote.model.dto.student;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.MemberType;
import caugarde.vote.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

public class StudentDetails {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String name;
        private String email;
        private String majority;
        private Set<Role> role;
        private String universityId;
        private Integer overdueFine;
        private MemberType memberType;
        private LocalDateTime createdAt;

        private Response(Student student) {
            this.name = student.getName();
            this.email = student.getEmail();
            this.majority = student.getMajority();
            this.role =student.getAuthorities();
            this.universityId = student.getUniversityId();
            this.overdueFine= student.getOverdueFine();
            this.memberType = student.getMemberType();
            this.createdAt = student.getCreatedAt();
        }

        public static Response of(Student student) {
            return new Response(student);
        }
    }
}
