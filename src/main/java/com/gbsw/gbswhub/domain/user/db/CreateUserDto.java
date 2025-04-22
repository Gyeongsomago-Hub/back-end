package com.gbsw.gbswhub.domain.user.db;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.gbsw.gbswhub.domain.user.model.User.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,255}$",
            message = "비밀번호는 소문자, 대문자, 특수문자를 각각 1개 이상 포함해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(example = "Hwan1234@")
    private String password;
    
    @NotBlank(message = "아이디를 입력해주세요.")
    @Schema(example = "suhwan3116")
    private String username;

    @NotBlank(message = "이름을 입력해주세요.")
    @Schema(example = "이수환")
    private String name;

    @NotBlank(message = "학년을 입력해주세요.")
    @Schema(example = "3학년")
    private String grade;

    @NotBlank(message = "반을 입력해주세요.")
    @Schema(example = "1반")
    private String classNumber;

    @NotBlank(message = "과를 입력해주세요")
    private String department;

    @Schema(description = "USER, ADMIN, CLUB_LEADER")
    private Role role = Role.USER;
}