package org.example.userauthservice_feb2026.services;

import org.example.userauthservice_feb2026.exceptions.PasswordMismatchException;
import org.example.userauthservice_feb2026.exceptions.UserAlreadyExistsException;
import org.example.userauthservice_feb2026.exceptions.UserNotSignedUpException;
import org.example.userauthservice_feb2026.models.Role;
import org.example.userauthservice_feb2026.models.Status;
import org.example.userauthservice_feb2026.models.User;
import org.example.userauthservice_feb2026.repos.RoleRepo;
import org.example.userauthservice_feb2026.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User signup(String name, String email, String password, String phoneNumber) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
           throw new UserAlreadyExistsException("Please use different emailId");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(new Date());

        Role role;
        String non_admin = "NON_ADMIN";

        Optional<Role> roleOptional = roleRepo.findByValue(non_admin);
        if(roleOptional.isEmpty()) {
            role = new Role();
            role.setValue(non_admin);
            role.setStatus(Status.ACTIVE);
            role.setCreatedAt(new Date());
            roleRepo.save(role);
        } else {
            role = roleOptional.get();
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepo.save(user);
        return user;
    }

    @Override
    public User login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
           throw new UserNotSignedUpException("Please signup first");
        }

        User user = optionalUser.get();
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("Please check your password again");
        }

        //ToDo : Generate JWT by Anurag

        return user;
    }

    @Override
    public User getProfile(String email) {
        return findActiveUser(email);
    }

    @Override
    public User updateProfile(String email, String name, String phoneNumber) {
        User user = findActiveUser(email);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setLastUpdatedAt(new Date());
        return userRepo.save(user);
    }

    @Override
    public void resetPassword(String email, String currentPassword, String newPassword) {
        User user = findActiveUser(email);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordMismatchException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
    }

    private User findActiveUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotSignedUpException("Please signup first"));
        if (user.getStatus() != Status.ACTIVE) {
            throw new UserNotSignedUpException("User account is not active");
        }
        return user;
    }
}
