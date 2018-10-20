package com.test.demo.controller;

import com.test.demo.UserNotFoundException;
import com.test.demo.model.Message;
import com.test.demo.model.User;
import com.test.demo.repository.MessageRepository;
import com.test.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/users")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity(userRepository.findAll(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/messages")
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @PostMapping(path = "/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    @PostMapping(path = "/messages")
    public Message createMessage(@Valid @RequestBody Message message) {
        message.setUser(userRepository.findById(message.getUser().getId()).orElseThrow(() -> new RuntimeException("notfound")));
        return messageRepository.save(message);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User updated) {
        User user = userRepository.findById(id).get();

        user.setName(updated.getName());
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("id") Long id) {
        return messageRepository.findById(id).map(message-> {
            messageRepository.delete(message);
            return ResponseEntity.ok().build();
        }).get();
    }
}
