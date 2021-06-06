package org.acowzon.backend.elaticsearch.dao;

import org.acowzon.backend.elaticsearch.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * PoemRepository es的一个类
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {
}
