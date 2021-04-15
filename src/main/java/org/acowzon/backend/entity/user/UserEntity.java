package org.acowzon.backend.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.order.OrderEntity;
import org.acowzon.backend.enums.SexEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class UserEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type="uuid-char")
    private UUID id;  // 用户id

    @NotEmpty
    @Length(max = 32)
    private String userName;    // 登录用户名

    @NotEmpty
    @Length(max = 32)
    private String password;     // 登录密码

    @NotEmpty
    @Length(max = 5)
    private String realName;    // 用户真实姓名

    @NotEmpty
    @Length(max = 32)
    private String nickname;    // 用户昵称

    @NotEmpty
    @Lob
    @Column(columnDefinition="TEXT")
    private String imageUrl;   // 用户头像的路径

    @NotEmpty
    @Length(max = 16)
    private String tel;   // 用户电话

    @NotEmpty
    private String email;   // 用户邮箱

    @Enumerated
    private SexEnum sex; // 用户性别 男 女

    private Date birthDay; // 用户生日

    private boolean isSeller;   // 该用户是否是卖家

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval = true)
    @JoinTable(name = "r_user_address",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<AddressEntity> address;   // 用户地址

    private Date createTime;    // 用户创建时间

    private Date updateTime;    // 用户修改时间

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval = true)
    private Set<OrderEntity> orderSet;// 用户订单

    @Version
    private int version;

    public UserEntity(UUID id) {
        this.id = id;
    }
}
