package caugarde.vote.model.dto.rentalgear;

import caugarde.vote.model.entity.RentalGear;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RentalGearDetails {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Response{
        private FencingType fencingType;
        private GearType gearType;
        private String studentName;
        private LocalDateTime rentalDate;
        private Response(RentalGear rentalGear){
            this.fencingType = rentalGear.getGear().getFencingType();
            this.gearType = rentalGear.getGear().getGearType();
            this.studentName = rentalGear.getStudent().getName();
            this.rentalDate = rentalGear.getRentalDate();
        }
        public static RentalGearDetails.Response from(RentalGear rentalGear){
            return new RentalGearDetails.Response(rentalGear);
        }
    }
}
