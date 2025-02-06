package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.QRentalGear;
import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.v2.interfaces.RentalGearRepository;
import caugarde.vote.repository.v2.interfaces.jpa.RentalGearJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RentalGearRepositoryImpl implements RentalGearRepository {

    private static final QRentalGear qRentalGear = QRentalGear.rentalGear;
    private final JPAQueryFactory queryFactory;
    private final RentalGearJpaRepository rentalGearJpaRepository;

    @Override
    public void save(RentalGear rentalGear) {
        rentalGearJpaRepository.save(rentalGear);
    }

    @Override
    public Optional<RentalGear> findByStudentAndGear(Student student, Gear gear) {
        return rentalGearJpaRepository.findByGearAndStudent(gear,student);
    }

    @Override
    public List<RentalGear> findOverDueRentals() {
        return queryFactory
                .selectFrom(qRentalGear)
                .where(
                        qRentalGear.returnedAt.isNull(),
                        qRentalGear.dueDate.before(LocalDateTime.now())
                )
                .fetch();
    }

}
