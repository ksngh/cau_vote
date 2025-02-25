package caugarde.vote.model.dto.rentalgear;

import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RentalGearDetails {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Response {
        private FencingType fencingType;
        private Integer num;
        private GearType gearType;
        private String studentName;
        private LocalDateTime rentalDate;
    }
}
