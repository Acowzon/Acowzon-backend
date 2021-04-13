package org.acowzon.backend.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "t_goods_type")
public class GoodsTypeEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type="uuid-char")
    @NotNull
    private UUID goodsTypeId; // 类别id

    @NotEmpty
    @Column(unique = true)
    private String goodsType;   // 类别
}
