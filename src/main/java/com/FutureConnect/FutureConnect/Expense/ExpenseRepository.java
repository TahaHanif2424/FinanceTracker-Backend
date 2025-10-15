package com.FutureConnect.FutureConnect.Expense;

import com.FutureConnect.FutureConnect.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByUserId(UUID userId);
}
