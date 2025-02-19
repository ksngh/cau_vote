package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.rentalgear.RentalGearDetails;
import caugarde.vote.model.entity.*;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearStatus;
import caugarde.vote.model.enums.GearType;
import caugarde.vote.repository.v2.interfaces.RentalGearRepository;
import caugarde.vote.repository.v2.interfaces.jpa.RentalGearJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RentalGearRepositoryImpl implements RentalGearRepository {

    private static final QRentalGear qRentalGear = QRentalGear.rentalGear;
    private static final QGear qGear = QGear.gear;
    private static final QStudent qStudent = QStudent.student;
    private final JPAQueryFactory queryFactory;
    private final RentalGearJpaRepository rentalGearJpaRepository;

    @Override
    public void save(RentalGear rentalGear) {
        rentalGearJpaRepository.save(rentalGear);
    }

    @Override
    public Optional<RentalGear> findByStudentAndGear(Student student, Gear gear) {
        return rentalGearJpaRepository.findByGearAndStudentAndReturnedAtIsNull(gear,student);
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

    @Override
    public Optional<RentalGear> findByGear(Gear gear) {
        return rentalGearJpaRepository.findByGearAndReturnedAtIsNull(gear);
    }

    @Override
    public Slice<RentalGearDetails.Response> getPages(Long cursorId, int size) {
        List<RentalGearDetails.Response> items = queryFactory
                .select(Projections.constructor(
                        RentalGearDetails.Response.class,
                        qGear.fencingType,
                        qGear.num,
                        qGear.gearType,
                        qStudent.name,
                        qRentalGear.rentalDate
                ))
                .from(qRentalGear)
                .join(qRentalGear.gear, qGear)
                .join(qRentalGear.student, qStudent)
                .where(cursorId != null ? qRentalGear.id.lt(cursorId) : null, isNotReturned())
                .orderBy(qRentalGear.rentalDate.desc()) // 최신 대여순 정렬
                .limit(size + 1)  // 다음 페이지 확인을 위해 요청 개수보다 +1
                .fetch();

        boolean hasNext = items.size() > size; // 다음 페이지 여부 확인

        if (hasNext) {
            items.remove(items.size() - 1); // 실제 응답에서는 +1한 데이터 제외
        }
        return new SliceImpl<>(items, PageRequest.of(0, size), hasNext);
    }

    @Override
    public Slice<GearInfo.Response> getUserPages(String email, Long cursorId, int size) {
        List<GearInfo.Response> items = queryFactory
                .select(Projections.constructor(
                        GearInfo.Response.class,
                        qGear.id,
                        qGear.num,
                        qGear.fencingType,
                        qGear.status,
                        qGear.gearType
                ))
                .from(qRentalGear)
                .join(qRentalGear.gear, qGear) // Gear 조인
                .join(qRentalGear.student, qStudent) // Student 조인
                .where(qStudent.email.eq(email), // email을 기반으로 Student 조회
                        cursorId != null ? qRentalGear.id.lt(cursorId) : null, // 커서 기반 페이지네이션 적용
                        isNotReturned()) // 반납되지 않은 기기만 조회
                .orderBy(qRentalGear.rentalDate.desc()) // 최신 대여순 정렬
                .limit(size + 1) // 다음 페이지 확인을 위해 size + 1개 가져오기
                .fetch();

        boolean hasNext = items.size() > size; // 다음 페이지 존재 여부 확인

        if (hasNext) {
            items.remove(items.size() - 1); // 마지막 요소 제거하여 응답 크기 맞추기
        }

        return new SliceImpl<>(items, PageRequest.of(0, size), hasNext);
    }

    private BooleanExpression isNotReturned(){
        return qRentalGear.returnedAt.isNull();
    }


}
