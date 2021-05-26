package org.acowzon.backend.service.search.impl;

import org.acowzon.backend.ctrl.search.request.PageQueryRequest;
import org.acowzon.backend.ctrl.search.request.QueryItem;
import org.acowzon.backend.elaticsearch.Goods;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.search.SearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SearchServiceImpl implements SearchService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestHighLevelClient elasticsearchClient;

    @Override
    public List<Goods> search(PageQueryRequest query) throws BusinessException {
        logger.info("开始搜索 " + query);
        return conditionSearch(query);
    }

    /**
     * 根据请求参数解析出最终的查询条件
     * @param queryRequest 查询请求的参数
     * @return 查询到的结果
     * @throws BusinessException 业务异常
     */
    private List<Goods> conditionSearch(PageQueryRequest queryRequest) throws BusinessException {
        try {
            // 指定搜索条件
            SearchSourceBuilder builder = new SearchSourceBuilder();
            // 分析queryRequest
            QueryItem matchArr = queryRequest.getMatchArr();
            String keyword = matchArr.getKeyword();
            String method = matchArr.getMethod();
            Map<String, Object> range = matchArr.getRange();
            Map<String, Object> sort = matchArr.getSort();

            Integer page = queryRequest.getPage();
            Integer size = queryRequest.getSize();

            // 判断如果为SINGLE表示单条件查询，使用matchQuery.fieldNames只有一个元素，直接取出0即可
            if (method.equals("SINGLE")) builder.query(QueryBuilders.matchQuery("name", keyword));
            // 如果g为false表示多条件查询，使用multiMatchQuery
            else if (method.equals("MULTI")) builder.query(QueryBuilders.multiMatchQuery(keyword, "name", "simpleDes"));
            else throw new BusinessException("无法识别的查询方式");

            // 范围查询
            if (range != null && !range.isEmpty()) builder.postFilter(QueryBuilders.rangeQuery(range.get("field").toString())
                    .lte(range.get("max"))
                    .gte(range.get("min")));

            // 查询文档条数
            SearchRequest request = new SearchRequest("goods").types("goods").source(builder);
            long count = elasticsearchClient.search(request, RequestOptions.DEFAULT).getHits().getTotalHits();
            // 通过页数获取起始位置
            page = (page-1) * size;
            logger.info("查询到的个数为:" + count);
            // 如果页码不合法就返回错误
            if (page < 0 || page >= count) throw new BusinessException("页数有误");

            // 增加排序条件
            if (sort != null && !sort.isEmpty()) {
                String field = sort.get("field").toString();
                if (sort.get("type").equals("DESC")) builder.sort(field, SortOrder.DESC);
                else if (sort.get("type").equals("ASC")) builder.sort(field, SortOrder.ASC);
                else throw new BusinessException("排序方式无效");
            }

            // 增加其他条件查询
            builder
                    .from(page) // 起始位置
                    .size(size) // 页面大小
                    .highlighter(new HighlightBuilder().field("*")  // 高亮查询
                            .requireFieldMatch(false)
                            .preTags("<span style='color:red'>")
                            .postTags("</span>"));
            // 查询请求
            SearchRequest searchRequest = new SearchRequest("goods").types("goods").source(builder);
            SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            logger.info("过滤后的个数:" + response.getHits().totalHits);
            // 解析获得数据
            return parseResponse(response);

        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("查询失败");
        }
    }

    private List<Goods> parseResponse(SearchResponse response){
        List<Goods> goodsList = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Goods goods = new Goods();
            // 获取各个字段的值
            Map<String, Object> map = hit.getSourceAsMap();
            goods.setId(UUID.fromString(map.get("id").toString()))
                    .setName(map.get("name").toString())
                    .setImageUrl(map.get("imageUrl").toString())
                    .setPrice((Double) map.get("price"))
                    .setSimpleDes(map.get("simpleDes").toString())
                    .setInventory((Integer) map.get("inventory"))
                    .setSoldCount((Integer) map.get("soldCount"));
            // 高亮处理
            Map<String, HighlightField> highLightMap = hit.getHighlightFields();
            if (highLightMap.containsKey("name")) goods.setName(highLightMap.get("name").fragments()[0].toString());
            if (highLightMap.containsKey("simpleDes")) goods.setSimpleDes(highLightMap.get("simpleDes").fragments()[0].toString());
            goodsList.add(goods);
        }
        return goodsList;
    }
}
