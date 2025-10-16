package com.FutureConnect.FutureConnect.Group;

import com.FutureConnect.FutureConnect.Group.DTO.GroupRequest;
import com.FutureConnect.FutureConnect.Model.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

  @Autowired private GroupService groupService;

  @PostMapping("/create")
  public ResponseEntity<?> createGroup(@RequestBody GroupRequest request) {
    try {
      Groups createdGroup = groupService.createGroup(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while creating the group: " + e.getMessage());
    }
  }
}
