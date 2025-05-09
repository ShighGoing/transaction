package com.bank.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserTransactionRecords {
    private Long Id;
    private Long userId;
    private Integer transactionType;
    private BigDecimal transactionAmount;
    private Date transactionTime;
    private Integer deleteStatus;
    private Integer transactionStatus;
    private String description;
}