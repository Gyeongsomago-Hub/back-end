package com.gbsw.gbswhub.domain.user.service;

import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.user.filter.DataNotFoundException;
import com.gbsw.gbswhub.domain.user.filter.DuplicateEmailException;
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

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .role(dto.getRole())
                .build();
        return userRepository.save(user);
    }

    public User getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        return user;
    }
}
