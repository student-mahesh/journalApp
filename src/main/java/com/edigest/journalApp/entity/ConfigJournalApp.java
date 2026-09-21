package com.edigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="config_journal_app")
@Data
public class ConfigJournalApp {
    @Id
    private ObjectId id;
    @NonNull
    private String key;
    private String value;



}
