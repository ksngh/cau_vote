package caugarde.vote.repository.jpa;

import caugarde.vote.model.entity.Category;
import caugarde.vote.model.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Category findByType(Type type);
}
