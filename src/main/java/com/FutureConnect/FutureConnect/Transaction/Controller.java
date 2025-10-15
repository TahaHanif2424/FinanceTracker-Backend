package com.FutureConnect.FutureConnect.Transaction;

import com.FutureConnect.FutureConnect.Model.Transaction;
import com.FutureConnect.FutureConnect.Transaction.DTO.RangeOfTransaction;
import com.FutureConnect.FutureConnect.Transaction.DTO.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class Controller {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<Transaction>> getTransactions(@PathVariable String userId) {
        List<Transaction> transactions =transactionService.getAllTransactions(userId);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/range")
    ResponseEntity<List<Transaction>> getRangeOfTransactions(@RequestBody RangeOfTransaction request) {
        List<Transaction> transactions =transactionService.getTransactionsByDate(request);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{transactionId}")
    ResponseEntity<Void> deleteTransaction(@PathVariable String transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }
}
