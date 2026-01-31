package com.example.ServiceManagement.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CursorPage<T> {

    private List<T> items;
    private Long nextCursor;
    private boolean hasMore;

    public CursorPage(List<T> items, Long nextCursor, boolean hasMore) {
        this.items = items;
        this.nextCursor = nextCursor;
        this.hasMore = hasMore;
    }

}
