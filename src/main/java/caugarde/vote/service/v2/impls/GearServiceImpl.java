package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.api.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.dto.gear.GearCreate;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.dto.gear.GearUpdate;
import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearStatus;
import caugarde.vote.model.enums.GearType;
import caugarde.vote.repository.v2.interfaces.GearRepository;
import caugarde.vote.service.v2.interfaces.GearService;
import caugarde.vote.service.v2.interfaces.RentalGearService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GearServiceImpl implements GearService {

    private final GearRepository gearRepository;

    @Override
    public void create(GearCreate.Request request) {
        gearRepository.save(Gear.from(request));
    }

    @Override
    public List<GearInfo.Response> getAllGears(FencingType fencingType, GearType gearType) {
        return gearRepository.findGears(fencingType, gearType);
    }

    @Override
    public Gear getById(Long id) {
        return gearRepository.findById(id).orElseThrow(()-> new CustomApiException(ResErrorCode.NOT_FOUND,"해당하는 장비가 없습니다."));
    }

    @Override
    @Transactional
    public void update(Long id, GearUpdate.Request request) {
        getById(id).update(request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateRental(id);
        gearRepository.deleteById(id);
    }

    private void validateRental(Long id){
        if(getById(id).getStatus().equals(GearStatus.IN_USE)){
            throw new CustomApiException(ResErrorCode.BAD_REQUEST,"현재 대여중인 장비입니다.");
        }
    }


}
