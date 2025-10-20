package com.FutureConnect.FutureConnect.Friends;

import com.FutureConnect.FutureConnect.Friends.DTO.FriendRequest;
import com.FutureConnect.FutureConnect.Model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendController {

  @Autowired private FriendService friendService;

  @PostMapping("/send-request")
  public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) {
    friendService.sendRequest(friendRequest);
    return ResponseEntity.ok("Friend request sent successfully");
  }

  @PostMapping("/accept-request")
  public ResponseEntity<String> acceptFriendRequest(@RequestBody FriendRequest friendRequest) {
    friendService.acceptRequest(friendRequest);
    return ResponseEntity.ok("Friend request accepted successfully");
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<User>> getAllFriends(@PathVariable String userId) {
    List<User> friends = friendService.getAllFriends(userId);
    return ResponseEntity.ok(friends);
  }

  @GetMapping("/{userId}/pending")
  public ResponseEntity<List<User>> getPendingFriendRequests(@PathVariable String userId) {
    List<User> pendingRequests = friendService.getPendingFriendRequests(userId);
    return ResponseEntity.ok(pendingRequests);
  }

  @GetMapping("/{userId}/non-friends")
  public ResponseEntity<List<User>> getNonFriendUsers(@PathVariable String userId) {
    List<User> nonFriends = friendService.getNonFriendUsers(userId);
    return ResponseEntity.ok(nonFriends);
  }
}
