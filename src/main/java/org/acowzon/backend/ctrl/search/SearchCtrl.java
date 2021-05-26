package org.acowzon.backend.ctrl.search;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.search.request.PageQueryRequest;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.search.AsyncService;
import org.acowzon.backend.service.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("search")
public class SearchCtrl {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SearchService searchService;
    @Autowired
    AsyncService asyncService;

    @PostMapping("goods/init")
    public DefaultWebResponse initGoods(){
        asyncService.initData();
        return DefaultWebResponse.Builder.success("ok");
    }

    @PostMapping("goods")
    public DefaultWebResponse test(@RequestBody PageQueryRequest query) throws BusinessException {
        System.out.println(query);
        return DefaultWebResponse.Builder.success(searchService.search(query));
    }
}
