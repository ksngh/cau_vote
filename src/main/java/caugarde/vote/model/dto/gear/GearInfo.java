package caugarde.vote.model.dto.gear;

import caugarde.vote.model.entity.Gear;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearStatus;
import caugarde.vote.model.enums.GearType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GearInfo {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Response {
        private Long id;
        private Integer num;
        private FencingType fencingType;
        private GearStatus gearStatus;
        private GearType gearType;

        private Response(Gear gear){
            this.id = gear.getId();
            this.num = gear.getNum();
            this.gearStatus=gear.getStatus();
            this.fencingType = gear.getFencingType();
            this.gearType = gear.getGearType();
        }

        public static Response from(Gear gear){
            return new Response(gear);
        }
    }

}
