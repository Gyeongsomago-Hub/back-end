package com.gbsw.gbswhub.domain.project.Service;


import com.gbsw.gbswhub.domain.category.db.CategoryDto;
import com.gbsw.gbswhub.domain.category.db.CategoryRepository;
import com.gbsw.gbswhub.domain.category.model.Category;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.project.db.CreateMentoringDto;
import com.gbsw.gbswhub.domain.project.db.CreateProjectDto;
import com.gbsw.gbswhub.domain.project.db.ProjectDto;
import com.gbsw.gbswhub.domain.project.db.ProjectRepository;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.project.model.Stack;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;

    public Map<String, String> createProject(CreateProjectDto dto, User user) {


        if(user == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }


        Project project = Project.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .people(dto.getPeople())
                .type(dto.getType())
                .status(dto.getStatus())
                .view_count(0L)
                .user(user)
                .created_at(LocalDateTime.now())
                .build();

        List<Stack> StackList = dto.getStacks().stream()
                        .map(name -> {
                            Stack stack = new Stack();
                            stack.setStack_name(name);
                            stack.setProject(project);
                            return stack;
                        })
                                .toList();
        project.setStacks(StackList);

        projectRepository.save(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "프로젝트 모집이 생성되었습니다.");
        return response;
    }

    public Map<String, String> createMentoring(CreateMentoringDto dto, User user) {

        if(user == null){
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

        List<Stack> StackList = dto.getStacks().stream()
                .map(name -> {
                    Stack stack = new Stack();
                    stack.setStack_name(name);
                    stack.setProject(project);
                    return stack;
                })
                .toList();
        project.setStacks(StackList);

        projectRepository.save(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "멘토멘티 모집이 생성되었습니다.");
        return response;
    }


    public List<ProjectDto> getAllProject() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> {
                    List<String> stacks = project.getStacks().stream()
                            .map(Stack::getStack_name)
                            .collect(Collectors.toList());

                    return new ProjectDto(
                            project.getProject_id(),
                            project.getTitle(),
                            project.getContent(),
                            project.getPeople(),
                            project.getView_count(),
                            stacks,
                            project.getStatus()
                    );
                })
                .collect(Collectors.toList());
    }

    public ProjectDto getProjectById(Long id) {
        projectRepository.incrementViewCount(id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        List<String> stacks = project.getStacks().stream()
                .map(Stack::getStack_name)
                .collect(Collectors.toList());

        return new ProjectDto(
                project.getProject_id(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stacks,
                project.getStatus()
        );
    }
}
