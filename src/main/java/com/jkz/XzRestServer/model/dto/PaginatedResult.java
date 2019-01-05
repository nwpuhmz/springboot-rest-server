package com.jkz.XzRestServer.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by scuhmz on 2017/11/9.
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaginatedResult implements Serializable {


    private static final long serialVersionUID = -8909803501603453461L;
    private int currentPage; // Current page number
    private int totalPage; // Number of total pages
    private int totalCount;//Number of total counts
    private Object data; // Paginated resources

}
