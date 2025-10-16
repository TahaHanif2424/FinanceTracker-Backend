package com.FutureConnect.FutureConnect.Group.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupRequest {
  private String name;
  private String adminId;
  private List<MemberRequest> members;
}
