package org.acowzon.backend.ctrl.order.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.enums.OrderStatusEnum;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateOrderStatusRequest {
    UUID id;
    OrderStatusEnum orderStatus;
}
