package org.acowzon.backend.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "t_goods")
public class GoodsEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type="uuid-char")
    private UUID goodsId; // 商品id

    @NotEmpty
    private String goodsName; // 商品名称

    @NotNull
    @Type(type="uuid-char")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "goods_type_goods_type_relation")
    private GoodsTypeEntity goodsType; // 商品类型

    @NotNull
    private double goodsPrice; // 商品价格

    @NotEmpty
    @Lob
    @Column(columnDefinition="TEXT")
    private String goodsImage; // 商品图片的存储地址

    @NotEmpty
    private String goodsSimpleDes; // 商品的简单描述

    @NotEmpty
    private String goodsDescription; // 商品的描述

    @NotNull
    @Min(0)
    private int goodsInventory = 0; // 商品库存

    @NotNull
    @Min(0)
    private int soldCount = 0; // 商品售出个数

    @NotNull
    private Date createTime; // 商品创建时间

    @NotNull
    private Date updateTime; // 商品修改时间

    @NotNull
    @Type(type="uuid-char")
    private UUID retailerId; // 商品上架商家的id

    @Column()
    @Min(0)
    private int goodsStarsCount=0; // 喜欢此商品的人数

    @Column()
    @Min(0)
    private int views=0; // 商品的浏览数

}