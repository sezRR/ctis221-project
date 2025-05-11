package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.pagination;

/**
 * A generic class representing a paginated response.
 *
 * @param <T> the type of the content
 */
public abstract class PaginationResponse<T> {
    private T content;
    private boolean hasMore;

    public PaginationResponse() {
    }

    public PaginationResponse(T content, boolean hasMore) {
        this.content = content;
        this.hasMore = hasMore;
    }

    public T getContent() {
        return content;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}
