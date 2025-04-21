package com.gbsw.gbswhub.domain.user.service;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.global.Exception.DataNotFoundException;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Map<String, String> createUser(CreateUserDto dto){

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_DUPLICATION);
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
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입이 완료되었습니다.");
        return response;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
