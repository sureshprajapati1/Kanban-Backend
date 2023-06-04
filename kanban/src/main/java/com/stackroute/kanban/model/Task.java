package com.stackroute.kanban.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Task {
    @Id
    private String taskTitle;
    private String taskId;
    private String taskContent;
    private String priority;
    private List<String> assignTo;
    private String StartDate;
    private String endDate;



}
