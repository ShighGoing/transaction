package com.bank.transaction.controller;

import com.bank.transaction.entity.UserTransactionRecords;
import com.bank.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "transactions controller")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(summary = "创建交易中心")
    public ResponseEntity<UserTransactionRecords> createTransaction(@RequestBody UserTransactionRecords userTransactionRecords) {
        UserTransactionRecords createdUserTransactionRecords = transactionService.createTransaction(userTransactionRecords);
        return new ResponseEntity<>(createdUserTransactionRecords, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserTransactionRecords> modifyTransaction(@RequestBody UserTransactionRecords userTransactionRecords) {
        UserTransactionRecords modifiedUserTransactionRecords = transactionService.modifyTransaction(userTransactionRecords);
        if (modifiedUserTransactionRecords != null) {
            return new ResponseEntity<>(modifiedUserTransactionRecords, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{user_id}/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id, @PathVariable Long user_id) {
        boolean deleted = transactionService.deleteTransaction(user_id, id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<UserTransactionRecords>> listTransactions(@PathVariable Long user_id) {
        List<UserTransactionRecords> userTransactionRecords = transactionService.listTransactions(user_id);
        return new ResponseEntity<>(userTransactionRecords, HttpStatus.OK);
    }
}