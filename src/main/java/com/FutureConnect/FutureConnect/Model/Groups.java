package com.FutureConnect.FutureConnect.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Groups {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @ToString.Exclude
  @JsonIgnoreProperties({"groupRelations", "password"})
  private User admin;

  private String name;
  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;
  private String description;

  @OneToMany(mappedBy = "group")
  @ToString.Exclude
  @JsonIgnoreProperties({"user", "group"})
  private List<UserGroupRelation> userRelations;
}
