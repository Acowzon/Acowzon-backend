package org.acowzon.backend.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBasicInfoDTO {

    private String id; // 用户id

    private String userName;    // 登录用户名

    private String nickName;  // 用户昵称

    private String imageUrl;   // 用户头像的路径
}
