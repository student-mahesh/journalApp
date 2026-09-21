package com.edigest.journalApp.repository;
import com.edigest.journalApp.entity.ConfigJournalApp;
import com.edigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournalApp, ObjectId> {//class datatype and the id datatypes

//    User findByUserName(String userName) ;
//    void deleteByUserName (String username);
}
