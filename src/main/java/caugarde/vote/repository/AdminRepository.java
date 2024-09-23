package caugarde.vote.repository;

import caugarde.vote.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByAdminPk(UUID uuid);

    Optional<Admin> findByUsername(String username);
}
