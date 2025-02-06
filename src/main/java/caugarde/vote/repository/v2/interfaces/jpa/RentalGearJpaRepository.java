package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalGearJpaRepository extends JpaRepository<RentalGear, Long> {

    Optional<RentalGear> findByGearAndStudent(Gear gear, Student student);

}
