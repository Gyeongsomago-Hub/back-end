package com.gbsw.gbswhub.domain.category.controller;

import com.gbsw.gbswhub.domain.category.db.CategoryDto;
import com.gbsw.gbswhub.domain.category.db.CreateCategoryDto;
import com.gbsw.gbswhub.domain.category.service.CategoryService;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@Tag(name = "Category", description = "category에 관한 API")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping()
    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/Category200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createCategory(
            @Valid @RequestBody CreateCategoryDto createCategoryDto,
            Principal principal) {

        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(categoryService.createCategory(createCategoryDto, user));
    }

    @GetMapping()
    @Operation(summary = "모든 카테고리 조회")
    @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class))))
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    @Operation(summary = "하나의 카테고리 조회")
    @ApiResponse(responseCode = "200", description = "카테고리 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDto.class)))
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Category404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
