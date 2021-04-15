package org.acowzon.backend.ctrl.shop.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateShopAdminRequest {
    private UUID shopId;
    private UUID adminId;
}
