package com.FutureConnect.FutureConnect.Group;

import com.FutureConnect.FutureConnect.Group.DTO.AddMember;
import com.FutureConnect.FutureConnect.Group.DTO.GroupRequest;
import com.FutureConnect.FutureConnect.Group.DTO.MemberRequest;
import com.FutureConnect.FutureConnect.Model.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
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

  @PostMapping("/addMember")
  public ResponseEntity<?> addMember(@RequestBody AddMember request) {
    try {
      groupService.addMemberToGroup(request.getGroupId(), request.getUserId());
      return ResponseEntity.ok("Member added to group successfully");
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while adding member: " + e.getMessage());
    }
  }
}
