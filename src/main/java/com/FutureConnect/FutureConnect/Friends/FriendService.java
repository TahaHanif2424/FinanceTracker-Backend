package com.FutureConnect.FutureConnect.Friends;

import com.FutureConnect.FutureConnect.Auth.UserRepository;
import com.FutureConnect.FutureConnect.Auth.UserService;
import com.FutureConnect.FutureConnect.Friends.DTO.FriendRequest;
import com.FutureConnect.FutureConnect.Model.Friends;
import com.FutureConnect.FutureConnect.Model.FriendshipStatus;
import com.FutureConnect.FutureConnect.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;

    private void sendRequest(FriendRequest friendRequest) {
        UUID myId= UUID.fromString(friendRequest.getMyId());
        UUID friendId= UUID.fromString(friendRequest.getFriendId());
        User myUser = userRepository.findById(myId).orElseThrow(()->new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(()->new RuntimeException("Friend not found"));
        Friends friends = new Friends();
        friends.setUser1(myUser);
        friends.setUser1(friend);
        friends.setStatus(FriendshipStatus.valueOf("PENDING"));
        friendRepository.save(friends);
    }

    private void accpetRequest(FriendRequest friendRequest) {
        UUID myId= UUID.fromString(friendRequest.getMyId());
        UUID friendId= UUID.fromString(friendRequest.getFriendId());
        User myUser = userRepository.findById(myId).orElseThrow(()->new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(()->new RuntimeException("Friend not found"));
        Friends friends = friendRepository.findByUser1AndUser2(myUser, friend).orElseThrow(()->new RuntimeException("Friend not found"));
        friends.setStatus(FriendshipStatus.valueOf("ACCEPTED"));
        friendRepository.save(friends);
    }

}
