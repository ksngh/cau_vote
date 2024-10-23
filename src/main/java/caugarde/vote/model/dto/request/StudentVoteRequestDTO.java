package caugarde.vote.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudentVoteRequestDTO {

        public record Create(String category) {}

        public record Delete(String category) {}

}
