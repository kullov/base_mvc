package com.example.common.http;

import lombok.*;

/**
 * The type Paging request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
