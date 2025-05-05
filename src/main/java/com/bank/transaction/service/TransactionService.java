package com.bank.transaction.service;

import com.bank.transaction.dao.UserFundsMapper;
import com.bank.transaction.dao.UserTransactionRecordsMapper;
import com.bank.transaction.entity.UserFunds;
import com.bank.transaction.entity.UserTransactionRecords;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Resource
    private UserTransactionRecordsMapper userTransactionRecordsMapper;

    @Resource
    private UserFundsMapper userFundsMapper;

    // TODO optional：对与重复创建/修改交易，针对userid设置分布式锁
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserTransactionRecords createTransaction(UserTransactionRecords userTransactionRecords) {
        if (userTransactionRecords.getUserId() == null) {
            throw new RuntimeException("user transaction records user id is null");
        }

        Optional<UserFunds> userFunds = userFundsMapper.selectUserFundsById(userTransactionRecords.getUserId());
        if (userFunds.isEmpty()) {
            throw new RuntimeException("user transaction records user funds is null");
        }

        UserFunds userFund = userFunds.get();

        // 检测操作是否合法，比如是否有足够钱交易
        BigDecimal update = userFund.getAccountBalance().add(userTransactionRecords.getTransactionAmount());
        if (update.floatValue() < 0) {
            throw new RuntimeException("user transaction records account balance is negative");
        }
        userFund.setAccountBalance(update);
        userFundsMapper.updateUserFunds(userFund);

        userTransactionRecordsMapper.insertTransactionRecords(userTransactionRecords);

        return userTransactionRecords;
    }

    // 待优化：变更不可修改交易信息，交易信息只能补充描述？
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserTransactionRecords modifyTransaction(UserTransactionRecords userTransactionRecords) {
        if (userTransactionRecords.getUserId() == null) {
            throw new RuntimeException("user transaction records user id is null");
        }

        if (userTransactionRecords.getId() == null) {
            throw new RuntimeException("user transaction records id is null");
        }

        Optional<UserTransactionRecords> res = userTransactionRecordsMapper.selectTransactionRecordsById(userTransactionRecords.getId());
        if (res.isEmpty()) {
            throw new RuntimeException("user transaction records is null");
        }
        userTransactionRecordsMapper.updateTransactionRecords(userTransactionRecords);
        return userTransactionRecords;
    }

    // TODO 待优化：使用乐观锁优化防止并发问题,代码未完成，撤销交易需要撤回款项
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteTransaction(Long user_id, Long id) {
        if (id == null) {
            throw new RuntimeException("user transaction records user id is null");
        }

        Optional<UserTransactionRecords> res = userTransactionRecordsMapper.selectTransactionRecordsById(id);


        if (res.isEmpty()) {
            throw new RuntimeException("user transaction records id is null");
        }
        UserTransactionRecords update = new UserTransactionRecords();
        update.setId(id);
        update.setDeleteStatus(1);

        // 设置扣款
        int count = userTransactionRecordsMapper.updateTransactionRecords(update);

        return count == 1;
    }

    // TODO 未实现分页
    public List<UserTransactionRecords> listTransactions(Long userId) {
        return userTransactionRecordsMapper.selectTransactionRecordsById(userId).stream().toList();
    }
}
