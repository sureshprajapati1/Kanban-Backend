package com.stackroute.kanban.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor @NoArgsConstructor
@Data

public class EmailDetails {

    private String emailId;
    private String name;
    private String message;
    private String subject;


}
