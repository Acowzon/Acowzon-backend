package org.acowzon.backend.service.user.impl;

import org.acowzon.backend.dao.address.AddressDAO;
import org.acowzon.backend.dao.order.OrderDAO;
import org.acowzon.backend.dao.shop.ShopDAO;
import org.acowzon.backend.dao.user.UserDAO;
import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.dto.user.UserBasicInfoDTO;
import org.acowzon.backend.dto.user.UserFullInfoDTO;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.shop.ShopMgnService;
import org.acowzon.backend.service.user.UserMgnService;
import org.acowzon.backend.utils.PublicBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class UserMgnServiceImpl implements UserMgnService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShopDAO shopDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private ShopMgnService shopMgnService;

    @Autowired
    private OrderDAO orderDAO;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 获取用户的基本信息
     *
     * @param id 用户id
     * @return UserBasicInfoDTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public UserBasicInfoDTO getUserBasicInfo(UUID id) throws BusinessException {
        Optional<UserEntity> userEntityOptional = userDAO.findById(id);
        if (userEntityOptional.isPresent()) {
            return UserBasicInfoDTO.parseDTO(userEntityOptional.get());
        } else {
            throw new BusinessException("no_such_user");
        }
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
        Optional<UserEntity> userEntityOptional = userDAO.findById(id);
        if (userEntityOptional.isPresent()) {
            return UserFullInfoDTO.parseDTO(userEntityOptional.get());
        } else {
            throw new BusinessException("no_such_user");
        }
    }

    /**
     * 列出所有用户
     *
     * @return UserBasicInfoDTO[]
     */
    @Override
    public UserBasicInfoDTO[] listAllUser() {
        return userDAO.findAll().stream().map(UserBasicInfoDTO::parseDTO).toArray(UserBasicInfoDTO[]::new);
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
     * 验证用户信息
     *
     * @param userName 用户名
     * @param password 登录密码
     * @return 登录状态
     * @throws BusinessException 业务相关异常
     */
    @Override
    public Optional<UUID> verifyUser(String userName, String password) throws BusinessException {
        Optional<UserEntity> userEntityOptional = userDAO.findByUserName(userName);
        if (userEntityOptional.isPresent()) {
            if (passwordEncoder.matches(password, userEntityOptional.get().getPassword())) {
                return Optional.of(userEntityOptional.get().getId());
            } else {
                return Optional.empty();
            }
        } else {
            throw new BusinessException("no_such_user");
        }
    }

    /**
     * 注册用户
     *
     * @param userFullInfoDTO 用户信息DTO
     * @return 生成的uuid
     * @throws BusinessException 业务相关异常
     */
    @Override
    public UUID addUser(UserFullInfoDTO userFullInfoDTO, String password) throws BusinessException {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userFullInfoDTO, userEntity, "isSeller", "addressSet");
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        return userDAO.save(userEntity).getId();
    }

    /**
     * 更新用户信息
     *
     * @param userFullInfoDTO 用户信息DTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updateUser(UserFullInfoDTO userFullInfoDTO) throws BusinessException {
        Optional<UserEntity> userEntityOptional = userDAO.findById(userFullInfoDTO.getId());
        if (userEntityOptional.isPresent()) {
            List<String> ignoredPropertyList = new ArrayList(Arrays.asList(PublicBeanUtils.getNullPropertyNames(userFullInfoDTO)));
            ignoredPropertyList.add("isSeller");
            ignoredPropertyList.add("addressSet");
            BeanUtils.copyProperties(userFullInfoDTO, userEntityOptional.get(), ignoredPropertyList.toArray(new String[0]));
            userEntityOptional.get().setUpdateTime(new Date());
            userDAO.save(userEntityOptional.get());
        } else {
            throw new BusinessException("no_such_user");
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void deleteUser(UUID id) throws BusinessException {
        Optional<UserEntity> userEntityOptional = userDAO.findById(id);
        if (userEntityOptional.isPresent()) {
            shopDAO.deleteByOwner(userEntityOptional.get());
            userDAO.deleteById(id);
        } else {
            throw new BusinessException("no_such_user");
        }
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
        Optional<UserEntity> userEntityOptional = userDAO.findById(id);
        if (userEntityOptional.isPresent()) {
            userEntityOptional.get().setSeller(isSeller);
            userEntityOptional.get().setUpdateTime(new Date());
            userDAO.save(userEntityOptional.get());
        } else {
            throw new BusinessException("no_such_user");
        }
    }

    /**
     * 修改用户密码
     *
     * @param id       用户id
     * @param password 新密码
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updateUserPassword(UUID id, String password) throws BusinessException {
        Optional<UserEntity> userEntityOptional = userDAO.findById(id);
        if (userEntityOptional.isPresent()) {
            userEntityOptional.get().setPassword(passwordEncoder.encode(password));
            userEntityOptional.get().setUpdateTime(new Date());
            userDAO.save(userEntityOptional.get());
        } else {
            throw new BusinessException("no_such_user");
        }
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
        Assert.notNull(id, "id can not be null");
        Assert.notNull(addressDTO, "addressDTO can not be null");
        Optional<UserEntity> userEntityOptional = userDAO.findById(id);
        if (!userEntityOptional.isPresent()) {
            throw new BusinessException("no_such_user");
        }

        Set<AddressEntity> addressEntitySet = userEntityOptional.get().getAddress();

        AddressEntity newAddr = new AddressEntity();

        BeanUtils.copyProperties(addressDTO, newAddr, "id");//忽略id字段，让后端自动生成
        addressEntitySet.add(newAddr);

        userEntityOptional.get().setUpdateTime(new Date());

        userDAO.save(userEntityOptional.get());
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
        Assert.notNull(userId, "id can not be null");
        Assert.notNull(addressId, "addressId can not be null");
        Optional<UserEntity> userEntityOptional = userDAO.findById(userId);
        if (!userEntityOptional.isPresent()) {
            throw new BusinessException("no_such_user");
        }

        if (userEntityOptional.get().getAddress().isEmpty()) {
            throw new BusinessException("user_address_list_is_empty");
        }

        Optional<AddressEntity> addressEntityOptional = addressDAO.findById(addressId);
        if (!addressEntityOptional.isPresent()) {
            throw new BusinessException("no_such_address");
        }
        //todo 这里需要改成双向级联操作，不用每次都先从shop表中提取再删除再保存
        Set<AddressEntity> addressEntitySet = userEntityOptional.get().getAddress();
        addressEntitySet.remove(addressEntityOptional.get());

        userEntityOptional.get().setUpdateTime(new Date());

        userDAO.save(userEntityOptional.get());
    }
}
