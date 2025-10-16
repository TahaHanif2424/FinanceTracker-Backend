package com.FutureConnect.FutureConnect.Group;

import com.FutureConnect.FutureConnect.Auth.UserRepository;
import com.FutureConnect.FutureConnect.Group.DTO.GroupRequest;
import com.FutureConnect.FutureConnect.Group.DTO.MemberRequest;
import com.FutureConnect.FutureConnect.Model.Groups;
import com.FutureConnect.FutureConnect.Model.User;
import com.FutureConnect.FutureConnect.Model.UserGroupRelation;
import com.FutureConnect.FutureConnect.UserGroupRelation.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Transactional
    public Groups createGroup(GroupRequest request) {
        // 1. Find the admin user
        User admin = userRepository.findById(UUID.fromString(request.getAdminId()))
                .orElseThrow(() -> new RuntimeException("Admin user not found with id: " + request.getAdminId()));

        // 2. Create the group
        Groups group = new Groups();
        group.setName(request.getName());
        group.setAdmin(admin);
        Groups savedGroup = groupRepository.save(group);

        // 3. Create UserGroupRelation for admin
        UserGroupRelation adminRelation = new UserGroupRelation();
        adminRelation.setUser(admin);
        adminRelation.setGroup(savedGroup);
        adminRelation.setDebt(0);
        adminRelation.setReceivable(0);
        userGroupRepository.save(adminRelation);

        // 4. Add members if provided
        if (request.getMembers() != null && !request.getMembers().isEmpty()) {
            List<UserGroupRelation> memberRelations = new ArrayList<>();

            for (MemberRequest memberRequest : request.getMembers()) {
                // Find user by email
                User member = userRepository.findByEmail(memberRequest.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + memberRequest.getEmail()));

                // Create relation for this member
                UserGroupRelation memberRelation = new UserGroupRelation();
                memberRelation.setUser(member);
                memberRelation.setGroup(savedGroup);
                memberRelation.setDebt(0);
                memberRelation.setReceivable(0);
                memberRelations.add(memberRelation);
            }

            userGroupRepository.saveAll(memberRelations);
        }

        return savedGroup;
    }
}
