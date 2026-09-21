package com.edigest.journalApp.controller;

import com.edigest.journalApp.Cache.AppCache;
import com.edigest.journalApp.Service.UserService;
import com.edigest.journalApp.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name="Admin APIs")
public class AdminController {

@Autowired
private UserService userService;

@Autowired
private AppCache appChache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> alluser=userService.getAll();
        if(alluser!=null && !alluser.isEmpty()){
            return new ResponseEntity<>(alluser,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }

    @GetMapping("clear-app-cache")
    public void clerAppCache(){
        appChache.init();
    }
}
