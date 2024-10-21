package caugarde.vote.service;

import caugarde.vote.model.entity.Category;
import caugarde.vote.model.enums.Type;
import caugarde.vote.repository.jpa.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category typeToCategory(Type type) {
        return categoryRepository.findByType(type);
    }

    public Type stringToType(String string) {
        return Type.valueOf(string);
    }

    public Category getCategoryByString(String string) {
        return typeToCategory(stringToType(string));
    }

}
