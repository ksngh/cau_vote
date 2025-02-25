package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.rentalgear.RentalGearDetails;
import caugarde.vote.model.dto.rentalgear.RentalGearHistory;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalGearRepository {

    void save(RentalGear rentalGear);

    Optional<RentalGear> findByStudentAndGear(Student student, Gear gear);

    List<RentalGear> findOverDueRentals();

    Optional<RentalGear> findByGear(Gear gear);

    Slice<RentalGearDetails.Response> getPages(LocalDateTime cursorId, int size);

    Slice<RentalGearHistory.Response> getHistoryPages(LocalDateTime cursorId, int size);

    Slice<GearInfo.Response> getUserPages(String email, Long cursorId, int size);

    List<RentalGear> findByStudentAndLateFee(Student student);

    List<RentalGear> findByStudentAndReturnedAtIsNull(Student student);
}
