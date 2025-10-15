package com.FutureConnect.FutureConnect.Transaction;

import com.FutureConnect.FutureConnect.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByDateDesc(UUID userId);
    List<Transaction> findByUserIdAndDateBetweenOrderByDateDesc(UUID userId, LocalDateTime fromDate, LocalDateTime toDate);
}
