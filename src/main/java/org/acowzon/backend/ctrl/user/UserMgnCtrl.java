package org.acowzon.backend.ctrl.user;


import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.UUIDParamRequest;
import org.acowzon.backend.ctrl.user.request.AddUserRequest;
import org.acowzon.backend.ctrl.user.request.UpdateUserAddressRequest;
import org.acowzon.backend.ctrl.user.request.UpdateUserPasswordRequest;
import org.acowzon.backend.ctrl.user.request.VerifyUserInfoRequest;
import org.acowzon.backend.dto.user.UserFullInfoDTO;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.user.UserMgnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("user")
public class UserMgnCtrl {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMgnService userMgnService;

    @PostMapping("/list/all")
    public DefaultWebResponse listAllUser() {
        return DefaultWebResponse.Builder.success(userMgnService.listAllUser());
    }

    @PostMapping("/basicInfo")
    public DefaultWebResponse getBasicInfo(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(userMgnService.getUserBasicInfo(request.getId()));
    }

    @PostMapping("/detail")
    public DefaultWebResponse getDetailInfo(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(userMgnService.getUserFullInfo(request.getId()));
    }

    @PostMapping(value = "/verify")
    public DefaultWebResponse verify(@RequestBody VerifyUserInfoRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        boolean accept = userMgnService.verifyUser(request.getUserName(), request.getPassword());
        if (accept) {
            return DefaultWebResponse.Builder.success("accept_user");
        } else {
            return DefaultWebResponse.Builder.fail("reject_user");
        }
    }

    @PostMapping(value = "/add")
    public DefaultWebResponse addUser(@RequestBody AddUserRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        UserFullInfoDTO userFullInfoDTO = new UserFullInfoDTO();
        BeanUtils.copyProperties(request,userFullInfoDTO);
        return DefaultWebResponse.Builder.success("add_user_success",userMgnService.addUser(userFullInfoDTO, request.getPassword()));
    }

    @PostMapping(value = "/update")
    public DefaultWebResponse updateUser(@RequestBody UserFullInfoDTO request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        userMgnService.updateUser(request);
        return DefaultWebResponse.Builder.success("update_user_info_success");
    }

    @PostMapping(value = "/delete")
    public DefaultWebResponse deleteUser(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        userMgnService.deleteUser(request.getId());
        return DefaultWebResponse.Builder.success("delete_user_success");
    }

    @PostMapping(value = "/updatePassword")
    public DefaultWebResponse updatePassword(@RequestBody UpdateUserPasswordRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        userMgnService.updateUserPassword(request.getId(),request.getPassword());
        return DefaultWebResponse.Builder.success("update_user_password_success");
    }

    @PostMapping(value = "/addAddr")
    public DefaultWebResponse addAddress(@RequestBody UpdateUserAddressRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        userMgnService.addUserAddress(request.getUserId(),request.getAddress());
        return DefaultWebResponse.Builder.success("add_user_address_success");
    }

    @PostMapping(value = "/deleteAddr")
    public DefaultWebResponse deleteAddress(@RequestBody UpdateUserAddressRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        userMgnService.deleteUserAddress(request.getUserId(),request.getAddress().getId());
        return DefaultWebResponse.Builder.success("delete_user_address_success");
    }
}
