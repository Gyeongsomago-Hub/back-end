package com.gbsw.gbswhub.domain.user.service;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.db.UpdateUserDto;
import com.gbsw.gbswhub.domain.user.db.UserDto;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Map<String, String> createUser(CreateUserDto dto) {

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

    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserDto getUserInfo(String username) {
        User user = getUser(username);
        return new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getName(),
                user.getGrade(),
                user.getClassNumber(),
                user.getDepartment(),
                user.getRole()
        );
    }

    public UserDto updateUser(Long userId, String username, UpdateUserDto dto) {

        User user = getUser(username);

        //로그인 한 사용자가 관리자인지 검사하는 로직
        if (!user.getRole().equals(User.Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        user = userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setGrade(dto.getGrade());
        user.setClassNumber(dto.getClassNumber());
        user.setDepartment(dto.getDepartment());
        user.setRole(dto.getRole());
        userRepository.save(user);

        return new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getName(),
                user.getGrade(),
                user.getClassNumber(),
                user.getDepartment(),
                user.getRole()
                );
    }

    public List<UserDto> getAllUsers(String username){
        User user = getUser(username);

        if (!user.getRole().equals(User.Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        return userRepository.findAll()
                .stream()
                .map(u -> new UserDto(
                        u.getUser_id(),
                        u.getUsername(),
                        u.getName(),
                        u.getGrade(),
                        u.getClassNumber(),
                        u.getDepartment(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userId, String username) {
        User user = getUser(username);

        if(!user.getRole().equals(User.Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getName(),
                user.getGrade(),
                user.getClassNumber(),
                user.getDepartment(),
                user.getRole()
        );
    }
}
