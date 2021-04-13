package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ListGoodsByRetailerRequest {

    /**
     * 商家Id
     */
    private UUID retailerId;
}
