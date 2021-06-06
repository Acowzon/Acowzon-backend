package org.acowzon.backend.ctrl.search.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryRequest implements Serializable {
    QueryItem matchArr;
    Integer size;
    Integer page;
}
