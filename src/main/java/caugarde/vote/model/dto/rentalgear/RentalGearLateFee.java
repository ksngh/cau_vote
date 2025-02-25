package caugarde.vote.model.dto.rentalgear;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class RentalGearLateFee {

    @Getter
    @NoArgsConstructor
    public static class Response{
        private int lateFee;
        public Response(int lateFee) {
            this.lateFee = lateFee;
        }
        public static Response of(int lateFee) {
            return new Response(lateFee);
        }
    }
}
