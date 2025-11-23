package com.FutureConnect.FutureConnect.UserGroupRelation;

import com.FutureConnect.FutureConnect.Model.UserGroupRelation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroupRelation, Long> {
  List<UserGroupRelation> findByUser_Id(UUID userId);

  List<UserGroupRelation> findByGroup_Id(Integer groupId);
}
