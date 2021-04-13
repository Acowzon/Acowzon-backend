package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateGoodsInventoryRequest {

    @NotNull
    UUID goodsId;

    @NotNull
    int inventory;

    boolean isAbsolute = true;
}
