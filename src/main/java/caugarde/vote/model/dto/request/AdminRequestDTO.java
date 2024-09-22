package caugarde.vote.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminRequestDTO {

    String username;
    String password;

    public AdminRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
