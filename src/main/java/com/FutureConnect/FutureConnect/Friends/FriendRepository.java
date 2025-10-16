package com.FutureConnect.FutureConnect.Friends;

import com.FutureConnect.FutureConnect.Model.Friends;
import com.FutureConnect.FutureConnect.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friends,Long> {
    Optional<Friends> findByUser1AndUser2(User user1, User user2);
}
