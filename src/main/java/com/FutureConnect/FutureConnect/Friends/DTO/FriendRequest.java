package com.FutureConnect.FutureConnect.Friends.DTO;

import com.FutureConnect.FutureConnect.Model.FriendshipStatus;
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
