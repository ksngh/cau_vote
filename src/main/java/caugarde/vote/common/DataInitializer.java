package caugarde.vote.common;

import caugarde.vote.model.entity.Admin;
import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.enums.Role;
import caugarde.vote.model.enums.Type;
import caugarde.vote.repository.jpa.AdminRepository;
import caugarde.vote.repository.jpa.AuthorityRepository;
import caugarde.vote.repository.jpa.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final AdminRepository adminRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${init.admin.name}")
    private String adminName;

    @Value("${init.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (authorityRepository.findByRole(Role.ADMIN).isEmpty()) {
            initializeData();
        }

    }

    private void initializeData() {
        authorityRepository.save(new Authority(UUID.randomUUID(), Role.ADMIN));
        authorityRepository.save(new Authority(UUID.randomUUID(), Role.USER));

        adminRepository.save(new Admin(UUID.randomUUID(),authorityRepository.findByRole(Role.ADMIN).orElse(null),adminName, passwordEncoder.encode(adminPassword)));

        categoryRepository.save(new caugarde.vote.model.entity.Category(UUID.randomUUID(), Type.SABRE));
        categoryRepository.save(new caugarde.vote.model.entity.Category(UUID.randomUUID(), Type.FLUERET));
        categoryRepository.save(new caugarde.vote.model.entity.Category(UUID.randomUUID(), Type.EPEE));
        categoryRepository.save(new caugarde.vote.model.entity.Category(UUID.randomUUID(), Type.COMMON));
    }
}
