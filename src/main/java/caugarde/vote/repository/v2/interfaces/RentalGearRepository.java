package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;

import java.util.List;
import java.util.Optional;

public interface RentalGearRepository {

    void save(RentalGear rentalGear);

    Optional<RentalGear> findByStudentAndGear(Student student, Gear gear);

    List<RentalGear> findOverDueRentals();
}
