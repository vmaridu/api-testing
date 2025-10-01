package com.example.service;

import com.example.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

    public List<User> getUsers() throws IOException {
        List<User> result = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/user.csv"));
        for(int index = 1; index < lines.size(); index ++){
            String record = lines.get(index);
            String[] recordTokens = record.split(",");
            User user = new User();
            user.setId(recordTokens[0]);
            user.setFirstName(recordTokens[1]);
            user.setLastName(recordTokens[2]);
            // user.setDob(LocalDate.);
            user.setUserName(recordTokens[4]);
            user.setPassword(recordTokens[5]);
            result.add(user);
        }
        return result;
    }

    public User getUser(String userId){
        return null;
    }
}
