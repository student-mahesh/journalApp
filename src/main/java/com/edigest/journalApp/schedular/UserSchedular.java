package com.edigest.journalApp.schedular;

import com.edigest.journalApp.Service.EmailService;
import com.edigest.journalApp.Service.SentimentalAnalysisService;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private  EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    private SentimentalAnalysisService  sentimentalAnalysisService;
    @Scheduled(cron= "0 0 10 * * SUN")
    public void fetchUsersAndSendMail(){

        List<User> users=userRepository.getUserForSA();
        for(User user:users){

            List<JournalEntry> journalEntries=user.getJournalEntries();
           List<String> filteredEntries =journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry =String.join( " ",filteredEntries);
            String sentiment =sentimentalAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days",sentiment);
        }

    }

}
