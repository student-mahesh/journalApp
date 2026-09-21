package com.edigest.journalApp.Service;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@Service
public class JournalEntryservice {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static  final Logger logger= LoggerFactory.getLogger(JournalEntryservice.class);


    public List<JournalEntry> getAll() {

        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user=userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);//  this entry is save in user list-->arraylist
//            user.setUserName(null);
            userService.saveEntry(user);
        }catch(Exception e){
            System.out.println(e);

            logger.info("user journal entry save");
            throw new RuntimeException("An error occured while saving the entry",e);

        }
    }

    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }


    public Optional<JournalEntry> getById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteById(ObjectId myId, String userName) {
      boolean  removed=false;
        try {
          User user = userService.findByUserName(userName);
           removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
          if (removed) {
              userService.saveEntry(user);
              journalEntryRepository.deleteById(myId);
          }
      }catch(Exception e){
              System.out.println(e);
              throw new RuntimeException("An error occured while deleting the entry:",e);
          }
        return removed;
    }
}
