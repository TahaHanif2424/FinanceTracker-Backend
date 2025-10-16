package com.FutureConnect.FutureConnect.Group.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupRequest {
    private String name;
    private String adminId;
    private List<MemberRequest> members;
}
