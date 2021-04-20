package org.acowzon.backend.ctrl.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.dto.address.AddressDTO;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateUserAddressRequest {
    private UUID userId;
    private AddressDTO address;
}
