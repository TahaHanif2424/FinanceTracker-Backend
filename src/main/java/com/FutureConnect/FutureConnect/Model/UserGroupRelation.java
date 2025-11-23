package com.FutureConnect.FutureConnect.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_group_relation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRelation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Each relation belongs to ONE user
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @ToString.Exclude
  private User user;

  // Each relation belongs to ONE group
  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false)
  @ToString.Exclude
  private Groups group;

  // Extra field in the relationship
  private int debt = 0;

  private int receivable = 0;

  @Column(name = "pending_balance", nullable = false)
  private int pendingBalance = 0;
}
