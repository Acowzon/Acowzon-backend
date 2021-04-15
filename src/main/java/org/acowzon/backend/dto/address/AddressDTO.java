package org.acowzon.backend.dto.address;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.address.AddressEntity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AddressDTO {

    private UUID id;   // 地址id

    private String country;  // 国家

    private String province; // 省

    private String city;     // 市

    private String detail;  // 具体地址

    static public AddressDTO parseDTO(AddressEntity entity) {
        AddressDTO dto = new AddressDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
}
