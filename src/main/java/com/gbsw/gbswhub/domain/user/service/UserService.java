package com.gbsw.gbswhub.domain.user.service;

import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.global.Exception.DataNotFoundException;
import com.gbsw.gbswhub.domain.user.filter.DuplicateUsernameException;
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

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUsernameException("이미 사용 중인 아이디입니다.");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .grade(dto.getGrade())
                .classNumber(dto.getClassNumber())
                .department(dto.getDepartment())
                .role(dto.getRole())
                .build();
        return userRepository.save(user);
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        return user;
    }
}
