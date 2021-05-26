package org.acowzon.backend.elaticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
// es创建索引的注解
@Document(indexName = "goods", indexStoreType = "goods")
public class Goods {
    @Id
    private UUID id;        // id
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;    // 商品名称
    @Field(type = FieldType.Keyword)
    private String imageUrl;    // 商品图片路径
    @Field(type = FieldType.Double)
    private double price;   // 商品价格
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String simpleDes; // 商品简单描述
    @Field(type = FieldType.Integer)
    private Integer inventory;  // 库存
    @Field(type = FieldType.Integer)
    private Integer soldCount;  // 售出数量

    public static Goods parse(GoodsEntity goods){
        return new Goods()
                .setId(goods.getId())
                .setName(goods.getName())
                .setImageUrl(goods.getImageUrl())
                .setPrice(goods.getPrice())
                .setInventory(goods.getInventory())
                .setSimpleDes(goods.getSimpleDes())
                .setSoldCount(goods.getSoldCount());
    }
}
