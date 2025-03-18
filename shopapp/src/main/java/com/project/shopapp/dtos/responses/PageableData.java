package com.project.shopapp.dtos.responses;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableData {
    int pageNumber;
    int pageSize;
    int totalPage;
    long totalRecord;

    public static PageableData from(Page<?> page) {
        final var pageableData = new PageableData();
        pageableData.setPageNumber(page.getNumber() + 1);
        pageableData.setPageSize(page.getSize());
        pageableData.setTotalPage(page.getTotalPages());
        pageableData.setTotalRecord(page.getTotalElements());
        return pageableData;
    }
}
