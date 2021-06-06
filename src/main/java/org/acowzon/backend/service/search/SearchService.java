package org.acowzon.backend.service.search;

import org.acowzon.backend.ctrl.search.request.PageQueryRequest;
import org.acowzon.backend.elaticsearch.Goods;
import org.acowzon.backend.exception.BusinessException;

import java.util.List;

public interface SearchService {

    /**
     * 综合查询
     * @param query 查询的请求参数
     * @return 查询结果
     * @throws BusinessException 业务异常
     */
    List<Goods> search(PageQueryRequest query) throws BusinessException;
}