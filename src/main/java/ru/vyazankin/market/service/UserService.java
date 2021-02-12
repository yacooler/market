package ru.vyazankin.market.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vyazankin.market.dto.UserDto;
import ru.vyazankin.market.exceptions.ResourceNotFoundException;
import ru.vyazankin.market.model.Role;
import ru.vyazankin.market.model.User;
import ru.vyazankin.market.repository.RoleRepository;
import ru.vyazankin.market.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public UserDto saveUserByUserDto(UserDto userDto){
        User user = new User(userDto.getName(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getEmail());
        Role role = roleService.findRoleByName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("Role ROLE_USER is not found!"));
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        return userDto;
    }
}