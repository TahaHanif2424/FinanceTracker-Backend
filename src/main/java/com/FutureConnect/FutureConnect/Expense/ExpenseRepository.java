package com.FutureConnect.FutureConnect.Expense;

import com.FutureConnect.FutureConnect.Model.Expense;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  Optional<Expense> findByUserId(UUID userId);
}
