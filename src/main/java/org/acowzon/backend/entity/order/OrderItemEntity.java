package org.acowzon.backend.entity.order;

import lombok.*;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_order_item")
@EqualsAndHashCode(exclude = {"order"})
public class OrderItemEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type="uuid-char")
    private UUID id; // 订单商品id

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private GoodsEntity goods; // 商品

    private int amount; // 商品数量

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private OrderEntity order; // 所属订单

    public OrderItemEntity(UUID id) {
        this.id = id;
    }
}
