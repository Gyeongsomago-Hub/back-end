package com.gbsw.gbswhub.domain.participation.mapper;

import com.gbsw.gbswhub.domain.participation.db.MyParticipationDto;
import com.gbsw.gbswhub.domain.participation.db.club.ResponseClubDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.ResponseMentoringDto;
import com.gbsw.gbswhub.domain.participation.db.project.ResponseProjectDto;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParticipationMapper {

    public MyParticipationDto toMyParticipationDto(Participation participation) {
        return MyParticipationDto.builder()
                .part_id(participation.getPart_id().toString())
                .introduce(participation.getIntroduce())
                .type(participation.getType())
                .status(participation.getStatus())
                .position(participation.getPosition())
                .build();
    }

    public ResponseProjectDto toResponseProjectDto(Participation participation) {
        return ResponseProjectDto.builder()
                .id(participation.getPart_id())
                .position(participation.getPosition())
                .ProjectId(participation.getProject().getId())
                .type(participation.getType())
                .status(participation.getStatus())
                .build();
    }

    public ResponseClubDto toResponseClubDto(Participation participation) {
        return ResponseClubDto.builder()
                .id(participation.getPart_id())
                .introduce(participation.getIntroduce())
                .name(participation.getName())
                .grade(participation.getGrade())
                .classNo(participation.getClassNo())
                .studentNo(participation.getStudentNo())
                .clubId(participation.getClub().getId())
                .type(participation.getType())
                .status(participation.getStatus())
                .build();
    }

    public ResponseMentoringDto toResponseMentoringDto(Participation participation) {
        return ResponseMentoringDto.builder()
                .id(participation.getPart_id())
                .introduce(participation.getIntroduce())
                .grade(participation.getGrade())
                .position(participation.getPosition())
                .classNo(participation.getClassNo())
                .studentNo(participation.getStudentNo())
                .name(participation.getName())
                .type(participation.getType())
                .status(participation.getStatus())
                .build();
    }

    public List<MyParticipationDto> toMyParticipationDtoList(List<Participation> participations) {
        return participations.stream()
                .map(this::toMyParticipationDto)
                .collect(Collectors.toList());
    }

    public List<ResponseProjectDto> toResponseProjectDtoList(List<Participation> participations) {
        return participations.stream()
                .map(this::toResponseProjectDto)
                .collect(Collectors.toList());
    }
    public List<ResponseClubDto> toResponseClubDtoList(List<Participation> participations) {
        return participations.stream()
                .map(this::toResponseClubDto)
                .collect(Collectors.toList());
    }

    public List<ResponseMentoringDto> toResponseMentoringDtoList(List<Participation> participations) {
        return participations.stream()
                .map(this::toResponseMentoringDto)
                .collect(Collectors.toList());
    }
}