package com.gbsw.gbswhub.domain.category.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "backend")
    private String category_name;
}
