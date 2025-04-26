package dev.sezrr.examples.llmchatservice.shared.custom_response_entity.pagination;

/**
 * A generic class representing a paginated response.
 *
 * @param <T> the type of the content
 */
public abstract class PaginationResponse<T> {
    private final T content;
    private final boolean hasMore;
    
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
