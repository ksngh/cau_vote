package caugarde.vote.repository.jpa;

import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

    Optional<Authority> findByRole(Role role);
}
