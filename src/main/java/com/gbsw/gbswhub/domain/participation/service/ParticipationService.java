package com.gbsw.gbswhub.domain.participation.service;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.participation.db.UpdateMentoringStatusDto;
import com.gbsw.gbswhub.domain.participation.db.RequestMentoringDto;
import com.gbsw.gbswhub.domain.participation.db.RequestProjectDto;
import com.gbsw.gbswhub.domain.participation.db.ParticipationRepository;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import com.gbsw.gbswhub.domain.project.db.ProjectRepository;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final ProjectRepository projectRepository;

    public Map<String, String> RequestProject(RequestProjectDto dto, User user) {

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }


        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        if (project.getType() != Project.Type.PROJECT) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }

        Participation participation = Participation.builder()
                .position(dto.getPosition())
                .project(project)
                .created_at(LocalDateTime.now())
                .status(dto.getStatus())
                .type(dto.getType())
                .user(user)
                .build();

        participationRepository.save(participation);
        Map<String, String> response = new HashMap<>();
        response.put("message", "프로젝트 모집에 참가 완료 되었습니다.");
        return response;
    }

    public Map<String, String> RequestMentoring(RequestMentoringDto dto, User user) {

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MENTORING_NOT_FOUND));

        if (project.getType() != Project.Type.MENTORING) {
            throw new BusinessException(ErrorCode.MENTORING_NOT_FOUND);
        }

        Participation participation = Participation.builder()
                .position(dto.getPosition())
                .introduce(dto.getIntroduce())
                .name(dto.getName())
                .grade(dto.getGrade())
                .classNo(dto.getClassNo())
                .studentNo(dto.getStudentNo())
                .status(dto.getStatus())
                .type(dto.getType())
                .user(user)
                .project(project)
                .created_at(LocalDateTime.now())
                .build();
        participationRepository.save(participation);
        Map<String, String> response = new HashMap<>();
        response.put("message", "멘토멘티 모집에 참가 신청되었습니다.");
        return response;
    }

    public UpdateMentoringStatusDto updateMentoringStatus(Long participationId, Participation.Status status, User user) {
        

        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PARTICIPATION_NOT_FOUND));

        if (!participation.getProject().getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        participation.setStatus(status);
        participation.setCreated_at(LocalDateTime.now());
        participationRepository.save(participation);

        return new UpdateMentoringStatusDto(participation.getStatus());
    }
}
