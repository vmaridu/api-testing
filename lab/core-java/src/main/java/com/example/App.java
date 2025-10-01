package com.example;

import com.example.model.User;
import com.example.service.CsvService;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        CsvService csvService = new CsvService();
        List<User> users = csvService.getUsers();
        for(User user : users){
            System.out.println(user.getUserName());
        }
    }
}
