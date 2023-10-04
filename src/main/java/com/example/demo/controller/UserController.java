package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @Operation(summary = "Add a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    @ApiResponse(responseCode = "400", description = "Invalid input or failed to create user")
    public ResponseEntity<User> addUser(
            @RequestParam String name,
            @RequestParam String position,
            @RequestParam(required = false) Long parentId) {
        User newUser = userService.addUser(name, parentId, position);
        if (newUser != null) {
            return ResponseEntity.status(201).body(newUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findByName")
    @Operation(summary = "Find users by name in subtree")
    @ApiResponse(responseCode = "200", description = "Users found successfully",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    @ApiResponse(responseCode = "400", description = "Invalid input or failed to find users")
    public ResponseEntity<List<User>> findUsersByNameInSubtree(
            @RequestParam String name,
            @RequestParam(required = false) Long parentId) {
        List<User> users = userService.findUsersByNameInSubtree(name, parentId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found successfully",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user by ID")
    @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    @ApiResponse(responseCode = "400", description = "Invalid input or failed to update user")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/dumpStructure")
    @Operation(summary = "Dump Organizational Structure to JSON File")
    @ApiResponse(responseCode = "200", description = "Organizational structure dumped successfully")
    @ApiResponse(responseCode = "400", description = "Failed to dump organizational structure")
    public ResponseEntity<String> dumpOrganizationalStructure(
            @RequestParam Long rootId,
            @RequestParam String filename) {
        try {
            userService.dumpOrganizationalStructureToJsonFile(rootId, filename);
            return ResponseEntity.ok("Organizational structure dumped to " + filename);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to dump organizational structure: " + e.getMessage());
        }
    }
}
