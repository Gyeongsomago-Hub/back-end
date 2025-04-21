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

    private String id;


    @Schema(example = "suhwan3116")
    private String username;

    @Schema(example = "이수환")
    private String name;

    @Schema(example = "3학년")
    private String grade;

    @Schema(example = "1반")
    private String classNumber;

    private String department;

    private User.Role role = User.Role.USER;


    public UserDto(Long id, String username, String name, String grade, String classNumber, String department) {
        this.username = username;
        this.name = name;
        this.grade = grade;
        this.classNumber = classNumber;
        this.department = department;
        this.id = id != null ? id.toString() : null;
    }
}
