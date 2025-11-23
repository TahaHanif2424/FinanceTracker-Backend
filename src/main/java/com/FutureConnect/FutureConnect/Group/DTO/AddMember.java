package com.FutureConnect.FutureConnect.Group.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddMember {
  @NotBlank(message = "Group ID is required")
  private String groupId;

  @NotEmpty(message = "User IDs list cannot be empty")
  private List<String> userId;
}
