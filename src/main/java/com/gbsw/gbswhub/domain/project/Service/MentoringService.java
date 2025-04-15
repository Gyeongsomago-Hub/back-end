package com.gbsw.gbswhub.domain.project.Service;

import com.gbsw.gbswhub.domain.category.db.CategoryRepository;
import com.gbsw.gbswhub.domain.category.model.Category;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.project.db.CreateMentoringDto;
import com.gbsw.gbswhub.domain.project.db.ProjectRepository;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.gbsw.gbswhub.domain.project.util.StackConverter.convertToStacks;

@Service
@RequiredArgsConstructor
public class MentoringService {
    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;

    public Map<String, String> createMentoring(CreateMentoringDto dto, User user) {

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        Project project = Project.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .people(dto.getPeople())
                .type(dto.getType())
                .status(dto.getStatus())
                .view_count(0L)
                .user(user)
                .category(category)
                .created_at(LocalDateTime.now())
                .build();

        project.setStacks(convertToStacks(dto.getStacks(), project));

        projectRepository.save(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "멘토멘티 모집이 생성되었습니다.");
        return response;
    }
}
