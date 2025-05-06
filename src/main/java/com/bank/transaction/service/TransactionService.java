package com.bank.transaction.service;

import com.bank.transaction.entity.UserFunds;
import com.bank.transaction.entity.UserTransactionRecords;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final List<UserTransactionRecords> userTransactionRecordsList = new ArrayList<>();
    private final List<UserFunds> userFundsList = new ArrayList<>();

    public UserTransactionRecords createTransaction(UserTransactionRecords userTransactionRecords) {
        if (userTransactionRecords.getUserId() == null) {
            throw new RuntimeException("user transaction records user id is null");
        }

        List<UserFunds> findUserFundsList = this.userFundsList.stream().filter(userFunds -> userFunds.getUserId().equals(userTransactionRecords.getUserId())).toList();

        if (findUserFundsList.size() != 1) {
            throw new RuntimeException("user transaction records user id is incorrect");
        }

        UserFunds userFund = findUserFundsList.getFirst();

        // 检测操作是否合法，比如是否有足够钱交易
        BigDecimal update = userFund.getAccountBalance().add(userTransactionRecords.getTransactionAmount());
        if (update.floatValue() < 0) {
            throw new RuntimeException("user transaction records account balance is negative");
        }
        userFund.setAccountBalance(update);

        UserTransactionRecords add = new UserTransactionRecords();
        add.setTransactionTime(new Date());
        add.setTransactionType(1);
        add.setTransactionAmount(userTransactionRecords.getTransactionAmount());
        add.setDeleteStatus(0);
        add.setDescription(userTransactionRecords.getDescription());
        add.setTransactionStatus(0);
        add.setUserId(userTransactionRecords.getUserId());

        userTransactionRecordsList.add(userTransactionRecords);

        return userTransactionRecords;
    }

    public UserTransactionRecords modifyTransaction(UserTransactionRecords userTransactionRecords) {
        if (userTransactionRecords.getUserId() == null) {
            throw new RuntimeException("user transaction records user id is null");
        }

        if (userTransactionRecords.getId() == null) {
            throw new RuntimeException("user transaction records id is null");
        }

        List<UserTransactionRecords> foundList = this.userTransactionRecordsList.stream().filter(transactionRecords ->
                transactionRecords.getUserId().equals(userTransactionRecords.getUserId()) && transactionRecords.getId().equals(userTransactionRecords.getId())).toList();

        if (foundList.isEmpty()) {
            throw new RuntimeException("user transaction records user id is incorrect");
        }

        UserTransactionRecords update = foundList.getFirst();

        // 只允许变更状态
        update.setTransactionStatus(userTransactionRecords.getTransactionStatus());
        return update;
    }


    public boolean deleteTransaction(Long user_id, Long id) {
        if (user_id == null) {
            throw new RuntimeException("user transaction records user id is null");
        }

        if (id == null) {
            throw new RuntimeException("user transaction records id is null");
        }

        List<UserTransactionRecords> foundList = this.userTransactionRecordsList.stream().filter(transactionRecords ->
                transactionRecords.getUserId().equals(user_id) && transactionRecords.getId().equals(id)).toList();

        if (foundList.isEmpty()) {
            throw new RuntimeException("user transaction records user id is incorrect");
        }

        UserTransactionRecords update = foundList.getFirst();

        if (update.getDeleteStatus() == 1) {
            throw new RuntimeException("user transaction records user id is incorrect");
        }

        // 只允许变更状态
        update.setDeleteStatus(1);
        return true;
    }

    // TODO 未实现分页
    public List<UserTransactionRecords> listTransactions(Long userId) {
        return this.userTransactionRecordsList.stream().filter(userTransactionRecords -> userTransactionRecords.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
