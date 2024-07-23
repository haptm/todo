package com.haptm.todoapp.service;

import com.haptm.todoapp.exception.InvalidPasswordException;
import com.haptm.todoapp.exception.UserExistException;
import com.haptm.todoapp.exception.UserNotFoundException;
import com.haptm.todoapp.model.User;
import com.haptm.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with username: " + username + " not found");
        }
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User with email: " + email + " not found");
        }
    }

    private void checkIfUserExistsByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new UserExistException("User with username: " + username + " already exists");
        }
    }

    private void checkIfUserExistsByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserExistException("User with email: " + email + " already exists");
        }
    }

    public User createUser(User user) {
        checkIfUserExistsByUsername(user.getUsername());
        checkIfUserExistsByEmail(user.getEmail());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        checkIfUserExistsByUsername(user.getUsername());
        checkIfUserExistsByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public User updatePassword(Long id, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            if(userToUpdate.getPasswordHash().equals(currentPassword)) {
                userToUpdate.setPasswordHash(newPassword);
                return userRepository.save(userToUpdate);
            }
            else {
                throw new InvalidPasswordException("Invalid password");
            }
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    public void deleteUser(Long id, String password) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getPasswordHash().equals(password)) {
                userRepository.delete(user);
            }
            else {
                throw new InvalidPasswordException("Invalid password");
            }
        }
        else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }
}
