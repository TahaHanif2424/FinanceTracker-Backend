package com.FutureConnect.FutureConnect.Group;

import com.FutureConnect.FutureConnect.Group.DTO.AddMember;
import com.FutureConnect.FutureConnect.Group.DTO.GroupDTO;
import com.FutureConnect.FutureConnect.Group.DTO.GroupRequest;
import com.FutureConnect.FutureConnect.Group.DTO.UserGroupMemberDTO;
import com.FutureConnect.FutureConnect.Model.Groups;
import jakarta.validation.Valid;
import java.util.List;
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
  public ResponseEntity<?> addMember(@Valid @RequestBody AddMember request) {
    try {
      groupService.addMemberToGroup(request);
      return ResponseEntity.ok("Members added to group successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while adding members: " + e.getMessage());
    }
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<?> getUserGroups(@PathVariable String userId) {
    try {
      List<GroupDTO> groups = groupService.displayUserGroups(userId);
      return ResponseEntity.ok(groups);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while fetching user groups: " + e.getMessage());
    }
  }

  @GetMapping("/info/{groupId}")
  public ResponseEntity<?> getGroupInfo(@PathVariable String groupId) {
    List<UserGroupMemberDTO> info = groupService.getGroupMemberInformation(groupId);
    return ResponseEntity.ok(info);
  }
}
