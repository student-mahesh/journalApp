package com.edigest.journalApp.Service;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@Component
@Slf4j
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
   // private static  final Logger logger= LoggerFactory.getLogger(UserService.class);
    public void saveEntry(User user) {

        userRepository.save(user);
    }
    public void saveAdmin (User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
    public void saveNewUser(User user) {
     try {
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user.setRoles(Arrays.asList("USER"));
         userRepository.save(user);
     }catch(Exception e){
         log.info("error occur while saving new user");
         log.error("Error occured for {}:",user.getUserName(),e);
         log.warn("warn");
         log.debug("debug");
         log.trace("trace");
     }  //The catch block runs only when an exception occurs, and therefore the logs placed inside that catch block are printed only when that exception occurs.
    }
    public List<User> getAll() {

        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteById(ObjectId myId) {
        userRepository.deleteById(myId);
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


}


