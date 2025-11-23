package com.FutureConnect.FutureConnect.Group.DTO;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupMemberDTO {
  private UUID userId;
  private String userName;
  private String userEmail;
  private int debt;
  private int receivable;
  private int pendingBalance;
}
