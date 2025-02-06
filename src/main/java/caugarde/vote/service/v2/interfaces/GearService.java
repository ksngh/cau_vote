package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.gear.GearCreate;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.gear.GearUpdate;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;

import java.util.List;

public interface GearService {

    void create(GearCreate.Request request);

    List<GearInfo.Response> getAllGears(FencingType fencingType, GearType gearType);

    Gear getById(Long id);

    void update(Long id, GearUpdate.Request request);

    void delete(Long id);


}
