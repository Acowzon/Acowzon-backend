package org.acowzon.backend.service.user.impl;

import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.dto.user.UserBasicInfoDTO;
import org.acowzon.backend.dto.user.UserFullInfoDTO;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    /**
     * 获取用户的基本信息
     *
     * @param id 用户id
     * @return UserBasicInfoDTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public UserBasicInfoDTO getUserBasicInfo(UUID id) throws BusinessException {
        return null;
    }

    /**
     * 获取用户的全部信息
     *
     * @param id 用户id
     * @return UserFullInfoDTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public UserFullInfoDTO getUserFullInfo(UUID id) throws BusinessException {
        return null;
    }

    /**
     * 列出所有用户
     *
     * @return UserBasicInfoDTO[]
     */
    @Override
    public UserBasicInfoDTO[] listAllUser() {
        return new UserBasicInfoDTO[0];
    }

    /**
     * 查询用户
     *
     * @return UserBasicInfoDTO[]
     */
    @Override
    public UserBasicInfoDTO[] queryUser() {
        return new UserBasicInfoDTO[0];
    }

    /**
     * 登录
     *
     * @param id       用户id
     * @param password 登录密码
     * @return 登录状态
     * @throws BusinessException 业务相关异常
     */
    @Override
    public boolean login(UUID id, String password) throws BusinessException {
        return false;
    }

    /**
     * 注册用户
     *
     * @param userFullInfoDTO 用户信息DTO
     * @return 生成的uuid
     * @throws BusinessException 业务相关异常
     */
    @Override
    public UUID addUser(UserFullInfoDTO userFullInfoDTO) throws BusinessException {
        return null;
    }

    /**
     * 更新用户信息
     *
     * @param userFullInfoDTO 用户信息DTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updateUser(UserFullInfoDTO userFullInfoDTO) throws BusinessException {

    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void deleteUser(UUID id) throws BusinessException {

    }

    /**
     * 设置用户卖家权限
     *
     * @param id       用户id
     * @param isSeller 是否有卖家权限
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void setUserSeller(UUID id, boolean isSeller) throws BusinessException {

    }

    /**
     * 添加用户地址
     *
     * @param id         用户id
     * @param addressDTO 地址DTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void addUserAddress(UUID id, AddressDTO addressDTO) throws BusinessException {

    }

    /**
     * 删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void deleteUserAddress(UUID userId, UUID addressId) throws BusinessException {

    }
}
