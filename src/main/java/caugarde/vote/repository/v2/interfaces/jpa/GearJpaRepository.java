package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Gear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GearJpaRepository extends JpaRepository<Gear, Long> {
}
