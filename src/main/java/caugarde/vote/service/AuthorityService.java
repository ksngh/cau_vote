package caugarde.vote.service;

import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public Authority findByRole(Role role) {
        return authorityRepository.findByRole(role).orElse(null);
    }

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

}
