package org.acowzon.backend.service.user;


import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.dto.user.UserBasicInfoDTO;
import org.acowzon.backend.dto.user.UserFullInfoDTO;
import org.acowzon.backend.exception.BusinessException;

import java.util.Optional;
import java.util.UUID;

public interface UserMgnService {

    /**
     * 获取用户的基本信息
     *
     * @param id 用户id
     * @return UserBasicInfoDTO
     * @throws BusinessException 业务相关异常
     */
    UserBasicInfoDTO getUserBasicInfo(UUID id) throws BusinessException;

    /**
     * 获取用户的全部信息
     *
     * @param id 用户id
     * @return UserFullInfoDTO
     * @throws BusinessException 业务相关异常
     */
    UserFullInfoDTO getUserFullInfo(UUID id) throws BusinessException;

    /**
     * 列出所有用户
     *
     * @return UserBasicInfoDTO[]
     */
    UserBasicInfoDTO[] listAllUser();

    /**
     * 查询用户
     *
     * @return UserBasicInfoDTO[]
     */
    UserBasicInfoDTO[] queryUser();

    /**
     * 验证用户信息
     *
     *
     * @param userName
     * @param password 登录密码
     * @return 登录状态
     * @throws BusinessException 业务相关异常
     */
    Optional<UUID> verifyUser(String userName, String password) throws BusinessException;

    /**
     * 注册用户
     *
     * @param userFullInfoDTO 用户信息DTO
     * @return 生成的uuid
     * @throws BusinessException 业务相关异常
     */
    UUID addUser(UserFullInfoDTO userFullInfoDTO, String password) throws BusinessException;

    /**
     * 更新用户信息
     *
     * @param userFullInfoDTO 用户信息DTO
     * @throws BusinessException 业务相关异常
     */
    void updateUser(UserFullInfoDTO userFullInfoDTO) throws BusinessException;

    /**
     * 删除用户
     *
     * @param id 用户id
     * @throws BusinessException 业务相关异常
     */
    void deleteUser(UUID id) throws BusinessException;

    /**
     * 设置用户卖家权限
     *
     * @param id       用户id
     * @param isSeller 是否有卖家权限
     * @throws BusinessException 业务相关异常
     */
    void setUserSeller(UUID id, boolean isSeller) throws BusinessException;

    /**
     * 修改用户密码
     *
     * @param id       用户id
     * @param password 新密码
     * @throws BusinessException 业务相关异常
     */
    void updateUserPassword(UUID id, String password) throws BusinessException;

    /**
     * 添加用户地址
     *
     * @param id         用户id
     * @param addressDTO 地址DTO
     * @throws BusinessException 业务相关异常
     */
    void addUserAddress(UUID id, AddressDTO addressDTO) throws BusinessException;

    /**
     * 删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @throws BusinessException 业务相关异常
     */
    void deleteUserAddress(UUID userId, UUID addressId) throws BusinessException;
}
