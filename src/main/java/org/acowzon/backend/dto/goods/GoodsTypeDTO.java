package org.acowzon.backend.dto.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GoodsTypeDTO {
    private UUID id; // 类别id
    private String name;   // 类别

    static public GoodsTypeDTO parseDTO(GoodsTypeEntity entity) {
        GoodsTypeDTO dto = new GoodsTypeDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
