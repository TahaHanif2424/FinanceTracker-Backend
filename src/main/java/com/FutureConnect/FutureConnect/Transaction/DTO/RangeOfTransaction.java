package com.FutureConnect.FutureConnect.Transaction.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RangeOfTransaction {
    private String userId;
    private String fromDate;
    private String toDate;
}
