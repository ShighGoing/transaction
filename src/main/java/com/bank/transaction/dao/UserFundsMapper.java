package com.bank.transaction.dao;

import com.bank.transaction.entity.UserFunds;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper
public interface UserFundsMapper {
    /**
     * 根据用户 ID 查询用户资金信息
     * @param userId 用户 ID
     * @return 用户资金信息
     */
    Optional<UserFunds> selectUserFundsById(Long userId);

    /**
     * 插入用户资金信息
     * @param userFunds 用户资金信息
     * @return 插入的记录数
     */
    int insertUserFunds(UserFunds userFunds);

    /**
     * 更新用户资金信息
     * @param userFunds 用户资金信息
     * @return 更新的记录数
     */
    int updateUserFunds(UserFunds userFunds);
}