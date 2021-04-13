package org.acowzon.backend.ctrl.goods.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UUIDParamRequest {

    /**
     * id
     */
    private UUID id;
}
