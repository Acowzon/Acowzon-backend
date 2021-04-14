package org.acowzon.backend.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.enums.SexEnum;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserFullInfoDTO {

    private String id; // 用户id

    private String realName;    // 用户真实姓名

    private String userName;    // 登录用户名

    private String nickName;  // 用户昵称

    private String imageUrl;   // 用户头像的路径

    private String tel;   // 用户电话

    private String email;   // 用户邮箱

    private SexEnum sex; // 用户性别 男 女

    private Date birthDay; // 用户生日

    private boolean isSeller;   // 该用户是否是卖家

    private Date createTime;    // 用户创建时间

    private Date updateTime;    // 用户修改时间

    private List<AddressEntity> address;   // 用户地址

}
