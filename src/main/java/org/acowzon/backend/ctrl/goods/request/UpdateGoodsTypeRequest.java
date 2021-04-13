package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateGoodsTypeRequest {
    private UUID goodsTypeId; // 类别id

    @NotNull
    private String goodsType;   // 类别
}
