package com.edigest.journalApp.controller;

import com.edigest.journalApp.Service.UserDetailsServiceImpl;
import com.edigest.journalApp.Service.UserService;
import com.edigest.journalApp.entity.DTOUser;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name="Public APIs")
public class PublicController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userservice;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthChecker(){

        return "OK";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody DTOUser user){
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userservice.saveNewUser(newUser);

        userservice.saveNewUser(newUser);;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
       try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
           UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUserName());
           String jwt= jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
       }catch(Exception e){
        log.error("Exception occured while creatAuthenticationToken ",e);
        return new ResponseEntity<>("Incorrect username or password,",HttpStatus.BAD_REQUEST);
       }

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
