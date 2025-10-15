package com.FutureConnect.FutureConnect.Transaction.DTO;

import com.FutureConnect.FutureConnect.Model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private UUID userId;
    private TransactionType type;
    private String category;
    private int amount;
    private LocalDateTime date;
    private String description;
}
