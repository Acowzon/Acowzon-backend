package org.acowzon.backend.ctrl.order.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.enums.PaymentStatusEnum;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdatePaymentStatusRequest {

    UUID id;
    PaymentStatusEnum paymentStatus;
}
