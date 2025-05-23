package com.gbsw.gbswhub.domain.category.service;

import com.gbsw.gbswhub.domain.category.db.CategoryDto;
import com.gbsw.gbswhub.domain.category.db.CategoryRepository;
import com.gbsw.gbswhub.domain.category.db.CreateCategoryDto;
import com.gbsw.gbswhub.domain.category.model.Category;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Map<String, String> createCategory (CreateCategoryDto dto, User user) {

        validateUser(user);

        Category category = Category.builder()
                .category_name(dto.getCategory_name())
                .build();

        categoryRepository.save(category);
        Map<String, String > response = new HashMap<>();
        response.put("message", "카테고리가 생성되었습니다.");
        return response;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategory(){
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryDto(c.getCategory_id(), c.getCategory_name()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        return new CategoryDto(category.getCategory_id(), category.getCategory_name());
    }
}
