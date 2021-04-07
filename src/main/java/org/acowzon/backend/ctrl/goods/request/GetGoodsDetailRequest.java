package org.acowzon.backend.ctrl.goods.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGoodsDetailRequest {
    private String goodsId;
}
