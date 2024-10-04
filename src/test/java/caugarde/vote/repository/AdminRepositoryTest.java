package caugarde.vote.repository;

import caugarde.vote.model.entity.Admin;
import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void makeAdminAccount(){
        Authority authority = authorityRepository.findByRole(Role.ADMIN).orElse(null);

        Admin admin = new Admin(UUID.randomUUID(),authority,"admin",passwordEncoder.encode("1234"));

        adminRepository.save(admin);
    }

}