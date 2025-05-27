package com.gbsw.gbswhub.domain.club.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubResponseDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String target;
    private String type;
    private LocalDate openDate;
    private LocalDate closeDate;
    private Long userId;
}
