package org.acowzon.backend.ctrl.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VerifyUserInfoRequest {

    private String userName;

    private String password;

}
