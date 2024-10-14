package com.doi.business.services;

import com.doi.business.model.RegistrationDTO;
import com.doi.business.model.User;
import com.doi.business.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    /* Loads a user by username for authentication. */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not valid"));
    }
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /*  Retrieves all users. */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public RegistrationDTO convertToDTO(User user) {
        return new RegistrationDTO(
                user.getFull_name(),
                user.getAfm(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone_num()
        );
    }
    /* Checks if a user has submitted a form. */
    public boolean hasUserSubmittedForm(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getForm() != null;
    }

}
