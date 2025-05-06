package com.bank.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class UserFunds {
    private Long userId;
    private BigDecimal accountBalance;
    private BigDecimal frozenAmount;
    private BigDecimal totalIncome;
    private BigDecimal totalWithdrawal;
    private Date createTime;
    private Date updateTime;
    private Integer deleteStatus;
}