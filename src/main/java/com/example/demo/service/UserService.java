package com.example.demo.service;

import com.example.demo.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(String name, Long parentId, String position);
    List<User> findUsersByNameInSubtree(String name, Long parentId);
    Optional<User> getUserById(Long userId);
    User updateUser(Long userId, User updatedUser);
    boolean deleteUser(Long userId);
    void dumpOrganizationalStructureToJsonFile(Long rootId, String filename) throws IOException;

}
