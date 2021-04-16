package org.acowzon.backend.ctrl.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateUserPasswordRequest {

    private UUID id;

    private String password;
    
}
