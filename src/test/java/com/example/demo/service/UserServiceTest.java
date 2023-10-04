package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAddUser() {

        String name = "John Doe";
        String position = "Manager";
        Long parentId = 1L;
        User parentUser = new User();
        parentUser.setId(parentId);
        when(userRepository.findById(parentId)).thenReturn(Optional.of(parentUser));

        User newUser = new User();
        newUser.setName(name);
        newUser.setParent(parentUser);
        newUser.setPosition(position);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = userService.addUser(name, parentId, position);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(position, result.getPosition());
        assertEquals(parentUser, result.getParent());
    }

    @Test
    public void testFindUsersByNameInSubtree() {

        String name = "John Doe";
        Long parentId = 1L;
        User parentUser = new User();
        parentUser.setId(parentId);

        User user1 = new User();
        user1.setId(1L);
        user1.setName(name);
        user1.setParent(parentUser);

        User user2 = new User();
        user2.setId(2L);
        user2.setName(name);
        user2.setParent(parentUser);

        List<User> usersInSubtree = new ArrayList<>();
        usersInSubtree.add(user1);
        usersInSubtree.add(user2);

        when(userRepository.findById(parentId)).thenReturn(Optional.of(parentUser));
        when(userRepository.findByParent(parentUser)).thenReturn(usersInSubtree);

        List<User> result = userService.findUsersByNameInSubtree(name, parentId);

        assertEquals(2, result.size());
        assertEquals(name, result.get(0).getName());
        assertEquals(parentUser, result.get(0).getParent());
        assertEquals(name, result.get(1).getName());
        assertEquals(parentUser, result.get(1).getParent());
    }

}