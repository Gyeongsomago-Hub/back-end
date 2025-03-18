package com.gbsw.gbswhub.domain.user.service;

import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserDto dto){
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .role(dto.getRole())
                .build();
        return userRepository.save(user);
    }
}
