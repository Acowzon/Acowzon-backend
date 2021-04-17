package org.acowzon.backend.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_shop")
public class ShopEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type = "uuid-char")
    private UUID id;  // 商铺id

    @NotEmpty
    @Length(max = 16)
    private String name; // 商家名称

    @NotEmpty
    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl; // 图片url

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity owner; // 店主

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "r_shop_admin",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Set<UserEntity> admin; // 管理员

    @OneToMany(mappedBy = "shop", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<GoodsEntity> goodsSet; // 上架商品

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "r_shop_address",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Set<AddressEntity> address;

    private Date createTime;    // 商铺创建时间

    private Date updateTime;    // 商铺修改时间

    public ShopEntity(UUID id) {
        this.id = id;
    }
}
