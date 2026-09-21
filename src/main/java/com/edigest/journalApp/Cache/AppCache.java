package com.edigest.journalApp.Cache;

import com.edigest.journalApp.entity.ConfigJournalApp;
import com.edigest.journalApp.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }
    @Autowired
    private ConfigJournalRepository configJournalRepository;

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache=new HashMap<>();
        List<ConfigJournalApp> all =configJournalRepository.findAll();

        for(ConfigJournalApp configJournalApp:all){
            appCache.put(configJournalApp.getKey(),configJournalApp.getValue());
        }

    }
}
