package com.gbsw.gbswhub.domain.user.db;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.gbsw.gbswhub.domain.user.model.User.Role;

@Getter
@Setter
public class CreateUserDto {
    private String email;

    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상이여야합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,255}$", message = "비밀번호는 소문자, 대문자, 특수문자를 각각 1개 이상 포함해야 합니다.")
    private String password;

    private String name;
    private Role role = Role.USER;
}
