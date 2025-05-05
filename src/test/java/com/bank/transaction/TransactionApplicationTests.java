package com.bank.transaction;

import com.bank.transaction.entity.UserTransactionRecords;
import com.bank.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TransactionApplicationTests {
	private final TransactionService transactionService = new TransactionService();

	@Test
	public void testCreateTransaction() {
		UserTransactionRecords userTransactionRecords = new UserTransactionRecords();
		UserTransactionRecords createdUserTransactionRecords = transactionService.createTransaction(userTransactionRecords);
		assertNotNull(createdUserTransactionRecords.getId());
	}

	@Test
	public void testListTransactions() {
		UserTransactionRecords userTransactionRecords = new UserTransactionRecords();
		transactionService.createTransaction(userTransactionRecords);
		assertFalse(transactionService.listTransactions(1L).isEmpty());
	}
}
