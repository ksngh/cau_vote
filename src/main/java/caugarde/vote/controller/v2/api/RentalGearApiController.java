package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.rentalgear.RentalGearDetails;
import caugarde.vote.model.dto.rentalgear.RentalGearInfo;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.service.v2.interfaces.RentalGearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/api")
public class RentalGearApiController {

    private final RentalGearService rentalGearService;

    @PostMapping("/gear/{gearId}/rental")
    public CustomApiResponse<Void> createRentalGear(@PathVariable Long gearId,
                                                    @AuthenticationPrincipal CustomOAuthUser user) {
        rentalGearService.create(gearId, user.getName());
        return CustomApiResponse.OK(ResSuccessCode.CREATED);
    }

    @PatchMapping("/gear/{gearId}/return")
    public CustomApiResponse<Void> returnRentalGear(@PathVariable Long gearId,
                                                    @AuthenticationPrincipal CustomOAuthUser user) {
        rentalGearService.returnGear(gearId, user.getName());
        return CustomApiResponse.OK(ResSuccessCode.UPDATED);
    }

    @GetMapping("/gear/{gearId}/rental")
    public CustomApiResponse<RentalGearInfo.Response> getRentalGear(@PathVariable Long gearId) {
        RentalGearInfo.Response response = RentalGearInfo.Response.from(rentalGearService.getByGearId(gearId));
        return CustomApiResponse.OK(ResSuccessCode.READ, response);
    }

    @GetMapping("/rental-gear")
    public CustomApiResponse<Slice<RentalGearDetails.Response>> getRentalGear(@RequestParam(required = false) Long cursorId,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Slice<RentalGearDetails.Response> responses = rentalGearService.getPages(cursorId, size);
        return CustomApiResponse.OK(ResSuccessCode.READ,responses);
    }

}
