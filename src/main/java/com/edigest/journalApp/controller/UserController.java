package com.edigest.journalApp.controller;

import com.edigest.journalApp.Service.UserService;
import com.edigest.journalApp.Service.WeatherService;
import com.edigest.journalApp.api.response.WeatherResponse;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name="User APIs",description = "user,delete ,update APIs")
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User userInDb=userservice.findByUserName(userName);
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userservice.saveEntry(userInDb);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?>deleteUserById(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?>greeting(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse =weatherService.getWeather("Mumbai");
        String weathermsg="";
        if(weatherResponse!=null){
            weathermsg=",Weather feels like"+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hii"+authentication.getName()+weathermsg,HttpStatus.OK);
    }

}
