package com.stackroute.kanban.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Data
@Document
public class User {
    @Id
    private String emailId;
    private String name;
    private String phoneNumber;
    private String address;
    private Binary image;
}
