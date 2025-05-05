package com.bank.transaction.dao;

import com.bank.transaction.entity.UserTransactionRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserTransactionRecordsMapper {
    /**
     * 根据交易 ID 查询用户交易记录信息
     * @param transactionId 交易 ID
     * @return 用户交易记录信息
     */
    Optional<UserTransactionRecords> selectTransactionRecordsById(Long transactionId);


    /**
     * 插入用户交易记录信息
     * @param transactionRecords 用户交易记录信息
     * @return 插入的记录数
     */
    int insertTransactionRecords(UserTransactionRecords transactionRecords);

    /**
     * 更新用户交易记录信息
     * @param transactionRecords 用户交易记录信息
     * @return 更新的记录数
     */
    int updateTransactionRecords(UserTransactionRecords transactionRecords);

    /**
     * 根据交易 ID 删除用户交易记录信息
     * @param transactionId 交易 ID
     * @return 删除的记录数
     */
    int deleteTransactionRecordsById(Long transactionId);
}