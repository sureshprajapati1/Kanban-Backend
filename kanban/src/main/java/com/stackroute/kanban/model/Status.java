package com.stackroute.kanban.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Status {
    @Id
    private String statusTitle;
    private String statusId;

    private List<Task> task;

    public void addTask(Task task1) {

        if (Objects.isNull(task)) {
            task = new ArrayList<>();
        }
        task.add(task1);
    }


}
