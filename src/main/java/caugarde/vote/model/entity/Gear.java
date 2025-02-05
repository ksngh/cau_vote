package caugarde.vote.model.entity;

import caugarde.vote.model.enums.FencingType;
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

    @Column(name = "TYPE", length = 10)
    @Enumerated(EnumType.STRING)
    private FencingType type;

}
