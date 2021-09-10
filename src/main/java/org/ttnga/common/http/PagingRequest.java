package org.ttnga.common.http;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Paging request.
 */
@Getter
@Setter
public class PagingRequest {
    /**
     * The Page.
     */
    Integer page;
    /**
     * The Size.
     */
    Integer size;
    /**
     * The Filter.
     */
    String filter;
    /**
     * The Sort by.
     */
    String sortBy;
    /**
     * The Sort desc.
     */
    boolean sortDesc;

}
