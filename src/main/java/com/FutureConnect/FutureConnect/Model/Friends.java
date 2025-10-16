package com.FutureConnect.FutureConnect.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Friends {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user1_id", nullable = false)
  private User user1;

  @ManyToOne
  @JoinColumn(name = "user2_id", nullable = false)
  private User user2;

  @Enumerated(EnumType.STRING)
  private FriendshipStatus status;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;
}
