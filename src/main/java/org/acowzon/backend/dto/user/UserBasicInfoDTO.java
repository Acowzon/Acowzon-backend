package org.acowzon.backend.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.user.UserEntity;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserBasicInfoDTO {

    private String id; // 用户id

    private String userName;    // 登录用户名

    private String nickName;  // 用户昵称

    private String imageUrl;   // 用户头像的路径

    static public UserBasicInfoDTO parseDTO(UserEntity entity) {
        UserBasicInfoDTO dto = new UserBasicInfoDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
}
