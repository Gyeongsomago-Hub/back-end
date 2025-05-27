package com.gbsw.gbswhub.domain.club.service;

import com.gbsw.gbswhub.domain.club.db.ClubDto;
import com.gbsw.gbswhub.domain.club.db.ClubResponseDto;
import com.gbsw.gbswhub.domain.club.db.CreateClubDto;
import com.gbsw.gbswhub.domain.club.db.UpdateClubDto;
import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.club.repository.ClubRepository;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public Map<String, String> createClub(CreateClubDto dto, User user) {

        validateUser(user);

        if(!user.getRole().equals(User.Role.CLUB_LEADER)){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
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

    @Transactional(readOnly = true)
    public List<ClubDto> getAllClub() {
        List<Club> clubs = clubRepository.findAll();

        return clubs.stream()
                .map(club -> {

                    return new ClubDto(
                            club.getId(),
                            club.getName(),
                            club.getDescription(),
                            club.getLocation(),
                            club.getTarget(),
                            club.getType(),
                            club.getOpenDate(),
                            club.getCloseDate(),
                            club.getUser().getUser_id(),
                            club.getUser().getName()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClubDto getClubById(Long id){
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        return new ClubDto(
                club.getId(),
                club.getName(),
                club.getDescription(),
                club.getLocation(),
                club.getTarget(),
                club.getType(),
                club.getOpenDate(),
                club.getCloseDate(),
                club.getUser().getUser_id(),
                club.getUser().getName()
        );
    }

    public ClubResponseDto updateClub(Long ClubId, UpdateClubDto dto, User user){
        validateUser(user);

        Club club = clubRepository.findById(ClubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        if(!user.getRole().equals(User.Role.CLUB_LEADER)){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        club.setName(dto.getName());
        club.setDescription(dto.getDescription());
        club.setLocation(dto.getLocation());
        club.setTarget(dto.getTarget());
        club.setType(dto.getType());
        club.setOpenDate(dto.getOpenDate());
        club.setCloseDate(dto.getCloseDate());
        clubRepository.save(club);

        return new ClubResponseDto(
                club.getId(),
                club.getName(),
                club.getDescription(),
                club.getLocation(),
                club.getTarget(),
                club.getType(),
                club.getOpenDate(),
                club.getCloseDate(),
                club.getUser().getUser_id()
        );
    }

    public void deleteClub(Long id, User user){
        validateUser(user);

        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        if(!user.getRole().equals(User.Role.CLUB_LEADER)){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        clubRepository.delete(club);
    }
}
