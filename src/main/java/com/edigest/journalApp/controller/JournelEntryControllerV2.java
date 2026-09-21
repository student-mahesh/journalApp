package com.edigest.journalApp.controller;
import com.edigest.journalApp.Service.JournalEntryservice;
import com.edigest.journalApp.Service.UserService;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name="Journal APIs")
public class JournelEntryControllerV2 {

    @Autowired
    private JournalEntryservice journalEntryservice;
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary="Get all journal entry of the users ")
    public ResponseEntity<List> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry> elist=user.getJournalEntries();
        if(elist!=null&&!elist.isEmpty()){
            return new ResponseEntity<>(elist,HttpStatus.OK);//200-->Successfully
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404--->Resource doesn't exist
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            journalEntryservice.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);//201-->new resourse created
        }catch(Exception e){
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);//400--?Client sent invalid data
        }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collects = user.getJournalEntries().stream().filter(x -> x.getId().equals((myId))).collect(Collectors.toList());
        if (!collects.isEmpty()) {

            Optional<JournalEntry> journalEntry = journalEntryservice.getById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);//200-->Request successful
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  //404-->Resource doesn't exist
    }



    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        boolean removed=journalEntryservice.deleteById(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204-->Successful, but no response body.
        }else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404-->Resource doesn't exist
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId,@RequestBody JournalEntry myEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName); // here we get the user detais username password and journal entries
        List<JournalEntry> collects = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList()); // the the login user entre id entry found
        if (!collects.isEmpty()) {

            Optional<JournalEntry> journalEntry = journalEntryservice.getById(myId); // herer actually that entry is fectch from the datbse mongodb
            if (journalEntry.isPresent()) {
                JournalEntry old= journalEntry.get();
                old.setTitle(myEntry.getTitle()!=null&&!myEntry.getTitle().equals("")?myEntry.getTitle(): old.getTitle());
                old.setContent(myEntry.getContent()!=null&&!myEntry.getContent().equals("")?myEntry.getContent(): old.getContent());
                journalEntryservice.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);//200---> successfully.
            }

        }
        return new ResponseEntity<>(HttpStatus.OK);//200---> success


    }
}
