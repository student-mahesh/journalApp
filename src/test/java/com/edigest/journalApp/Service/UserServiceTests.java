package com.edigest.journalApp.Service;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
public class UserServiceTests {

@Autowired
private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "Ram",
//            "Syam",
//            "Mahesh"
    })
    public void testFindByUserName(String name){
       // assertEquals(4,2+1);
        assertNotNull(userRepository.findByUserName(name),"Error message"+name);
//        assertNotNull(user.getJournalEntries().isEmpty());
        //assertNotNull(userRepository.findByUserName("Ram"));
    }
@Disabled
@ParameterizedTest
@CsvSource({
        "1,1,2",
//        "2,10,12",
//        "3,3,9"
})
public void test(int a,int b,int expected){
       assertEquals(expected, a+b);
}
}
