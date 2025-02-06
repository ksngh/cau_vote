package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.gear.GearCreate;
import caugarde.vote.model.dto.gear.GearInfo;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;
import caugarde.vote.service.v2.interfaces.GearService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class GearApiController {

    private final GearService gearService;

    @PostMapping("/gear")
    public CustomApiResponse<Void> createGear(@Valid @RequestBody GearCreate.Request request) {
        gearService.create(request);
        return CustomApiResponse.OK(ResSuccessCode.CREATED);
    }

    @GetMapping("/gear")
    public CustomApiResponse<List<GearInfo.Response>> getAllGears(@RequestParam FencingType fencingType,
                                                         @RequestParam GearType gearType) {
        List<GearInfo.Response> gearInfos = gearService.getAllGears(fencingType, gearType);
        return CustomApiResponse.OK(ResSuccessCode.READ,gearInfos);
    }

    //TODO: 수정 필요
    @PatchMapping("/gear/{gearId}")
    public CustomApiResponse<Void> updateGear(){
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

    //TODO: 삭제 로직 추가

}
