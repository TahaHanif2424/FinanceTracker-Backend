package com.FutureConnect.FutureConnect.Transaction;

import com.FutureConnect.FutureConnect.Auth.UserRepository;
import com.FutureConnect.FutureConnect.Expense.ExpenseRepository;
import com.FutureConnect.FutureConnect.Model.Expense;
import com.FutureConnect.FutureConnect.Model.Transaction;
import com.FutureConnect.FutureConnect.Model.User;
import com.FutureConnect.FutureConnect.Transaction.DTO.RangeOfTransaction;
import com.FutureConnect.FutureConnect.Transaction.DTO.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    public Transaction createTransaction(TransactionRequest request) {
        // Validate amount
        if (request.getAmount() == 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        System.out.println(request.getDate());
        // Validate user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(request.getType());
        transaction.setDate(request.getDate());
        transaction.setCategory( request.getCategory() == null ? "General" : request.getCategory() );

        transaction.setDescription(
                request.getDescription() == null ? "" : request.getDescription()
        );

        transaction.setAmount(request.getAmount());

        Transaction finalTransaction = transactionRepository.save(transaction);
        Expense expense=expenseRepository.findByUserId(user.getId()).orElseThrow();
        expense.setCurrentBalance(expense.getCurrentBalance()+finalTransaction.getAmount());
        expenseRepository.save(expense);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(String userId) {
        UUID uuid = UUID.fromString(userId);
        Optional<User> user= userRepository.findById(uuid);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        List<Transaction> transaction= transactionRepository.findByUserIdOrderByDateDesc(uuid);
        System.out.println(transaction);
        return transaction;
    }

    public List<Transaction> getTransactionsByDate(RangeOfTransaction request) {
        UUID uuid = UUID.fromString(request.getUserId());
        Optional<User> user= userRepository.findById(uuid);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        LocalDateTime fromDate = LocalDateTime.parse(request.getFromDate() + "T00:00:00");
        LocalDateTime toDate = LocalDateTime.parse(request.getToDate() + "T23:59:59");

        return transactionRepository.findByUserIdAndDateBetweenOrderByDateDesc(uuid, fromDate, toDate);
    }
    public void deleteTransaction(String id) {
        if(id.isEmpty()){
            throw new IllegalArgumentException("ID must be provided");
        }

        Long transactionId = Long.parseLong(id);
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        transactionRepository.deleteById(transactionId);
    }
}
