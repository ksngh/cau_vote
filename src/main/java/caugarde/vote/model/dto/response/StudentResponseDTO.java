package caugarde.vote.model.dto.response;

import caugarde.vote.model.enums.Role;
import lombok.Getter;

@Getter
public class StudentResponseDTO {

    private String name;
    private String role;

    public StudentResponseDTO(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
