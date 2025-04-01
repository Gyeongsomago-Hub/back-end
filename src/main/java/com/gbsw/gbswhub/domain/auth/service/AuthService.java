package com.gbsw.gbswhub.domain.auth.service;

import com.gbsw.gbswhub.domain.auth.db.LoginRequest;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public boolean validateUser(LoginRequest request) {

        User user = userService.getUser(request.getEmail());

        //encoder로 암호화된 비번 비교
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
