package com.FutureConnect.FutureConnect.Group;

import com.FutureConnect.FutureConnect.Model.Groups;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Integer> {
  List<Groups> findByAdmin_Id(UUID adminId);
}
