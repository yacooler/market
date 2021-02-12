package ru.vyazankin.market.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vyazankin.market.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
