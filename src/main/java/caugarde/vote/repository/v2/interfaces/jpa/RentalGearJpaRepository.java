package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalGearJpaRepository extends JpaRepository<RentalGear, Long> {

    Optional<RentalGear> findByGearAndStudentAndReturnedAtIsNull(Gear gear, Student student);

    Optional<RentalGear> findByGearAndReturnedAtIsNull(Gear gear);

    List<RentalGear> findByStudentAndLateFeeGreaterThan(Student student,int lateFee);

    List<RentalGear> findByStudentAndReturnedAtIsNull(Student student);

    void deleteAllByGear(Gear gear);
}
