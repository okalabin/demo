package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(String name, Long parentId, String position) {
        User parent = parentId != null ? userRepository.findById(parentId).orElse(null) : null;
        User newUser = new User();
        newUser.setName(name);
        newUser.setParent(parent);
        newUser.setPosition(position);
        return userRepository.save(newUser);
    }

    public List<User> findUsersByNameInSubtree(String name, Long parentId) {
        User parent = parentId != null ? userRepository.findById(parentId).orElse(null) : null;
        List<User> usersInSubtree = new ArrayList<>();
        findUsersInSubtreeRecursive(parent, name, usersInSubtree);
        return usersInSubtree;
    }

    private void findUsersInSubtreeRecursive(User node, String name, List<User> result) {
        if (node == null) {
            return;
        }

        if (node.getName().contains(name)) {
            result.add(node);
        }

        List<User> children = userRepository.findByParent(node);
        for (User child : children) {
            findUsersInSubtreeRecursive(child, name, result);
        }
    }

    // New method to retrieve a user by ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // New method to update a user
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setPosition(updatedUser.getPosition());
            return userRepository.save(existingUser);
        }
        return null; // User not found
    }

    // New method to delete a user by ID
    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true; // User deleted
        }
        return false; // User not found
    }

    public void dumpOrganizationalStructureToJsonFile(Long rootId, String filename) throws IOException {
        User root = rootId != null ? userRepository.findById(rootId).orElse(null) : null;
        if (root == null) {
            throw new IllegalArgumentException("Root user not found.");
        }

        List<User> organizationalStructure = new ArrayList<>();
        buildOrganizationalStructureRecursive(root, organizationalStructure);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), organizationalStructure);
    }

    private void buildOrganizationalStructureRecursive(User node, List<User> structure) {
        if (node == null) {
            return;
        }

        structure.add(node);
        List<User> children = userRepository.findByParent(node);
        for (User child : children) {
            buildOrganizationalStructureRecursive(child, structure);
        }
    }
}
