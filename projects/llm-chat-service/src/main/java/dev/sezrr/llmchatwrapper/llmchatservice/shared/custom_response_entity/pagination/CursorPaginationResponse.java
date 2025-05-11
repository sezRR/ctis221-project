package dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.pagination;

import java.util.UUID;

public class CursorPaginationResponse<T> extends PaginationResponse<T> {
    private final UUID nextCursor;

    public CursorPaginationResponse(T content, boolean hasNext, UUID nextCursor) {
        super(content, hasNext);
        this.nextCursor = nextCursor;
    }

    public UUID getNextCursor() {
        return nextCursor;
    }
}
