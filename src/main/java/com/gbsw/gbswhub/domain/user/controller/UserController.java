package com.gbsw.gbswhub.domain.user.controller;

import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.db.CreateUserResult;
import com.gbsw.gbswhub.domain.user.filter.DuplicateEmailException;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResult> createUser(
            @RequestBody @Valid CreateUserDto dto, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder b = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                b.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(new CreateUserResult(b.toString(), null));
        }

        try {
            // 회원가입 처리
            User user = userService.createUser(dto);
            return ResponseEntity.ok(new CreateUserResult("회원가입 성공", user.getEmail()));
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(409).body(new CreateUserResult(e.getMessage(), null));
        }
    }
}
