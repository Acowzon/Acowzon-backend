package org.acowzon.backend.dto.shop;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.dto.goods.GoodsTypeDTO;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ShopDetailDTO {

    private UUID id;  // 商铺id

    private String name; // 商家名称

    private String imageUrl; // 图片url

    private UUID ownerId; // 店主id

    private Set<UUID> adminId; // 管理员id

    private Set<AddressDTO> addressSet; // 商铺地址

    static public ShopDetailDTO parseDTO(ShopEntity entity) {
        ShopDetailDTO dto = new ShopDetailDTO();
        BeanUtils.copyProperties(entity,dto);
        dto.setOwnerId(entity.getOwner().getId());
        dto.setAdminId(entity.getAdmin().stream().map(UserEntity::getId).collect(Collectors.toSet()));
        dto.setAddressSet(entity.getAddress().stream().map(AddressDTO::parseDTO).collect(Collectors.toSet()));
        return dto;
    }

}
