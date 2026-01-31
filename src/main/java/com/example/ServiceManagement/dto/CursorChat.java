package com.example.ServiceManagement.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CursorChat<T> {

    private List<T> items;
    private LocalDateTime nextCreatedAt;
    private Long nextId;
    private boolean hasMore;

    public CursorChat(List<T> items,
                      LocalDateTime nextCreatedAt,
                      Long nextId,
                      boolean hasMore) {
        this.items = items;
        this.nextCreatedAt = nextCreatedAt;
        this.nextId = nextId;
        this.hasMore = hasMore;
    }
}
