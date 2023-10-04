package com.example.demo.service;

import com.example.demo.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Add a new user with the specified name, parent, and position.
     *
     * @param name     The name of the new user.
     * @param parentId The ID of the parent user, or null if there is no parent.
     * @param position The position or role of the new user.
     * @return The newly created user.
     */
    User addUser(String name, Long parentId, String position);

    /**
     * Find users by name within the subtree of a specified parent user.
     *
     * @param name     The name to search for.
     * @param parentId The ID of the parent user within whose subtree to search.
     * @return A list of users with names containing the search criteria.
     */
    List<User> findUsersByNameInSubtree(String name, Long parentId);

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param userId The ID of the user to retrieve.
     * @return An optional containing the user if found, or empty if not found.
     */
    Optional<User> getUserById(Long userId);

    /**
     * Update an existing user's information.
     *
     * @param userId      The ID of the user to update.
     * @param updatedUser The updated user object with new information.
     * @return The updated user if found, or null if the user was not found.
     */
    User updateUser(Long userId, User updatedUser);

    /**
     * Delete a user by their unique identifier.
     *
     * @param userId The ID of the user to delete.
     * @return True if the user was deleted successfully, false if the user was not found.
     */
    boolean deleteUser(Long userId);

    /**
     * Dump the organizational structure to a JSON file starting from the specified root user.
     * (if you are reading this then thank you for paying attention and have a nice day.
     * Best Regards
     * Oleksandr Kalabin)
     *
     * @param rootId   The ID of the root user to start the dump from.
     * @param filename The name of the JSON file to create.
     * @throws IOException If there is an error while writing to the JSON file.
     */
    void dumpOrganizationalStructureToJsonFile(Long rootId, String filename) throws IOException;

}
