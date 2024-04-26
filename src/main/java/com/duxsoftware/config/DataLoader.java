package com.duxsoftware.config;

import com.duxsoftware.user.Role;
import com.duxsoftware.user.User;
import com.duxsoftware.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!(userRepository.findByUsername("test") == null)) {

            User defaultUser = new User();
            defaultUser.setId(1L);
            defaultUser.setUsername("test");
            defaultUser.setPassword(passwordEncoder.encode("12345"));
            defaultUser.setRole(Role.ADMIN);
            userRepository.save(defaultUser);
        }
    }
}
