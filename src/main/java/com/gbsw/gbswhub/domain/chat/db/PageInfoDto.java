package com.gbsw.gbswhub.domain.chat.db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfoDto {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;

    public PageInfoDto (int page, int size, int totalElements, int totalPages) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
