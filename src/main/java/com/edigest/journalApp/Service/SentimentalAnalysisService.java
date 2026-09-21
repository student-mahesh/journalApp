package com.edigest.journalApp.Service;

import org.springframework.scheduling.annotation.Scheduled;

public class SentimentalAnalysisService {

    public String getSentiment(String entry) {
        return "Positive";
    }

}

