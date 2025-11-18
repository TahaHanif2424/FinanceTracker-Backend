package com.FutureConnect.FutureConnect.Friends;

import com.FutureConnect.FutureConnect.Model.Friends;
import com.FutureConnect.FutureConnect.Model.FriendshipStatus;
import com.FutureConnect.FutureConnect.Model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friends, Long> {
  Optional<Friends> findByUser1AndUser2(User user1, User user2);

  @Query("SELECT f FROM Friends f WHERE (f.user1.id = :userId OR f.user2.id = :userId) AND f.status = :status")
  List<Friends> findByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") FriendshipStatus status);

  @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Friends f WHERE " +
         "(f.user1.id = :userId1 AND f.user2.id = :userId2) OR " +
         "(f.user1.id = :userId2 AND f.user2.id = :userId1)")
  boolean existsByUsers(@Param("userId1") UUID userId1, @Param("userId2") UUID userId2);
}
