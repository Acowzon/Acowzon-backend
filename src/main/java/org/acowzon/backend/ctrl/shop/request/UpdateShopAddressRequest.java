package org.acowzon.backend.ctrl.shop.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.dto.address.AddressDTO;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateShopAddressRequest {
    private UUID shopId;
    private AddressDTO address;
}
