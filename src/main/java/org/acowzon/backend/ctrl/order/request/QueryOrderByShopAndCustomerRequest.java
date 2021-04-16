package org.acowzon.backend.ctrl.order.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class QueryOrderByShopAndCustomerRequest {
    UUID shopId;
    UUID customerId;
}
