package caugarde.vote.controller.v2.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.service.v2.interfaces.RentalGearService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

}
