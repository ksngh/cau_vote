package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;

import java.util.List;
import java.util.Optional;

public interface GearRepository {

    void save(Gear gear);

    List<GearInfo.Response> findGears(FencingType fencingType,GearType gearType);

    Optional<Gear> findById(Long id);

    void deleteById(Long id);
}
