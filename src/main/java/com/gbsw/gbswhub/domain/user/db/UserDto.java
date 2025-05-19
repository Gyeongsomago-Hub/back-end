package com.gbsw.gbswhub.domain.user.db;

import com.gbsw.gbswhub.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(example = "1")
    private Long user_id;

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    @Schema(example = "suhwan3116")
    private String username;

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    @Schema(example = "이수환")
    private String name;

    @NotBlank(message = "학년은 비어있을 수 없습니다.")
    @Schema(example = "3학년")
    private String grade;

    @NotBlank(message = "반은 비어있을 수 없습니다.")
    @Schema(example = "1반")
    private String classNumber;

    @NotBlank(message = "과는 비어있을 수 없습니다.")
    @Schema(example = "소프트웨어개발과")
    private String department;

    @Schema(example = "USER")
    @NotNull(message = "권한은 비어있을 수 없습니다.")
    private User.Role role = User.Role.USER;
}
