package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.rentalgear.RentalGearDetails;
import caugarde.vote.model.dto.rentalgear.RentalGearHistory;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalGearService {

    void create(Long gearId,String email);

    void returnGear(Long gearId,String email);

    void returnAllGear(String email);

    RentalGear getByStudentAndGear(Student student, Gear gear);

    void getOverDueRentals();

    RentalGear getByGearId(Long gearId);

    Slice<RentalGearDetails.Response> getPages(LocalDateTime cursorId, int size);

    Slice<RentalGearHistory.Response> getHistoryPages(LocalDateTime cursorId, int size);

    Slice<GearInfo.Response> getUserPages(String email, Long cursorId, int size);

    void imposeLateFee(List<RentalGear> overDueRentals);
}
