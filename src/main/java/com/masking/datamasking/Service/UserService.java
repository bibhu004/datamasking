package com.masking.datamasking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.masking.datamasking.Entity.User;
import com.masking.datamasking.Repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();   
    }

    // Create or Update User
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Get User by ID
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    // Get User by Username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get User by Email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete User by ID
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    // Validate User by Email
    // public boolean validateUserByEmail(String email) {
    //     Optional<User> userOptional = getUserByEmail(email);
    //     return userOptional.isPresent() && userOptional.get().isValidated();
    // }

    // Update User's Validation Status
    // public User updateUserValidationStatus(long id, boolean validationStatus) {
    //     Optional<User> userOptional = getUserById(id);
    //     if (userOptional.isPresent()) {
    //         User user = userOptional.get();
    //         user.setValidated(validationStatus);
    //         return userRepository.save(user);
    //     }
    //     return null; 
    // }

}
