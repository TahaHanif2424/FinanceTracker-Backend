package com.FutureConnect.FutureConnect.Group;

import com.FutureConnect.FutureConnect.Auth.UserRepository;
import com.FutureConnect.FutureConnect.Group.DTO.AddMember;
import com.FutureConnect.FutureConnect.Group.DTO.GroupDTO;
import com.FutureConnect.FutureConnect.Group.DTO.GroupRequest;
import com.FutureConnect.FutureConnect.Group.DTO.UserGroupMemberDTO;
import com.FutureConnect.FutureConnect.Model.Groups;
import com.FutureConnect.FutureConnect.Model.User;
import com.FutureConnect.FutureConnect.Model.UserGroupRelation;
import com.FutureConnect.FutureConnect.UserGroupRelation.UserGroupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

  @Autowired private GroupRepository groupRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private UserGroupRepository userGroupRepository;

  @Transactional
  public Groups createGroup(GroupRequest request) {
    // 1. Find the admin user
    User admin =
        userRepository
            .findById(UUID.fromString(request.getAdminId()))
            .orElseThrow(
                () ->
                    new RuntimeException("Admin user not found with id: " + request.getAdminId()));
    System.out.println("admin found" + admin);
    // 2. Create the group
    Groups group = new Groups();
    group.setName(request.getName());
    group.setAdmin(admin);
    Groups savedGroup = groupRepository.save(group);
    System.out.println("Group saved: " + savedGroup);
    // 3. Create UserGroupRelation for admin
    UserGroupRelation adminRelation = new UserGroupRelation();
    adminRelation.setUser(admin);
    adminRelation.setGroup(savedGroup);
    adminRelation.setDebt(0);
    adminRelation.setReceivable(0);
    adminRelation.setPendingBalance(0);
    userGroupRepository.save(adminRelation);

    // 4. Add members if provided
    if (request.getMembers() != null && !request.getMembers().isEmpty()) {
      List<UserGroupRelation> memberRelations = new ArrayList<>();

      for (String memberId : request.getMembers()) {
        // Find user by ID
        User member =
            userRepository
                .findById(UUID.fromString(memberId))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + memberId));

        // Create relation for this member
        UserGroupRelation memberRelation = new UserGroupRelation();
        memberRelation.setUser(member);
        memberRelation.setGroup(savedGroup);
        memberRelation.setDebt(0);
        memberRelation.setReceivable(0);
        memberRelation.setPendingBalance(0);
        memberRelations.add(memberRelation);
      }

      userGroupRepository.saveAll(memberRelations);
    }

    return savedGroup;
  }

  @Transactional
  public void addMemberToGroup(AddMember members) {
    // Find the group
    Groups group =
        groupRepository
            .findById(Integer.valueOf(members.getGroupId()))
            .orElseThrow(
                () -> new RuntimeException("Group not found with id: " + members.getGroupId()));

    // Validate that userIds list is not empty
    if (members.getUserId() == null || members.getUserId().isEmpty()) {
      throw new IllegalArgumentException("User IDs list cannot be empty");
    }

    List<UserGroupRelation> memberRelations = new ArrayList<>();

    // Iterate through all user IDs and add them to the group
    for (String userId : members.getUserId()) {
      // Find the user
      User user =
          userRepository
              .findById(UUID.fromString(userId))
              .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

      // Check if user is already a member
      boolean alreadyMember =
          userGroupRepository.findAll().stream()
              .anyMatch(
                  relation ->
                      relation.getGroup().getId() == group.getId()
                          && relation.getUser().getId().equals(user.getId()));

      if (alreadyMember) {
        throw new IllegalStateException(
            "User " + user.getName() + " is already a member of this group");
      }

      // Create UserGroupRelation for the new member
      UserGroupRelation memberRelation = new UserGroupRelation();
      memberRelation.setUser(user);
      memberRelation.setGroup(group);
      memberRelation.setDebt(0);
      memberRelation.setReceivable(0);
      memberRelation.setPendingBalance(0);
      memberRelations.add(memberRelation);
    }

    // Save all member relations at once
    userGroupRepository.saveAll(memberRelations);
  }

  public List<GroupDTO> displayUserGroups(String userId) {
    UUID userUuid = UUID.fromString(userId);

    // Verify user exists
    userRepository
        .findById(userUuid)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    // Get all groups the user is a member of (includes groups where user is admin)
    List<UserGroupRelation> userGroupRelations = userGroupRepository.findByUser_Id(userUuid);

    // Convert groups to DTOs
    return userGroupRelations.stream()
        .map(UserGroupRelation::getGroup)
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  // Helper method to convert Groups entity to GroupDTO
  private GroupDTO convertToDTO(Groups group) {
    GroupDTO dto = new GroupDTO();
    dto.setId(group.getId());
    dto.setName(group.getName());
    dto.setDescription(group.getDescription());
    dto.setCreatedAt(group.getCreatedAt());
    dto.setUpdatedAt(group.getUpdatedAt());

    // Set admin info (without circular references)
    if (group.getAdmin() != null) {
      dto.setAdminId(group.getAdmin().getId());
      dto.setAdminName(group.getAdmin().getName());
      dto.setAdminEmail(group.getAdmin().getEmail());
    }

    // Convert members with their balances (without circular references)
    if (group.getUserRelations() != null) {
      List<UserGroupMemberDTO> members =
          group.getUserRelations().stream()
              .map(this::convertToMemberDTO)
              .collect(Collectors.toList());
      dto.setMembers(members);
    }

    return dto;
  }

  // Helper method to convert UserGroupRelation to UserGroupMemberDTO
  private UserGroupMemberDTO convertToMemberDTO(UserGroupRelation relation) {
    UserGroupMemberDTO memberDTO = new UserGroupMemberDTO();
    memberDTO.setUserId(relation.getUser().getId());
    memberDTO.setUserName(relation.getUser().getName());
    memberDTO.setUserEmail(relation.getUser().getEmail());
    memberDTO.setDebt(relation.getDebt());
    memberDTO.setReceivable(relation.getReceivable());
    memberDTO.setPendingBalance(relation.getPendingBalance());
    return memberDTO;
  }

  public List<UserGroupMemberDTO> getGroupMemberInformation(String groupId) {
    Groups group =
        groupRepository
            .findById(Integer.valueOf(groupId))
            .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
    List<UserGroupRelation> info = userGroupRepository.findByGroup_Id(Integer.valueOf(groupId));

    // Convert to DTOs with user names included
    return info.stream().map(this::convertToMemberDTO).collect(Collectors.toList());
  }
}
