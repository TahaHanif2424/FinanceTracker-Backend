package com.FutureConnect.FutureConnect.Group.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddMember {
    private String groupId;
    private String userId;
}
