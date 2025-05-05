-- 创建用户资金表
CREATE TABLE user_funds (
                            user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID，唯一标识用户',
                            account_balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '账户余额，精确到小数点后两位',
                            frozen_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额，如在某些交易中被冻结的资金，精确到小数点后两位',
                            total_income DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '总收入，精确到小数点后两位',
                            total_withdrawal DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '总提现金额，精确到小数点后两位',
                            delete_status TINYINT DEFAULT 0 COMMENT '已删除 1 未删除 0',
                            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，记录该用户资金记录创建的时刻',
                            update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，每次资金相关变动时更新'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户资金表，记录用户资金相关信息';

-- 创建用户交易记录表
CREATE TABLE user_transaction_records (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '交易ID，唯一标识每笔交易',
                                          user_id BIGINT NOT NULL COMMENT '关联的用户ID，表明该交易属于哪个用户',
                                          transaction_type VARCHAR(20) NOT NULL COMMENT '交易类型，如"存款"、"取款"、"转账"、"消费"等',
                                          transaction_amount DECIMAL(10, 2) NOT NULL COMMENT '交易金额，精确到小数点后两位',
                                          transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间，记录交易发生的时刻',
                                          delete_status TINYINT DEFAULT 0 COMMENT '已删除 1 未删除 0',
                                          counterparty_info VARCHAR(255) COMMENT '交易对手信息，如转账时对方的账户名、账号等',
                                          transaction_status VARCHAR(20) NOT NULL DEFAULT '成功' COMMENT '交易状态，如"成功"、"失败"、"处理中"等',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户交易记录表，记录用户的每笔交易信息';