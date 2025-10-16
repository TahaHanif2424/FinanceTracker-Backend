package com.FutureConnect.FutureConnect.Friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FriendRequest {
  private String myId;
  private String friendId;
}
