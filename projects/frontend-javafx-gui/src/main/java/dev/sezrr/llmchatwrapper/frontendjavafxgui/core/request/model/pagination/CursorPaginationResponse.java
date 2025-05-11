package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.pagination;

import java.util.UUID;

public class CursorPaginationResponse<T> extends PaginationResponse<T> {
    private UUID nextCursor;

    public CursorPaginationResponse() {
        super();

    }

    public CursorPaginationResponse(T content, boolean hasNext, UUID nextCursor) {
        super(content, hasNext);
        this.nextCursor = nextCursor;
    }

    public UUID getNextCursor() {
        return nextCursor;
    }
}
