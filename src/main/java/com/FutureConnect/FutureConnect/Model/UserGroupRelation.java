package com.FutureConnect.FutureConnect.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

    // Each relation belongs to ONE group
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;

    // Extra field in the relationship
    private int debt;

    private int receivable;
}
