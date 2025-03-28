package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.api.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.rentalgear.RentalGearDetails;
import caugarde.vote.model.dto.rentalgear.RentalGearHistory;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.GearStatus;
import caugarde.vote.repository.v2.interfaces.RentalGearRepository;
import caugarde.vote.service.v2.interfaces.GearService;
import caugarde.vote.service.v2.interfaces.RentalGearService;
import caugarde.vote.service.v2.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalGearServiceImpl implements RentalGearService {

    private final GearService gearService;
    private final RentalGearRepository rentalGearRepository;
    private final StudentService studentService;

    @Override
    public void create(Long gearId, String email) {
        Student student = studentService.getByEmail(email);
        validateLateFee(student);
        Gear gear = gearService.getById(gearId);
        gear.rental();
        RentalGear rentalGear = RentalGear.of(student, gear);
        rentalGearRepository.save(rentalGear);
    }

    @Override
    @Transactional
    public void returnGear(Long gearId, String email) {
        Gear gear = gearService.getById(gearId);
        Student student = studentService.getByEmail(email);
        validateRentalStatus(gear);
        RentalGear rentalGear = getByStudentAndGear(student, gear);
        gear.returnGear();
        rentalGear.returnGear();
    }

    @Override
    @Transactional
    public void returnAllGear(String email) {
        Student student = studentService.getByEmail(email);
        rentalGearRepository.findByStudentAndReturnedAtIsNull(student).forEach(RentalGear::returnGear);
    }

    private void validateLateFee(Student student) {
        if (student.getOverdueFine() > 0) {
            throw new CustomApiException(ResErrorCode.BAD_REQUEST, "미납된 연체료가 있습니다.");
        }
    }

    private void validateRentalStatus(Gear gear) {
        if (!gear.getStatus().equals(GearStatus.IN_USE)) {
            throw new CustomApiException(ResErrorCode.BAD_REQUEST, "대여 중인 장비가 아닙니다.");
        }
    }

    @Override
    public RentalGear getByStudentAndGear(Student student, Gear gear) {
        return rentalGearRepository.findByStudentAndGear(student, gear).orElseThrow(() -> new CustomApiException(ResErrorCode.NOT_FOUND, "해당 장비 대여 내역이 없습니다."));
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void getOverDueRentals() {
        List<RentalGear> overDueRentals = rentalGearRepository.findOverDueRentals();
        overDueRentals.forEach(RentalGear::imposeLateFee);
        imposeLateFee(overDueRentals);
    }

    @Override
    public RentalGear getByGearId(Long gearId) {
        Gear gear = gearService.getById(gearId);
        return rentalGearRepository.findByGear(gear).orElse(null);
    }

    @Override
    public Slice<RentalGearDetails.Response> getPages(LocalDateTime cursorId, int size) {
        return rentalGearRepository.getPages(cursorId, size);
    }

    @Override
    public Slice<RentalGearHistory.Response> getHistoryPages(LocalDateTime cursorId, int size) {
        return rentalGearRepository.getHistoryPages(cursorId, size);
    }

    @Override
    public Slice<GearInfo.Response> getUserPages(String email, Long cursorId, int size) {
        return rentalGearRepository.getUserPages(email, cursorId, size);
    }

    @Override
    public void imposeLateFee(List<RentalGear> overDueRentals) {
        overDueRentals.stream()
                .map(RentalGear::getStudent)
                .distinct()
                .forEach(student -> {
                    int maxLateFee = overDueRentals.stream()
                            .filter(rentalGear -> rentalGear.getStudent().equals(student))
                            .mapToInt(RentalGear::getLateFee)
                            .max()
                            .orElse(0);
                    if (student.getOverdueFine() < maxLateFee) {
                        student.imposeOverDueFine(maxLateFee);
                    }
                });
    }

}
