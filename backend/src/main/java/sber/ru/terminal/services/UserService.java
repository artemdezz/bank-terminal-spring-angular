package sber.ru.terminal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sber.ru.terminal.entity.Role;
import sber.ru.terminal.entity.User;
import sber.ru.terminal.repositories.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;


    public User getUserById(Long userId) {
        return userRepo.findById(userId).get();
    }

    public User registration(User user){
        user.setRoles(Collections.singleton(Role.CLIENT));
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
