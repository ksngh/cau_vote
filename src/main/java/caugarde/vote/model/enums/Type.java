package caugarde.vote.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    SABRE("SABRE"),
    FLUERET("FLUERET"),
    EPEE("EPEE"),
    COMMON("COMMON");

    private final String name;

}
