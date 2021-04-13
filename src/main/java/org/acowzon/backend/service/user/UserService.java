package org.acowzon.backend.service.user;


import org.acowzon.backend.dto.user.UserDTO;
import org.acowzon.backend.entity.user.User;


public interface UserService {

    User getUserInfo(String userID);

    int updateUserInfo(User user);

    UserDTO login(String userID, String password);

    int register(User user);
}
