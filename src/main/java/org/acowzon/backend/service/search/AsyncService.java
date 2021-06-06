package org.acowzon.backend.service.search;

import org.acowzon.backend.dao.goods.GoodsDAO;
import org.acowzon.backend.elaticsearch.Goods;
import org.acowzon.backend.elaticsearch.dao.GoodsRepository;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsyncService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsDAO goodsDAO;

    @Async
    public void initData() {
        logger.info("删除索引");
        // 先删除索引中的所有文档
        goodsRepository.deleteAll();
        // 再重新从数据库中读取所有记录，重新添加到es索引库中
        List<GoodsEntity> list = goodsDAO.findAll();
        List<Goods> goodsList = new ArrayList<>();
        list.forEach(goods->goodsList.add(Goods.parse(goods)));
        logger.info("重新导入数据");
        goodsRepository.saveAll(goodsList);
    }

    // 每天的2点和14点定时触发
    @Scheduled(cron = "* 2,14 * * *")
    public void buildElasticsearch(){
        initData();
    }
}
