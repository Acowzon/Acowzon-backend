package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateInventoryRequest {

    @NotNull
    UUID id;

    @NotNull
    int inventory;

    boolean isAbsolute = true;
}
