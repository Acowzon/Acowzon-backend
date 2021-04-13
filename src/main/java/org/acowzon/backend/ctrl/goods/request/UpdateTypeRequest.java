package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateTypeRequest {
    private UUID id; // 类别id

    @NotNull
    @Column(unique = true)
    private String name;   // 类别
}
