package com.cobanogluhasan.springboot.controller;


import com.cobanogluhasan.springboot.Sha256Algorithm;
import com.cobanogluhasan.springboot.exception.ResourceNotFoundException;
import com.cobanogluhasan.springboot.model.User;
import com.cobanogluhasan.springboot.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping( "/api/v1/")
public class UserController  {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

        //get all users
    @GetMapping("users")
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    //get user by id
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + userId));
    }

    //create user
    @PostMapping("users")
    public User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with id: " + userId));

        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setGsm(user.getGsm());
        existingUser.setTckn(user.getTckn());
        existingUser.setPassword(user.getPassword());

        return this.userRepository.save(existingUser);
    }

    //delete user by id
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        //get existing user from database
        User existingUser = this.userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with id: " + userId));

        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }

}
