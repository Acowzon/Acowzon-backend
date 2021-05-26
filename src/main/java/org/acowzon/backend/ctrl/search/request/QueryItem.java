package org.acowzon.backend.ctrl.search.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryItem implements Serializable {
    String method;  // 查询的方法，只有两种: SINGLE 单字段查询  MULTI 多字段查询
    String keyword; // 查询的关键字
    Map<String, Object> range;  // 范围查询
    Map<String, Object> sort;   // 排序
}
