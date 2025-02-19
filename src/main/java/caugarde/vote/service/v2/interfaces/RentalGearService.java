package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;

public interface RentalGearService {

    void create(Long gearId,String email);

    void returnGear(Long gearId,String email);

    RentalGear getByStudentAndGear(Student student, Gear gear);

    void getOverDueRentals();

    RentalGear getByGearId(Long gearId);

}
