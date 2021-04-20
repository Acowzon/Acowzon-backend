package org.acowzon.backend.ctrl.shop.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateShopOwnerRequest {
    private UUID shopId;
    private UUID ownerId;
}
