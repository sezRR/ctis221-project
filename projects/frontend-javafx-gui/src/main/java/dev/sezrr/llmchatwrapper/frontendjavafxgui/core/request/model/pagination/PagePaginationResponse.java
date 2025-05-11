package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.pagination;

import java.io.Serializable;

public class PagePaginationResponse<T extends Serializable> extends PaginationResponse<T> {
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagePaginationResponse(T content, boolean hasNext, int page, int size, long totalElements, int totalPages) {
        super(content, hasNext);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
