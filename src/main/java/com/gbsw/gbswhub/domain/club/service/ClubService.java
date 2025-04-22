package com.gbsw.gbswhub.domain.club.service;

import com.gbsw.gbswhub.domain.club.db.CreateClubDto;
import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.club.repository.ClubRepository;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public Map<String, String> createClub(CreateClubDto dto, User user) {

        if(user == null){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        Club club = Club.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .target(dto.getTarget())
                .type(dto.getType())
                .openDate(dto.getOpenDate())
                .closeDate(dto.getCloseDate())
                .user(user)
                .build();

        clubRepository.save(club);
        Map<String, String> response =  new HashMap<>();
        response.put("message", "동아리 모집이 생성되었습니다.");
        return response;
    }
}
