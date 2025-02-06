package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.entity.QGear;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;
import caugarde.vote.repository.v2.interfaces.GearRepository;
import caugarde.vote.repository.v2.interfaces.jpa.GearJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GearRepositoryImpl implements GearRepository {

    private final GearJpaRepository gearJpaRepository;

    private final JPAQueryFactory queryFactory;
    private static final QGear qGear = QGear.gear;

    @Override
    public void save(Gear gear) {
        gearJpaRepository.save(gear);
    }

    @Override
    public List<GearInfo.Response> findGears(FencingType fencingType,GearType gearType) {

        return queryFactory
                .select(Projections.constructor(GearInfo.Response.class,
                        qGear.id,
                        qGear.num,
                        qGear.fencingType,
                        qGear.gearType
                ))
                .from(qGear)
                .where(
                        qGear.gearType.eq(gearType),
                        qGear.fencingType.eq(fencingType)
                )
                .orderBy(qGear.num.asc())
                .fetch();

    }

    @Override
    public Optional<Gear> findById(long id) {
        return gearJpaRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        gearJpaRepository.deleteById(id);
    }


}
