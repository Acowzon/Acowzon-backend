package org.acowzon.backend.dto.goods;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GoodsTypeDTO {
    private UUID id; // 类别id
    private String name;   // 类别
}
