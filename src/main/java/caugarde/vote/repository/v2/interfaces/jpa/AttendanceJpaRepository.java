package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {
}
