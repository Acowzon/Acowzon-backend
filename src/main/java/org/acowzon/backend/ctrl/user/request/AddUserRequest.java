package org.acowzon.backend.ctrl.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.enums.SexEnum;

import java.util.Date;

@Data
@NoArgsConstructor
public class AddUserRequest {

    private String realName;    // 用户真实姓名

    private String userName;    // 登录用户名

    private String password;

    private String nickName;  // 用户昵称

    private String imageUrl;   // 用户头像的路径

    private String tel;   // 用户电话

    private String email;   // 用户邮箱

    private SexEnum sex; // 用户性别 男 女

    private Date birthDay; // 用户生日
}
