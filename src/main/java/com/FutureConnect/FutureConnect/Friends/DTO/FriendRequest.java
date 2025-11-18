package com.FutureConnect.FutureConnect.Friends.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FriendRequest {
  @JsonProperty("senderId")
  private String myId;

  @JsonProperty("receiverId")
  private String friendId;
}
