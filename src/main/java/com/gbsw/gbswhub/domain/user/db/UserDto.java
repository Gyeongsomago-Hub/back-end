package com.gbsw.gbswhub.domain.user.db;

import com.gbsw.gbswhub.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @Schema(example = "suhwan3116")
    private String username;

    @Schema(example = "이수환")
    private String name;

    @Schema(example = "3학년")
    private String grade;

    @Schema(example = "1반")
    private String classNumber;

    @Schema(example = "소프트웨어개발과")
    private String department;

    @Schema(example = "USER")
    private User.Role role = User.Role.USER;
}
