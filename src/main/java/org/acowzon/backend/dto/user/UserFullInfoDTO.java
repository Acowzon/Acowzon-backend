package org.acowzon.backend.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.entity.user.UserEntity;
import org.acowzon.backend.enums.SexEnum;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserFullInfoDTO {

    private UUID id; // 用户id

    private String realName;    // 用户真实姓名

    private String userName;    // 登录用户名

    private String nickName;  // 用户昵称

    private String imageUrl;   // 用户头像的路径

    private String tel;   // 用户电话

    private String email;   // 用户邮箱

    private SexEnum sex; // 用户性别 男 女

    private Date birthDay; // 用户生日

    private boolean isSeller = false;   // 该用户是否是卖家

    private Set<AddressDTO> addressSet;   // 用户地址

    static public UserFullInfoDTO parseDTO(UserEntity entity) {
        UserFullInfoDTO dto = new UserFullInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setAddressSet(entity.getAddress().stream().map(AddressDTO::parseDTO).collect(Collectors.toSet()));
        return dto;
    }
}
