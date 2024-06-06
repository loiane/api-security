package com.loiane.api.auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.loiane.api.user.User;
import com.loiane.api.user.UserDTO;
import com.loiane.api.user.UserRepository;
import com.loiane.api.user.role.Role;
import com.loiane.api.user.role.RoleEnum;
import com.loiane.api.user.role.RoleRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(UserDTO userDTO) {

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = new User(userDTO.fullName(), userDTO.email(), passwordEncoder.encode(userDTO.password()));
        user.setRole(optionalRole.get());
        return userRepository.save(user);
    }

    public User signin(UserDTO userDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.email(),
                        userDTO.password()));

        return userRepository.findByEmail(userDTO.email()).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
