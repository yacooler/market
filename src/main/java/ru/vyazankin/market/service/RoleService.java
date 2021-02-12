package ru.vyazankin.market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vyazankin.market.model.Role;
import ru.vyazankin.market.repository.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }
}
