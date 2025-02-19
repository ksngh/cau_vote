package caugarde.vote.model.dto.student.auth;

import caugarde.vote.model.enums.Role;
import lombok.Getter;

import java.util.Set;

public class AuthInfo {

    @Getter
    public static class Response{
        private Set<Role> role;

        public Response(Set<Role> role){
            this.role = role;
        }

        public static Response of(Set<Role> role){
            return new Response(role);
        }
    }

}
