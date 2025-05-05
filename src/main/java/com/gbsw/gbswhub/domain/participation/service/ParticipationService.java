package com.gbsw.gbswhub.domain.participation.service;

import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.club.repository.ClubRepository;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.participation.db.*;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import com.gbsw.gbswhub.domain.project.db.ProjectRepository;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final ProjectRepository projectRepository;
    private final ClubRepository clubRepository;

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

    public Map<String, String> requestClub(RequestClubDto dto, User user){
        if(user == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Club club = clubRepository.findById(dto.getClubId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));


        Participation participation = Participation.builder()
                .introduce(dto.getIntroduce())
                .name(dto.getName())
                .grade(dto.getGrade())
                .classNo(dto.getClassNo())
                .studentNo(dto.getStudentNo())
                .status(dto.getStatus())
                .type(dto.getType())
                .user(user)
                .club(club)
                .created_at(LocalDateTime.now())
                .build();
        participationRepository.save(participation);
        Map<String, String> response = new HashMap<>();
        response.put("message", "동아리 모집에 참가 신청 되었습니다.");
        return response;
    }


    public UpdateMentoringStatusDto updateMentoringStatus(Long participationId, Participation.Status status, User user) {

        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PART_MENTORING_NOT_FOUND));

        if (!participation.getProject().getUser().getUser_id().equals(user.getUser_id())) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        participation.setStatus(status);
        participation.setCreated_at(LocalDateTime.now());
        participationRepository.save(participation);

        return new UpdateMentoringStatusDto(participation.getStatus());
    }

    public UpdateClubStatusDto updateClubStatus(Long participationId, Participation.Status status, User user){

        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PART_CLUB_NOT_FOUND));

        if (!participation.getClub().getUser().getUser_id().equals(user.getUser_id())) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        participation.setStatus(status);
        participation.setCreated_at(LocalDateTime.now());
        participationRepository.save(participation);

        return new UpdateClubStatusDto(participation.getStatus());
    }

    public List<MyParticipationDto> getMyParticipations(User user) {

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        List<Participation> participations = participationRepository.findByUser(user);


        return participations.stream()
                .map(participation -> MyParticipationDto.builder()
                        .part_id(participation.getPart_id().toString())
                        .introduce(participation.getIntroduce())
                        .type(participation.getType())
                        .status(participation.getStatus())
                        .position(participation.getPosition())
                        .build())
                .collect(Collectors.toList());
    }
}
