package caugarde.vote.model.entity;

import caugarde.vote.model.dto.gear.GearCreate;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.GearType;
import caugarde.vote.model.enums.GearStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUM")
    private Integer num;

    @Column(name = "FENCING_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private FencingType fencingType;

    @Column(name = "GEAR_TYPE", length = 30)
    @Enumerated(EnumType.STRING)
    private GearType gearType;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private GearStatus status;

    private Gear(GearCreate.Request request){
        this.num = request.getNum();
        this.fencingType = request.getFencingType();
        this.gearType = request.getGearType();
        this.status = GearStatus.AVAILABLE;
    }

    public static Gear from(GearCreate.Request request){
        return new Gear(request);
    }

    public void returned(){
        this.status=GearStatus.AVAILABLE;
    }

    public void rental(){
        this.status = GearStatus.IN_USE;
    }

}
