package com.gbsw.gbswhub.domain.participation.service;

import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.club.repository.ClubRepository;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.participation.db.*;
import com.gbsw.gbswhub.domain.participation.db.club.RequestClubDto;
import com.gbsw.gbswhub.domain.participation.db.club.ResponseClubDto;
import com.gbsw.gbswhub.domain.participation.db.club.UpdateClubStatusDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.RequestMentoringDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.ResponseMentoringDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.UpdateMentoringStatusDto;
import com.gbsw.gbswhub.domain.participation.db.project.RequestProjectDto;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import com.gbsw.gbswhub.domain.project.db.ProjectRepository;
import com.gbsw.gbswhub.domain.project.model.Project;
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

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final ProjectRepository projectRepository;
    private final ClubRepository clubRepository;

    public Map<String, String> RequestProject(RequestProjectDto dto, User user) {

        validateUser(user);

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
        validateUser(user);

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MENTORING_NOT_FOUND));

        if (project.getType() != Project.Type.MENTORING) {
            throw new BusinessException(ErrorCode.MENTORING_NOT_FOUND);
        }

        boolean alreadyRequested = participationRepository.existsByUserAndProject(user, project);

        if(alreadyRequested) {
            throw new BusinessException(ErrorCode.ALREADY_PARTICIPATED);
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
        validateUser(user);

        Club club = clubRepository.findById(dto.getClubId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        boolean alreadyRequested = participationRepository.existsByUserAndClub(user, club);

        if(alreadyRequested){
            throw new BusinessException(ErrorCode.ALREADY_PARTICIPATED);
        }

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

    @Transactional(readOnly = true)
    public List<MyParticipationDto> getMyParticipations(User user) {
        validateUser(user);

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


    public void deleteParticipation(Long id, User user) {

        validateUser(user);

        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PART_NOT_FOUND));

        if (!participation.canBeDeletedBy(user)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        participationRepository.delete(participation);
    }

    public List<ResponseClubDto> getRequestByClub(User user, Long clubId) {

        Club club = clubRepository.findById(clubId).orElseThrow(()-> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        if(!club.isLeader(user)){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        List<Participation> participations = participationRepository.findAllByClub_Id(clubId);

        return participations.stream()
                .map(participation -> ResponseClubDto.builder()
                        .id(participation.getPart_id())
                        .introduce(participation.getIntroduce())
                        .name(participation.getName())
                        .grade(participation.getGrade())
                        .classNo(participation.getClassNo())
                        .studentNo(participation.getStudentNo())
                        .clubId(participation.getClub().getId())
                        .type(participation.getType())
                        .status(participation.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponseMentoringDto> getRequestByMentoring(User user, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENTORING_NOT_FOUND));

        if(!project.isOwner(user)){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        List<Participation> participations = participationRepository.findAllByProject_Id(projectId);

        return participations.stream()
                .map(participation -> ResponseMentoringDto.builder()
                        .id(participation.getPart_id())
                        .introduce(participation.getIntroduce())
                        .grade(participation.getGrade())
                        .position(participation.getPosition())
                        .classNo(participation.getClassNo())
                        .studentNo(participation.getStudentNo())
                        .name(participation.getName())
                        .type(participation.getType())
                        .status(participation.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
