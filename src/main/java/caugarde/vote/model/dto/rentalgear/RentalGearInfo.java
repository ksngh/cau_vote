package caugarde.vote.model.dto.rentalgear;

import caugarde.vote.model.entity.RentalGear;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RentalGearInfo {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Response{
        private String studentName;
        private LocalDateTime dueDate;
        private Response(RentalGear rentalGear){
            this.studentName = rentalGear.getStudent().getName();
            this.dueDate = rentalGear.getDueDate();
        }
        public static Response from(RentalGear rentalGear){
            return new Response(rentalGear);
        }
    }
}
