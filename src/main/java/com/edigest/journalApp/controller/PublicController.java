package com.edigest.journalApp.controller;

import com.edigest.journalApp.Service.UserService;
import com.edigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userservice;

    @GetMapping("/health-check")
    public String healthChecker(){

        return "OK";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userservice.saveNewUser(user);;
    }
//    @PutMapping
//    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String  userName){
//        User userInDb=userservice.findByUserName(userName);
//        if(userInDb!=null){
//            userInDb.setUserName(user.getUserName());
//            userInDb.setPassword(user.getPassword());
//            userservice.saveEntry(userInDb);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
