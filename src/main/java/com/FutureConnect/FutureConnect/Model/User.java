package com.FutureConnect.FutureConnect.Model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
  private UUID id;

  private String name;
  private String email;
  private String password;

  @Column(columnDefinition = "integer default 0")
  private Integer monthlyIncome = 0;

  private boolean isVerified;

  @OneToMany(mappedBy = "user")
  private List<UserGroupRelation> groupRelations;
}
