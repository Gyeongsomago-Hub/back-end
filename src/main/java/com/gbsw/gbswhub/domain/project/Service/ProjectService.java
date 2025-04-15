package com.gbsw.gbswhub.domain.project.Service;

import com.gbsw.gbswhub.domain.category.db.CategoryRepository;
import com.gbsw.gbswhub.domain.category.model.Category;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.jwt.provider.TokenProvider;
import com.gbsw.gbswhub.domain.project.db.*;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.project.model.Stack;
import com.gbsw.gbswhub.domain.user.db.UserRepository;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gbsw.gbswhub.domain.project.model.Project.Type.PROJECT;
import static com.gbsw.gbswhub.domain.project.util.StackConverter.convertToStacks;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final CategoryRepository categoryRepository;


    public Map<String, String> createProject(CreateProjectDto dto, User user) {
        if (user == null) {
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

        // 헬퍼 메서드 사용
        project.setStacks(convertToStacks(dto.getStacks(), project));

        projectRepository.save(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "프로젝트 모집이 생성되었습니다.");
        return response;
    }

    public List<ProjectDto> getAllProject() {
        List<Project> projects = projectRepository.findByType(PROJECT);

        return projects.stream()
                .map(project -> {
                    List<String> stack = project.getStacks().stream()
                            .map(Stack::getStack_name)
                            .collect(Collectors.toList());

                    return new ProjectDto(
                            project.getProject_id(),
                            project.getTitle(),
                            project.getContent(),
                            project.getPeople(),
                            project.getView_count(),
                            stack,
                            project.getStatus()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto getProjectById(Long id) {
        projectRepository.incrementViewCount(id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));


        List<String> stack = project.getStacks().stream()
                .map(Stack::getStack_name)
                .collect(Collectors.toList());

        return new ProjectDto(
                project.getProject_id(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stack,
                project.getStatus()
        );
    }

    public ProjectDto UpdateProject(Long projectId, UpdateProjectDto dto, User user) {

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

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
        project.setStatus(dto.getStatus());

        projectRepository.save(project);

        List<String> stack = project.getStacks().stream()
                .map(Stack::getStack_name)
                .collect(Collectors.toList());

        return new ProjectDto(
                project.getProject_id(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stack,
                project.getStatus()
        );
    }

    public void deleteProject(Long id, User user){

        if(user==null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Project project = projectRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        if(!project.getUser().getId().equals(user.getId())){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        projectRepository.deleteById(id);
    }
}