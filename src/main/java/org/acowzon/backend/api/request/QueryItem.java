package org.acowzon.backend.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.enums.QueryMethodEnum;
import org.acowzon.backend.enums.QueryPredicateEnum;
import org.acowzon.backend.enums.QuerySortEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryItem {
    String attr;
    Object[] values;
    QueryMethodEnum method;
    QueryPredicateEnum predicate;
    QuerySortEnum sort;
}
