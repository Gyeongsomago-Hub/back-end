package com.gbsw.gbswhub.domain.chat.db;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfoDto pageInfoDto;
}
