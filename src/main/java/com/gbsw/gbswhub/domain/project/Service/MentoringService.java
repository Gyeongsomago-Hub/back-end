package com.gbsw.gbswhub.domain.project.Service;

import com.gbsw.gbswhub.domain.category.db.CategoryRepository;
import com.gbsw.gbswhub.domain.category.model.Category;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.project.mentoring.db.CreateMentoringDto;
import com.gbsw.gbswhub.domain.project.mentoring.db.MentoringDto;
import com.gbsw.gbswhub.domain.project.db.ProjectRepository;
import com.gbsw.gbswhub.domain.project.mentoring.db.UpdateMentoringDto;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.project.model.Stack;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gbsw.gbswhub.domain.project.model.Project.Type.MENTORING;
import static com.gbsw.gbswhub.domain.project.util.StackConverter.convertToStacks;

@Service
@RequiredArgsConstructor
public class MentoringService {
    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

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
                .openDate(dto.getOpenDate())
                .closeDate(dto.getCloseDate())
                .category(category)
                .created_at(LocalDateTime.now())
                .build();

        project.setStacks(convertToStacks(dto.getStacks(), project));

        projectRepository.save(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "멘토멘티 모집이 생성되었습니다.");
        return response;
    }

    public List<MentoringDto> getAllMentoring() {
        List<Project> projects = projectRepository.findByType(MENTORING);
        return projects.stream()
                .map(project -> {
                    List<String> stack = project.getStacks().stream()
                            .map(Stack::getStack_name)
                            .collect(Collectors.toList());

                    return new MentoringDto(
                            project.getProject_id(),
                            project.getTitle(),
                            project.getContent(),
                            project.getPeople(),
                            project.getView_count(),
                            stack,
                            project.getOpenDate(),
                            project.getCloseDate(),
                            project.getStatus(),
                            project.getCategory().getCategory_id()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public MentoringDto getMentoringById(Long id){
        projectRepository.incrementViewCount(id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENTORING_NOT_FOUND));

        if(project.getType() != Project.Type.MENTORING){
            throw new BusinessException(ErrorCode.MENTORING_NOT_FOUND);
        }

        if (project.getCategory() == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        List<String> stack = project.getStacks().stream()
                .map(Stack::getStack_name)
                .collect(Collectors.toList());

        return new MentoringDto(
                project.getProject_id(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stack,
                project.getOpenDate(),
                project.getCloseDate(),
                project.getStatus(),
                project.getCategory().getCategory_id()
        );
    }

    public MentoringDto updateMentoring(Long mentoringId, UpdateMentoringDto dto, User user) {

        if(user == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Project project = projectRepository.findById(mentoringId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENTORING_NOT_FOUND));

        if (project.getType() != Project.Type.MENTORING) {
            throw new BusinessException(ErrorCode.MENTORING_NOT_FOUND);
        }

        if(!project.getUser().getId().equals(user.getId())){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        project.setTitle(dto.getTitle());
        project.setContent(dto.getContent());
        project.setPeople(dto.getPeople());
        project.getStacks().clear();
        if (dto.getStack() != null) {
            project.getStacks().addAll(convertToStacks(dto.getStack(), project));
        }
        project.setOpenDate(dto.getOpenDate());
        project.setCloseDate(dto.getCloseDate());
        project.setStatus(dto.getStatus());

        Category category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
        project.setCategory(category);

        projectRepository.save(project);

        List<String> stack = project.getStacks().stream()
                .map(Stack::getStack_name)
                .collect(Collectors.toList());

        return new MentoringDto(
                project.getProject_id(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stack,
                project.getOpenDate(),
                project.getCloseDate(),
                project.getStatus(),
                project.getCategory().getCategory_id()
        );
    }

    public void deleteMentoring(Long  id, User user){

        if(user == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENTORING_NOT_FOUND));

        if (project.getType() != Project.Type.MENTORING) {
            throw new BusinessException(ErrorCode.MENTORING_NOT_FOUND);
        }


        if (!project.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        projectRepository.delete(project);
    }
}

