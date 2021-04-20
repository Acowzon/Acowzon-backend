package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class QueryGoodsRequest {

    /**
     * 查询条件map
     */
    Map<String,Object> queryMap;
}
