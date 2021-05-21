package org.acowzon.backend.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageQueryRequest {
    QueryItem[] matchArr;
    int pageSize;
    int pageNum;
}

