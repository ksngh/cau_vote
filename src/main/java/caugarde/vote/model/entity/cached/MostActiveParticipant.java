package caugarde.vote.model.entity.cached;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class MostActiveParticipant implements Serializable {

    private String majority;
    private String name;
    private Integer count;

}
