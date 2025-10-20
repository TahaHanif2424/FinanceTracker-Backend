package com.FutureConnect.FutureConnect.Friends;

import com.FutureConnect.FutureConnect.Auth.UserRepository;
import com.FutureConnect.FutureConnect.Friends.DTO.FriendRequest;
import com.FutureConnect.FutureConnect.Model.Friends;
import com.FutureConnect.FutureConnect.Model.FriendshipStatus;
import com.FutureConnect.FutureConnect.Model.User;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

  @Autowired private FriendRepository friendRepository;
  @Autowired private UserRepository userRepository;

  public void sendRequest(FriendRequest friendRequest) {
    UUID myId = UUID.fromString(friendRequest.getMyId());
    UUID friendId = UUID.fromString(friendRequest.getFriendId());
    User myUser =
        userRepository.findById(myId).orElseThrow(() -> new RuntimeException("User not found"));
    User friend =
        userRepository
            .findById(friendId)
            .orElseThrow(() -> new RuntimeException("Friend not found"));

    // Check if friendship already exists (in either direction)
    boolean exists = friendRepository.findAll().stream()
        .anyMatch(f ->
            (f.getUser1().getId().equals(myId) && f.getUser2().getId().equals(friendId)) ||
            (f.getUser1().getId().equals(friendId) && f.getUser2().getId().equals(myId)));

    if (exists) {
      throw new IllegalStateException("Friendship request already exists");
    }

    Friends friends = new Friends();
    friends.setUser1(myUser);
    friends.setUser2(friend);
    friends.setStatus(FriendshipStatus.PENDING);
    friendRepository.save(friends);
  }

  public void acceptRequest(FriendRequest friendRequest) {
    UUID myId = UUID.fromString(friendRequest.getMyId());
    UUID friendId = UUID.fromString(friendRequest.getFriendId());
    User myUser =
        userRepository.findById(myId).orElseThrow(() -> new RuntimeException("User not found"));
    User friend =
        userRepository
            .findById(friendId)
            .orElseThrow(() -> new RuntimeException("Friend not found"));
    Friends friends =
        friendRepository
            .findByUser1AndUser2(myUser, friend)
            .orElseThrow(() -> new RuntimeException("Friend request not found"));
    friends.setStatus(FriendshipStatus.ACCEPTED);
    friendRepository.save(friends);
  }

  public List<User> getAllFriends(String userId){
    UUID myId = UUID.fromString(userId);
    userRepository.findById(myId).orElseThrow(() -> new RuntimeException("User not found"));
    List<Friends> friendships = friendRepository.findByUserIdAndStatus(myId, FriendshipStatus.ACCEPTED);

    return friendships.stream()
        .map(friendship -> {
          if (friendship.getUser1().getId().equals(myId)) {
            return friendship.getUser2();
          } else {
            return friendship.getUser1();
          }
        })
        .toList();
  }

  public List<User> getPendingFriendRequests(String userId){
    UUID myId = UUID.fromString(userId);
    userRepository.findById(myId).orElseThrow(() -> new RuntimeException("User not found"));
    List<Friends> friendships = friendRepository.findByUserIdAndStatus(myId, FriendshipStatus.PENDING);

    return friendships.stream()
        .map(friendship -> {
          if (friendship.getUser1().getId().equals(myId)) {
            return friendship.getUser2();
          } else {
            return friendship.getUser1();
          }
        })
        .toList();
  }

  public List<User> getNonFriendUsers(String userId){
    UUID myId = UUID.fromString(userId);
    userRepository.findById(myId).orElseThrow(() -> new RuntimeException("User not found"));

    // Get ALL friendships (any status) for this user
    List<Friends> allRelationships = friendRepository.findAll().stream()
        .filter(friendship ->
          friendship.getUser1().getId().equals(myId) ||
          friendship.getUser2().getId().equals(myId))
        .toList();

    // Collect all user IDs that have any entry in friends table with current user
    List<UUID> relatedUserIds = new java.util.ArrayList<>();
    allRelationships.forEach(friendship -> {
      if (friendship.getUser1().getId().equals(myId)) {
        relatedUserIds.add(friendship.getUser2().getId());
      } else {
        relatedUserIds.add(friendship.getUser1().getId());
      }
    });

    // Get all users and filter out current user and anyone with any relationship entry
    return userRepository.findAll().stream()
        .filter(user -> !user.getId().equals(myId) && !relatedUserIds.contains(user.getId()))
        .toList();
  }
}
