package com.gbsw.gbswhub.domain.project.Service;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.project.db.*;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.project.model.Stack;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;
import static com.gbsw.gbswhub.domain.project.model.Project.Type.PROJECT;
import static com.gbsw.gbswhub.domain.global.util.StackConverter.convertToStacks;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Map<String, String> createProject(CreateProjectDto dto, User user) {
        validateUser(user);

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
                .created_at(LocalDateTime.now())
                .build();

        // 헬퍼 메서드 사용
        project.setStacks(convertToStacks(dto.getStacks(), project));

        projectRepository.save(project);
        Map<String, String> response = new HashMap<>();
        response.put("message", "프로젝트 모집이 생성되었습니다.");
        return response;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProject() {
        List<Project> projects = projectRepository.findByType(PROJECT);

        return projects.stream()
                .map(project -> {
                    List<String> stack = project.getStacks().stream()
                            .map(Stack::getStack_name)
                            .collect(Collectors.toList());

                    return new ProjectDto(
                            project.getId(),
                            project.getTitle(),
                            project.getContent(),
                            project.getPeople(),
                            project.getView_count(),
                            stack,
                            project.getOpenDate(),
                            project.getCloseDate(),
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
                project.getId(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stack,
                project.getOpenDate(),
                project.getCloseDate(),
                project.getStatus()
        );
    }

    public ProjectDto UpdateProject(Long projectId, UpdateProjectDto dto, User user) {

        validateUser(user);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        if (project.getType() != Project.Type.PROJECT) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }

        if(!project.getUser().getUser_id().equals(user.getUser_id())){
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

        projectRepository.save(project);

        List<String> stack = project.getStacks().stream()
                .map(Stack::getStack_name)
                .collect(Collectors.toList());

        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getContent(),
                project.getPeople(),
                project.getView_count(),
                stack,
                project.getOpenDate(),
                project.getCloseDate(),
                project.getStatus()
        );
    }

    public void deleteProject(Long id, User user){

        validateUser(user);

        Project project = projectRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        if (project.getType() != Project.Type.PROJECT) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }

        if(!project.getUser().getUser_id().equals(user.getUser_id())){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        projectRepository.delete(project);
    }
}