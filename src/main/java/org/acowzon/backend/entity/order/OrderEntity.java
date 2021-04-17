package org.acowzon.backend.entity.order;

import lombok.*;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.acowzon.backend.enums.OrderStatusEnum;
import org.acowzon.backend.enums.PaymentStatusEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_order")
@EqualsAndHashCode(exclude = {"customer","shop"})
public class OrderEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type="uuid-char")
    private UUID id; // 订单id

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OrderItemEntity> items;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity customer;  // 用户

    /**
     * 订单绑定的商家不是双向依赖，订单主要还是绑定在用户这边
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ShopEntity shop;  // 商家

    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY,optional = false)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private AddressEntity destAddress; // 收货地址

    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY,optional = false)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private AddressEntity originAddress; // 发货地址

    private double orderPrice;  // 订单价格

    @Enumerated
    private OrderStatusEnum orderStatus; // 订单状态 0未支付 1已支付 2取消订单

    @Enumerated
    private PaymentStatusEnum paymentStatus; // 支付状态

    private Date createTime; // 订单创建的时间

    /** 更新时间 **/
    private Date updateTime;

    @Version
    private int version;

    public OrderEntity(UUID id) {
        this.id = id;
    }
}
